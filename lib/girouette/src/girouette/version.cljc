(ns girouette.version
  (:require [girouette.util :as util]))

(defn- version-category-= [version1 version2]
  (= (first version1) (first version2)))


(defn- version-< [version1 version2]
  (loop [;; Skips the version category, e.g. `:tw` or `:gi`
         v1 (next version1)
         v2 (next version2)]
    (if (or (seq v1) (seq v2))
      (let [x1 (or (first v1) 0)
            x2 (or (first v2) 0)]
        (cond
          (< x1 x2) true
          (> x1 x2) false
          :else (recur (next v1) (next v2))))
      false)))


(defn- version-<= [version1 version2]
  (loop [;; Skips the version category, e.g. `:tw` or `:gi`
         v1 (next version1)
         v2 (next version2)]
    (if (or (seq v1) (seq v2))
      (let [x1 (or (first v1) 0)
            x2 (or (first v2) 0)]
        (cond
          (< x1 x2) true
          (> x1 x2) false
          :else (recur (next v1) (next v2))))
      true)))


(defn filter-components-by-version
  "Filters the `components` according to a given version."
  [components version]
  (filter (fn [component]
            (let [{:keys [since-version removed-in-version]} component]
              (and (version-category-= since-version version)
                   (version-<= since-version version)
                   (util/implies (some? removed-in-version)
                                 (version-< version removed-in-version)))))
          (util/into-one-vector components)))
