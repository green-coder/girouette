(ns girouette.tw.typography
  (:require [girouette.tw.common :refer [read-number value->css value-unit->css]]
            [girouette.tw.color :refer [read-color rgba->css]]))

(def components
  [{:id :text-color
    :rules "
    text-color = <'text-'> color
    "
    :garden (fn [{[[_ color]] :component-data}]
              (let [color (read-color color)]
                (if (string? color)
                  {:color color}
                  (let [[r g b a] color]
                    {:--gi-text-opacity (if (some? a)
                                          (/ (min a 255) 255.0)
                                          1)
                     :color (rgba->css r g b (or a 255))}))))}


   {:id :text-opacity
    :rules "
    text-opacity = <'text-opacity-'> integer
    "
    :garden (fn [{[[_ value]] :component-data}]
              {:--gi-text-opacity (value->css (* (read-number value) 0.01))})}])
