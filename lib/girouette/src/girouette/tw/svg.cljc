(ns ^:no-doc girouette.tw.svg
  (:require [girouette.tw.color :refer [read-color color->css]]
            [girouette.tw.common :refer [value-unit->css]]))

(def components
  [{:id :fill
    :rules "
    fill = <'fill-'> color
    "
    :garden (fn [{[color] :component-data}]
              {:fill (color->css (read-color color))})}


   {:id :stroke
    :rules "
    stroke = <'stroke-'> color
    "
    :garden (fn [{[color] :component-data}]
              {:stroke (color->css (read-color color))})}


   {:id :stroke-width
    :rules "
    stroke-width = <'stroke-'> number
    "
    :garden (fn [{[thickness] :component-data}]
              {:stroke-width (value-unit->css thickness)})}])
