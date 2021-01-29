(ns girouette.tw.sizing
  (:require [clojure.string :as str]
            [girouette.util :as util]
            [girouette.tw.common :refer [value-unit->css]]))

(def components
  [{:id :width
    :rules "
    width = <'w-'> (number | length | length-unit | fraction | percentage-full |
                    auto | screen-100vw | min-content | max-content)
    "
    :garden (fn [{[value-data] :component-data}]
              {:width (value-unit->css value-data
                                       {:zero-unit nil
                                        :number-unit :quarter-rem
                                        :fraction-unit "%"})})}


   {:id :min-width
    :rules "
    min-width = <'min-w-'> (number | length | length-unit | fraction | percentage-full |
                            auto | screen-100vw | min-content | max-content)
    "
    :garden (fn [{[value-data] :component-data}]
              {:width (value-unit->css value-data
                                       {:zero-unit nil
                                        :number-unit :quarter-rem
                                        :fraction-unit "%"})})}


   {:id :max-width
    :rules "
    max-width = <'max-w-'> (number | length | length-unit | fraction | percentage-full |
                            none | screen-100vw | min-content | max-content)
    "
    :garden (fn [{[value-data] :component-data}]
              {:width (value-unit->css value-data
                                       {:zero-unit nil
                                        :number-unit :quarter-rem
                                        :fraction-unit "%"})})}


   {:id :height
    :rules "
    height = <'h-'> (number | length | length-unit | fraction | percentage-full |
                     auto | screen-100vh | min-content | max-content)
    "
    :garden (fn [{[value-data] :component-data}]
              {:height (value-unit->css value-data
                                        {:zero-unit nil
                                         :number-unit :quarter-rem
                                         :fraction-unit "%"})})}


   {:id :min-height
    :rules "
    min-height = <'min-h-'> (number | length | length-unit | fraction | percentage-full |
                             auto | screen-100vh | min-content | max-content)
    "
    :garden (fn [{[value-data] :component-data}]
              {:min-height (value-unit->css value-data
                                            {:zero-unit nil
                                             :number-unit :quarter-rem
                                             :fraction-unit "%"})})}


   {:id :max-height
    :rules "
    max-height = <'max-h-'> (number | length | length-unit | fraction | percentage-full |
                             none | screen-100vh | min-content | max-content)
    "
    :garden (fn [{[value-data] :component-data}]
              {:max-height (value-unit->css value-data
                                            {:zero-unit nil
                                             :number-unit :quarter-rem
                                             :fraction-unit "%"})})}])
