(ns girouette.tw.border
  (:require [girouette.tw.common :refer [value-unit->css]]))

(def components
  [{:id     :border-radius
    :rules  "
    border-radius = <'rounded'>  (<'-'> direction (side)?)? (<'-'> size)?
    side = 'l' | 'r'
    size = none | full | media-query-min-width
    "
    :garden (fn [{:keys [component-data]}]
              (println component-data)
              {:border-radius (value-unit->css nil [:number "1"]
                                               {:number-unit :quarter-rem})})}
   {:id     :border
    :rules  "
    border-width = <'border'> (<'-'> direction)? (<'-'> border-width-value)?
    border-width-value = number | length | length-unit
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [border-width-value direction]} (into {} component-data)
                    css-prop (case direction
                               "t" :border-top-width
                               "r" :border-right-width
                               "b" :border-bottom-width
                               "l" :border-left-width
                               :border-width)]
                {css-prop (if (nil? border-width-value)
                            "1px"
                            (value-unit->css nil border-width-value {:number-unit "px"}))}))}])
