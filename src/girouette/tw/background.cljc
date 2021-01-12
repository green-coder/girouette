(ns girouette.tw.background
  (:require [girouette.tw.common :refer [read-number value->css value-unit->css]]
            [girouette.tw.color :refer [read-color color->css]]))

(def components
  [{:id :background-color
    :rules "
    background-color = <'bg-'> color
    "
    :garden (fn [{[[_ color]] :component-data}]
              (let [color (read-color color)]
                (if (string? color)
                  {:color color}
                  (let [[r g b a] color]
                    (if (some? a)
                      {:background-color (color->css color)}
                      {:--gi-bg-opacity 1
                       :background-color (color->css [r g b "var(--gi-bg-opacity)"])})))))}


   {:id :background-opacity
    :rules "
    background-opacity = <'bg-opacity-'> integer
    "
    :garden (fn [{[[_ value]] :component-data}]
              {:--gi-bg-opacity (value->css (/ (read-number value) 100.0))})}])
