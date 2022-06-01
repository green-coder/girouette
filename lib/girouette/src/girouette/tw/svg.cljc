(ns ^:no-doc girouette.tw.svg
  (:require [girouette.tw.color :refer [color->css]]
            [girouette.tw.common :refer [value-unit->css]]))

(def components
  [{:id :fill
    :since-version [:tw 2]
    :rules "
    fill = <'fill-'> color
    "
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              {:fill (color->css (read-color color))})}


   {:id :stroke
    :since-version [:tw 2]
    :rules "
    stroke = <'stroke-'> color
    "
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              {:stroke (color->css (read-color color))})}


   {:id :stroke-width
    :since-version [:tw 2]
    :rules "
    stroke-width = <'stroke-'> number
    "
    :garden (fn [{[thickness] :component-data}]
              {:stroke-width (value-unit->css thickness)})}])
