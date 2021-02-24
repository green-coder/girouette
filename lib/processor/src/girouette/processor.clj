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
            [girouette.processor.env :refer [config]])
  (:import (java.io File)))


(defn red-str [& s]
  (let [s (apply str s)]
    (if (:color? @config)
      (str "\u001B[31m" s "\u001B[0m")
      s)))

(defn green-str [& s]
  (let [s (apply str s)]
    (if (:color? @config)
      (str "\u001B[32m" s "\u001B[0m")
      s)))


(defn- stub-js-deps! [state]
  (let [deps (closure/get-upstream-deps)
        npm-deps (when (map? (:npm-deps deps))
                   (keys (:npm-deps deps)))
        foreign-libs (mapcat :provides (:foreign-libs deps))
        stubbed-js-deps (zipmap (concat npm-deps foreign-libs)
                                (repeatedly #(gensym "fake$module")))]
    (swap! state update :js-dependency-index #(merge stubbed-js-deps %))))


(defonce state
  (let [s (ana-api/empty-state)]
    (ana-api/with-state s
     (comp/with-core-cljs))
    (stub-js-deps! s)
    s))


(defn- invoke-hook [qualified-symbol hook-fn]
  (fn [env ast opts]
    (when (and (= (:op ast) :invoke)
               (= (-> ast :fn :name) qualified-symbol))
      (hook-fn (-> ast :form second)))
    ast))


(defn- gather-css-classes [file]
  (let [css-classes (atom #{})
        passes [(invoke-hook (:css-symb @config)
                             (fn [form]
                               (walk/postwalk (fn [x]
                                                (when (string? x)
                                                  (swap! css-classes conj x)))
                                              form)))]
        ns-info (ana-api/parse-ns file)
        ns (:ns ns-info)]
    ;; Make sure that we forget about the previous parsed info in that namespace.
    (when ns
      (ana-api/remove-ns state ns))

    ;; Parse the file and collect the used CSS classes.
    (ana-api/with-passes passes
      (ana-api/analyze-file state file nil))

    {:ns ns
     :css-classes (into [] (sort @css-classes))}))

#_ (gather-css-classes (io/file "../../example/shadow-cljs-reagent-demo/src/acme/frontend/app.cljc"))
#_ (ana-api/parse-ns (io/file "../../example/shadow-cljs-reagent-demo/src/acme/frontend/app.cljc"))


(defn- find-source-paths []
  (let [{:keys [root-edn user-edn project-edn]} (t/find-edn-maps)
        deps (t/merge-edns [root-edn user-edn project-edn])]
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
  (let [{:keys [output-format output-file]} @config]
    (if (= output-format :css-classes)
      ;; css class names by relative file path
      (with-open [file-writer (io/writer output-file :encoding "UTF-8")]
        (pp/pprint @file-data file-writer))
      (let [all-cs-classes (into #{} (mapcat :css-classes) (vals @file-data))
            all-garden-defs (into [] (keep (:garden-fn @config)) (sort all-cs-classes))]
        (if (= output-format :garden)
          ;; garden
          (with-open [file-writer (io/writer output-file :encoding "UTF-8")]
            (pp/pprint all-garden-defs file-writer))
          ;; css stylesheet
          (spit output-file (garden/css all-garden-defs)))))))


(defn- on-file-changed
  ([^File file change-type]
   (let [relative-path (relative-path file)
         css-classes-before (set (-> @file-data (get relative-path) :css-classes))]
     (when (#{:delete :modify} change-type)
       (swap! file-data dissoc relative-path))

     (when (#{:create :modify} change-type)
       (swap! file-data assoc relative-path (gather-css-classes file)))

     (when (:verbose? @config)
       (let [css-classes-after (set (-> @file-data (get relative-path) :css-classes))
             removed-classes (sort (set/difference css-classes-before css-classes-after))
             added-classes (sort (set/difference css-classes-after css-classes-before))]
         (when (or (seq removed-classes)
                   (seq added-classes))
           (println (str relative-path ": "
                         (when (seq removed-classes)
                           (red-str "[- " (str/join " " removed-classes) "] "))
                         (when (seq added-classes)
                           (green-str "[+ " (str/join " " added-classes) "]"))))))))))


(defn process
  [{{:keys [source-paths file-filters]
     :or {source-paths (find-source-paths)
          file-filters [".cljs" ".cljc" ".clj"]}} :input

    {:keys [retrieval-method output-format output-file]
     :or {retrieval-method :annotated
          output-format :css}} :css

    :keys [css-symb garden-fn watch? verbose? color?]
    :or {css-symb 'girouette.core/css
         garden-fn 'girouette.tw.default-api/class-name->garden
         watch? false
         verbose? true
         color? true}}]

  (assert (and (seq source-paths)
               (every? string? source-paths))
          "source-paths should be a sequence of strings")
  (assert (and (seq file-filters)
               (every? string? file-filters))
          "file-filters should be a sequence of strings")

  (assert (contains? #{#_:anything :annotated} retrieval-method)
          "retrieval-method should be in #{:annotated}")
  (assert (contains? #{:css-classes :garden :css} output-format)
          "output-format should be in #{:css-classes :garden :css}")
  (assert (or (nil? output-file)
              (string? output-file))
          "output-file should be a string")

  (assert (qualified-symbol? css-symb)
          "css-symb should be a qualified symbol")
  (assert (qualified-symbol? garden-fn)
          "garden-fn should be a qualified symbol")
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
                    :watch? watch?
                    :verbose? verbose?
                    :color? color?})
    (when verbose?
      (pp/pprint @config))

    ;; Process all the files
    (doseq [^File file (->> (map io/file source-paths)
                            (mapcat file-seq)
                            (filter input-file?))]
      (on-file-changed file :create))

    ;; Emit the output
    (spit-output)

    ;; If requested, listen to the file changes.
    (when watch?
      (when verbose?
        (println (str "Watching files in " (str/join ", " source-paths) " ...")))
      (hawk/watch! [{:paths source-paths
                     :handler (fn [ctx {:keys [^File file kind]}]
                                (when (input-file? file)
                                  (on-file-changed file kind)
                                  (spit-output))
                                ctx)}]))))


(comment
  (process {:css {:retrieval-method :annotated
                  :output-format :garden
                  :output-file "girouette.edn"}
            :watch? true
            :verbose? true})

  (hawk/stop! *1)

  ,)