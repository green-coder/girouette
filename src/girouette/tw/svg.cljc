(ns girouette.tw.svg
  (:require [girouette.tw.color :refer [read-color color->css]]
            [girouette.tw.common :refer [value-unit->css]]))

(def components
  [{:id :fill
    :rules "
    fill = <'fill-'> color
    "
    :garden (fn [{[[_ color]] :component-data}]
              (let [color (read-color color)]
                (if (string? color)
                  {:fill color}
                  {:fill (color->css color)})))}


   {:id :stroke
    :rules "
    stroke = <'stroke-'> color
    "
    :garden (fn [{[[_ color]] :component-data}]
              (let [color (read-color color)]
                (if (string? color)
                  {:stroke color}
                  {:stroke (color->css color)})))}


   {:id :stroke-width
    :rules "
    stroke-width = <'stroke-'> number
    "
    :garden (fn [{[thickness] :component-data}]
              {:stroke-width (value-unit->css thickness)})}])
