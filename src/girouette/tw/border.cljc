(ns girouette.tw.border
  (:require [girouette.tw.common :refer [value-unit->css]]))

(def components
  [{:id     :border
    :rules  "
    border = <'border'> (<'-'> direction)? (<'-'> integer)?
    "
    :garden (fn [{:keys [component-data]}]
              (let [{border-width     :integer :or {border-width "1"}
                     border-direction :direction} (into {} component-data)
                    border-prop (case border-direction
                                  "t" :border-top-width
                                  "r" :border-right-width
                                  "b" :border-bottom-width
                                  "l" :border-left-width
                                  :border-width)]
                {border-prop (value-unit->css nil
                                              [:integer border-width]
                                              {:number-unit "px"})}))}])
