(ns ^:no-doc girouette.tw.spacing
  (:require [girouette.tw.common :refer [value-unit->css div-4 between-children-selector]]))


(def components
  [{:id :padding
    :since-version [:tw 2]
    :rules "
    padding = signus? <'p'> (direction | axis)? <'-'> padding-value
    padding-value = number | length | length-unit
    "
    :garden (fn [{:keys [component-data unitless-length-conversion]}]
              (let [{:keys [signus direction axis padding-value]} (into {} component-data)
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
                                                              :number unitless-length-conversion})]
                (if (nil? directions)
                  {:padding value-css}
                  (into {}
                        (map (fn [direction] [(keyword (str "padding-" direction)) value-css]))
                        directions))))}


   {:id :margin
    :since-version [:tw 2]
    :rules "
    margin = signus? <'m'> (direction | axis)? <'-'> margin-value
    margin-value = number | length | length-unit | auto
    "
    :garden (fn [{:keys [component-data unitless-length-conversion]}]
              (let [{:keys [signus direction axis margin-value]} (into {} component-data)
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
                                                             :number unitless-length-conversion})]
                (if (nil? directions)
                  {:margin value-css}
                  (into {}
                        (map (fn [direction] [(keyword (str "margin-" direction)) value-css]))
                        directions))))}


   {:id :space-between
    :since-version [:tw 2]
    :rules "
    space-between = signus? <'space-'> axis <'-'> space-between-value (<'-'> space-between-reverse)?
    space-between-value = number | length | length-unit
    space-between-reverse = 'reverse'
    "
    :garden (fn [{:keys [component-data unitless-length-conversion]}]
              (let [{:keys [signus axis space-between-value space-between-reverse]} (into {} component-data)
                    direction ({["x" false] "left"
                                ["x" true]  "right"
                                ["y" false] "top"
                                ["y" true]  "bottom"} [axis (some? space-between-reverse)])
                    value-css (value-unit->css space-between-value {:signus signus
                                                                    :zero-unit nil
                                                                    :number unitless-length-conversion})]
                [between-children-selector {(keyword (str "margin-" direction)) value-css}]))}])
