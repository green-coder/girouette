(ns girouette.processor
  (:require [cljs.analyzer.api :as ana-api]
            [cljs.closure :as closure]
            [cljs.compiler.api :as comp]
            [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [clojure.set :as set]
            [clojure.string :as str]
            [clojure.tools.deps.alpha :as t]
            [clojure.tools.deps.alpha.util.dir :refer [*the-dir*]]
            [clojure.walk :as walk]
            [garden.core :as garden]
            [hawk.core :as hawk]
            [girouette.tw.preflight :refer [preflight]]
            [girouette.processor.env :refer [config]])
  (:import (java.io File)))

(defn color-str [color & s]
  (let [s          (apply str s)
        ansi-color {:reset         "\u001B[0m"
                    :bright-red    "\u001B[31;1m"
                    :bright-blue   "\u001B[34;1m"
                    :bright-yellow "\u001B[33;1m"
                    :bright-green  "\u001B[32;1m"}]
    (if (:color? @config)
      (str (color ansi-color) s (:reset ansi-color))
      s)))


(defn- stub-js-deps! [state]
  (let [deps            (closure/get-upstream-deps)
        npm-deps        (when (map? (:npm-deps deps))
                          (keys (:npm-deps deps)))
        foreign-libs    (mapcat :provides (:foreign-libs deps))
        stubbed-js-deps (zipmap (concat npm-deps foreign-libs)
                                (repeatedly #(gensym "fake$module")))]
    (swap! state update :js-dependency-index #(merge stubbed-js-deps %))))


(defonce state
  (let [s (ana-api/empty-state)]
    (ana-api/with-state s
      (comp/with-core-cljs))
    (stub-js-deps! s)
    s))

;; (ana-api/analyze nil "hi")
;; (ana-api/analyze nil :hi)

(defn- invoke-hook [qualified-symbol hook-fn]
  (fn [env ast opts]
    (when (and (= (:op ast) :invoke)
               (= (-> ast :fn :name) qualified-symbol))
      (hook-fn (-> ast :form second)))
    ast))

(def receive-hook-fn
  {:keyword #(->> (name %)
                  (re-seq #"\.[^\.#]+")
                  (map (fn [s] (subs s 1))))
   :string  #(->> (str/split % #" ")
                  (remove str/blank?))
   :symbol  #(re-seq #"[\w-]+" (name %))})

(defn receive-utility [type css-classes]
  (fn [x]
    (let [names ((type receive-hook-fn) x)]
      (swap! css-classes into names))))

(defn- receive [type css-classes]
  (let [hook-type {:keyword 'cljs.core/Keyword
                   :string  'string
                   :symbol  'cljs.core/Symbol}]
    (fn [env ast opts]
      (when (and (= (:op ast) :const)
                 (= (:tag ast) (type hook-type)))
        ((receive-utility type css-classes) (:val ast)))
      ast)))

(defn- gather-css-classes [file]
  (let [css-classes (atom #{})
        passes      (case (:retrieval-method @config)
                      :comprehensive [
                                      (receive :keyword css-classes)
                                      (receive :string css-classes)
                                      (receive :symbol css-classes)
                                      ]
                      :annotated     [(invoke-hook (:css-symb @config)
                                                   (fn [form]
                                                     (walk/postwalk
                                                       (fn [x]
                                                         (when (string? x)
                                                           (receive-utility :string css-classes)))
                                                       form)))])
        ns-info     (ana-api/no-warn
                      (ana-api/parse-ns file))
        ns          (:ns ns-info)]
    ;; Make sure that we forget about the previous parsed info in that namespace.
    (when ns
      (ana-api/remove-ns state ns))

    ;; Parse the file and collect the used CSS classes.
    (ana-api/no-warn
      (ana-api/with-passes passes
        (ana-api/analyze-file state file nil)))

    {:ns          ns
     :css-classes (into [] (sort @css-classes))}))

#_ (gather-css-classes (io/file "../../example/reagent-demo/src/acme/frontend/app.cljc"))
#_ (ana-api/parse-ns (io/file "../../example/reagent-demo/src/acme/frontend/app.cljc"))

#_ (gather-css-classes (io/file "../../example/reagent-demo/src/acme/frontend/drop_in.cljc"))
#_ (ana-api/parse-ns (io/file "../../example/reagent-demo/src/acme/frontend/drop_in.cljc"))

(defn- find-source-paths []
  (let [{:keys [root-edn user-edn project-edn]} (t/find-edn-maps)
        deps                                    (t/merge-edns [root-edn user-edn project-edn])]
    (:paths deps)))


(defn- relative-path [^File file]
  (let [home-path (.toPath *the-dir*)]
    (.toString (.relativize home-path (.toPath (.getCanonicalFile file))))))


(defn- input-file? [^File file]
  (let [path (.getPath file)]
    (some #(str/ends-with? path %) (:file-filters @config))))


;; {relative-path {:ns acme.frontend.app
;;                 :css-classes #{"flex" "flex-1" "flex-2" "flex-9/3"}}}
(def file-data (atom (sorted-map-by compare)))


(defn- spit-output []
  (let [{:keys [output-format output-file preflight?]} @config]
    ;; NOTE output-file is a string, e.g. "public/style/girouette.css"
    ;; so we should check all parent directories exist
    (let [file-parent (-> (io/file output-file)
                          (.getParent)
                          (io/file))]
      (when-not (.exists file-parent)
        (.mkdirs file-parent))
      (if (= output-format :css-classes)
        ;; css class names by relative file path
        (with-open [file-writer (io/writer output-file :encoding "UTF-8")]
          (pp/pprint @file-data file-writer))
        (let [all-css-classes (into #{} (mapcat :css-classes) (vals @file-data))
              predef-garden   (if preflight? preflight [])
              all-garden-defs (into predef-garden (keep (:garden-fn @config)) (sort all-css-classes))]
          (if (= output-format :garden)
            ;; garden
            (with-open [file-writer (io/writer output-file :encoding "UTF-8")]
              (pp/pprint all-garden-defs file-writer))
            ;; css stylesheet
            (spit output-file (garden/css all-garden-defs))))))))


(defn- on-file-changed
  ([^File file change-type]
   (let [relative-path      (relative-path file)
         css-classes-before (set (-> @file-data (get relative-path) :css-classes))]
     (try
       (let [gathered-css-classes (gather-css-classes file)]
         (when (#{:delete :modify} change-type)
           (swap! file-data dissoc relative-path))

         (when (#{:create :modify} change-type)
           (swap! file-data assoc relative-path gathered-css-classes))

         (when (:verbose? @config)
           (let [css-classes-after        (set (-> @file-data (get relative-path) :css-classes))
                 removed-classes          (sort (set/difference css-classes-before css-classes-after))
                 added-classes            (sort (set/difference css-classes-after css-classes-before))
                 match-grammar?           (comp some? (:garden-fn @config))
                 removed-matching-classes (filter match-grammar? removed-classes)
                 removed-unknown-classes  (remove match-grammar? removed-classes)
                 added-unknown-classes    (remove match-grammar? added-classes)
                 added-matching-classes   (filter match-grammar? added-classes)]
             (when (or (seq removed-classes)
                       (seq added-classes))
               (println (str relative-path ": "
                             (when (seq added-unknown-classes)
                               (color-str :bright-red "[\uD83D\uDE31 " (str/join " " added-unknown-classes) "] "))
                             (when (seq removed-unknown-classes)
                               (color-str :bright-yellow "[\uD83D\uDE24 " (str/join " " removed-unknown-classes) "] "))
                             (when (seq removed-matching-classes)
                               (color-str :bright-blue "[- " (str/join " " removed-matching-classes) "] "))
                             (when (seq added-matching-classes)
                               (color-str :bright-green "[+ " (str/join " " added-matching-classes) "]"))))))))
       (catch Exception e
         (println (str relative-path ": \uD83D\uDCA5 parse error!")))))))


(defn process
  [{{:keys [source-paths file-filters]
     :or {source-paths (find-source-paths)
          file-filters [".cljs" ".cljc" ".clj"]}} :input

    {:keys [retrieval-method output-format output-file]
     :or {retrieval-method :comprehensive
          output-format :css}} :css

    :keys [css-symb garden-fn preflight? watch? verbose? color?]
    :or {css-symb 'girouette.core/css
         garden-fn 'girouette.tw.default-api/class-name->garden
         preflight? true
         watch? false
         verbose? true
         color? true}}]

  (assert (and (seq source-paths)
               (every? string? source-paths))
          "source-paths should be a sequence of strings")
  (assert (and (seq file-filters)
               (every? string? file-filters))
          "file-filters should be a sequence of strings")

  (assert (contains? #{:comprehensive :annotated} retrieval-method)
          "retrieval-method should be in #{:comprehensive :annotated}")
  (assert (contains? #{:css-classes :garden :css} output-format)
          "output-format should be in #{:css-classes :garden :css}")
  (assert (or (nil? output-file)
              (string? output-file))
          "output-file should be a string")

  (assert (qualified-symbol? css-symb)
          "css-symb should be a qualified symbol")
  (assert (qualified-symbol? garden-fn)
          "garden-fn should be a qualified symbol")
  (assert (boolean? preflight?)
          "preflight? should be a boolean")
  (assert (boolean? watch?)
          "watch? should be a boolean")
  (assert (boolean? verbose?)
          "verbose? should be a boolean")
  (assert (boolean? color?)
          "color? should be a boolean")

  (let [garden-fn (cond-> garden-fn
                    (#{:garden :css} output-format) requiring-resolve)
        output-file (cond
                      (some? output-file) output-file
                      (= output-format :css) "girouette.css"
                      :else "girouette.edn")]
    (reset! config {:source-paths source-paths
                    :file-filters file-filters
                    :retrieval-method retrieval-method
                    :output-format output-format
                    :output-file output-file
                    :css-symb css-symb
                    :garden-fn garden-fn
                    :preflight? preflight?
                    :watch? watch?
                    :verbose? verbose?
                    :color? color?})
    (when verbose?
      (println "⚙️ Settings:")
      (pp/pprint @config)
      (println))

    ;; Process all the files
    (doseq [^File file (->> (map io/file source-paths)
                            (mapcat file-seq)
                            (filter input-file?))]
      (on-file-changed file :create))

    ;; Emit the output
    (spit-output)
    (when verbose?
      (println "\uD83C\uDF89 CSS stylesheet generated!"))

    ;; If requested, listen to the file changes.
    (when watch?
      (when verbose?
        (println (str "\n\uD83D\uDC40 Watching files in " (str/join ", " source-paths) " ...")))
      (hawk/watch! [{:paths source-paths
                     :handler (fn [ctx {:keys [^File file kind]}]
                                (when (input-file? file)
                                  (on-file-changed file kind)
                                  (spit-output))
                                ctx)}]))))
