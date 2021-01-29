(ns girouette.tw.spacing
  (:require [garden.selectors :as gs]
            [girouette.util :as util]
            [girouette.tw.common :refer [dot value-unit->css default-pipeline]]))


(def components
  [{:id :padding
    :rules "
    padding = signus? <'p'> (direction | axis)? <'-'> padding-value
    padding-value = number | length | length-unit
    "
    :garden (fn [{component-data :component-data}]
              (let [{:keys [signus direction axis padding-value]} (util/index-by first second component-data)
                    directions (case direction
                                 "t" ["top"]
                                 "r" ["right"]
                                 "b" ["bottom"]
                                 "l" ["left"]
                                 (case axis
                                   "x" ["left" "right"]
                                   "y" ["top" "bottom"]
                                   nil))
                    value-css (value-unit->css padding-value {:signus signus
                                                              :zero-unit nil
                                                              :number-unit :quarter-rem})]
                (if (nil? directions)
                  {:padding value-css}
                  (into {}
                        (map (fn [direction] [(keyword (str "padding-" direction)) value-css]))
                        directions))))}


   {:id :margin
    :rules "
    margin = signus? <'m'> (direction | axis)? <'-'> margin-value
    margin-value = number | length | length-unit | auto
    "
    :garden (fn [{component-data :component-data}]
              (let [{:keys [signus direction axis margin-value]} (util/index-by first second component-data)
                    directions (case direction
                                 "t" ["top"]
                                 "r" ["right"]
                                 "b" ["bottom"]
                                 "l" ["left"]
                                 (case axis
                                   "x" ["left" "right"]
                                   "y" ["top" "bottom"]
                                   nil))
                    value-css (value-unit->css margin-value {:signus signus
                                                             :zero-unit nil
                                                             :number-unit :quarter-rem})]
                (if (nil? directions)
                  {:margin value-css}
                  (into {}
                        (map (fn [direction] [(keyword (str "margin-" direction)) value-css]))
                        directions))))}

   {:id :space-between
    :rules "
    space-between = signus? <'space-'> axis <'-'> space-between-value (<'-'> space-between-reverse)?
    space-between-value = number | length | length-unit
    space-between-reverse = 'reverse'
    "
    :pipeline (assoc default-pipeline
                :class-name [(fn [rule props]
                               [(gs/> (dot (:class-name props)) (gs/+ :* :*)) rule])])
    :garden (fn [{component-data :component-data}]
              (let [{:keys [signus axis space-between-value space-between-reverse]} (util/index-by first second component-data)
                    direction ({["x" false] "left"
                                ["x" true]  "right"
                                ["y" false] "top"
                                ["y" true]  "bottom"} [axis (some? space-between-reverse)])
                    value-css (value-unit->css space-between-value {:signus signus
                                                                    :zero-unit nil
                                                                    :number-unit :quarter-rem})]
                {(keyword (str "margin-" direction)) value-css}))}])
