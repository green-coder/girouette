(ns ^:no-doc girouette.util
  (:refer-clojure :exclude [group-by])
  (:require [clojure.core :as cc]))

(defn group-by
  "Same as clojure.core/group-by, but with some handy new arities which apply
   custom map & reduce operations to the elements grouped together under the same key."
  ([kf coll]
   ;(group-by kf identity conj [] coll)
   (cc/group-by kf coll))
  ([kf vf coll]
   (group-by kf vf conj [] coll))
  ([kf vf rf coll]
   (group-by kf vf rf (rf) coll))
  ([kf vf rf init coll]
   (->> coll
        (reduce (fn [ret x]
                  (let [k (kf x)
                        v (vf x)]
                    (assoc! ret k (rf (get ret k init) v))))
                (transient {}))
        persistent!)))

#_ (group-by first             [[:a 1] [:a 2] [:b 3] [:a 4] [:b 5]])
#_ (group-by first second      [[:a 1] [:a 2] [:b 3] [:a 4] [:b 5]])
#_ (group-by first second +    [[:a 1] [:a 2] [:b 3] [:a 4] [:b 5]])
#_ (group-by first second + 10 [[:a 1] [:a 2] [:b 3] [:a 4] [:b 5]])

(defn index-by
  ([kf coll]
   (index-by kf identity coll))
  ([kf vf coll]
   (->> coll
        (reduce (fn [ret x]
                  (assoc! ret (kf x) (vf x)))
                (transient {}))
        persistent!)))

#_ (index-by first        [[:a 1] [:a 2] [:b 3] [:a 4] [:b 5]])
#_ (index-by first second [[:a 1] [:a 2] [:b 3] [:a 4] [:b 5]])

(defn into-one-vector
  "Flattens the outer vectors in the provided data and returns one vector with the data inside."
  [data]
  (if (vector? data)
    (into [] (mapcat into-one-vector) data)
    [data]))

#_ (into-one-vector 3)
#_ (into-one-vector [3 4 5 6])
#_ (into-one-vector [3 [4 5 6]])
#_ (into-one-vector [[3] [[4] 5 [6]]])
#_ (into-one-vector [[3] [] [[]] [[4] [5]] 6])
