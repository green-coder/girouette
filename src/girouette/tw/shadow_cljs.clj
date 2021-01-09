(ns girouette.tw.shadow-cljs
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [garden.core :refer [css]]
            [girouette.tw.core :refer [class-name->garden]]))

(defn- collect-class-names
  "Returns a set of all the CSS class-names used in the compiled program."
  [build-state]
  (-> (for [[namespace ns-props] (-> build-state :compiler-env :cljs.analyzer/namespaces)
            [local-symb def-props] (:defs ns-props)
            class-name (:girouette.css/class-names def-props)]
        class-name)
      set))

(def ^:private generated-class-names (atom #{}))

(defn generate-css
  "A compiler hook to be used in a Shadow-CLJS project.
   It collects the CSS class names used in the project and
   generate corresponding CSS into the output-filename."
  {:shadow.build/stage :compile-finish}
  [build-state {:keys [output-filename cache-class-names?]}]
  (let [collected-class-names (collect-class-names build-state)
        class-names (cond-> collected-class-names
                      cache-class-names? (set/union @generated-class-names))]
    (when (not= class-names @generated-class-names)
      (io/make-parents output-filename)
      (spit output-filename (->> (sort class-names)
                                 (mapv class-name->garden)
                                 css))
      (reset! generated-class-names class-names)))
  ;(tap> build-state)
  build-state)
