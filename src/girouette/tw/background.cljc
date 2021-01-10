(ns girouette.tw.background
  (:require [girouette.tw.common :refer [value-unit->css]]))

(def components
  [{:id :xxx
    :rules "
    xxx = <'xxx'> integer
    "
    :garden (fn [{[val] :component-data}]
              {:xxx (value-unit->css nil val {})})}])
