(ns girouette.tw.border
  (:require [girouette.tw.common :refer [value-unit->css]]))

(def components
  [{:id     :border
    :rules  "
    border = <'border-'> integer
    "
    :garden (fn [{[val] :component-data}]
              {:border-width (value-unit->css nil val {:number-unit "px"})})}])

