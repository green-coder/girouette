(ns girouette.garden.util
  (:require [clojure.spec.alpha :as s]
            [clojure.walk :as walk]))

(declare merge-rules)

(defn- merge-similar-rules [[rule-type x y] values]
  (case rule-type
    :simple       [x (apply merge values)]
    :pseudo-class [x [y (apply merge values)]]
    :media        (assoc-in x [:value :rules] (merge-rules (apply concat values)))
    :unknown      values))

(defn- rule-info [rule]
  (condp s/valid? rule
    (s/tuple string? map?)
    {:ident [:simple (first rule)]
     :value (second rule)}

    (s/tuple string? (s/tuple keyword? map?))
    {:ident [:pseudo-class (first rule) (first (second rule))]
     :value (second (second rule))}

    (s/and (s/keys :req-un [::identifier ::value])
           #(= :media (:identifier %)))
    {:ident [:media (update rule :value dissoc :rules)]
     :value (get-in rule [:value :rules])}

    {:ident [:unknown rule]
     :value rule}))

(defn merge-rules
  "Combine garden rules that have the same selectors."
  [rules]
  (map (fn [[ident values]]
         (merge-similar-rules ident values))
       (reduce (fn [m rule]
                 (let [{:keys [ident value]} (rule-info rule)]
                   (update m ident conj value)))
               {}
               rules)))

(defn apply-class-rules [garden alias-class classes]
  (merge-rules
    (keep (fn [rule]
            (let [new-rule (walk/postwalk (fn [x]
                                            (if (contains? classes x)
                                              alias-class
                                              x))
                                          rule)]
              (when (not= rule new-rule)
                new-rule)))
          garden)))
