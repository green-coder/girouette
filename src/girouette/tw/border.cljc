(ns girouette.tw.border
  (:require [girouette.tw.common :refer [value-unit->css]]))

(def components
  [{:id     :border
    :rules  "
    border = <'border'> (<'-'> direction)? (<'-'> border-width)?
    border-width = number | length | length-unit
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [border-width direction]
                     :or   {border-width [:integer "1"]}} (into {} component-data)
                    border-prop (case direction
                                  "t" :border-top-width
                                  "r" :border-right-width
                                  "b" :border-bottom-width
                                  "l" :border-left-width
                                  :border-width)]
                {border-prop (value-unit->css nil
                                              border-width
                                              {:number-unit "px"})}))}])
