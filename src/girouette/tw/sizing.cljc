(ns girouette.tw.sizing
  (:require [clojure.string :as str]
            [girouette.util :as util]
            [girouette.tw.common :refer [value-unit->css div-4 mul-100]]))

(def components
  [{:id :width
    :rules "
    width = <'w-'> (number | length | length-unit | fraction | percentage-full |
                    auto | screen-100vw | min-content | max-content)
    "
    :garden (fn [{[value-data] :component-data}]
              {:width (value-unit->css value-data
                                       {:zero-unit nil
                                        :number {:unit "rem"
                                                 :value-fn div-4}
                                        :fraction {:unit "%"
                                                   :value-fn mul-100}})})}


   {:id :min-width
    :rules "
    min-width = <'min-w-'> (number | length | length-unit | fraction | percentage-full |
                            auto | screen-100vw | min-content | max-content)
    "
    :garden (fn [{[value-data] :component-data}]
              {:width (value-unit->css value-data
                                       {:zero-unit nil
                                        :number {:unit "rem"
                                                 :value-fn div-4}
                                        :fraction {:unit "%"
                                                   :value-fn mul-100}})})}


   {:id :max-width
    :rules "
    max-width = <'max-w-'> (number | length | length-unit | fraction | percentage-full |
                            none | screen-100vw | min-content | max-content)
    "
    :garden (fn [{[value-data] :component-data}]
              {:width (value-unit->css value-data
                                       {:zero-unit nil
                                        :number {:unit "rem"
                                                 :value-fn div-4}
                                        :fraction {:unit "%"
                                                   :value-fn mul-100}})})}


   {:id :height
    :rules "
    height = <'h-'> (number | length | length-unit | fraction | percentage-full |
                     auto | screen-100vh | min-content | max-content)
    "
    :garden (fn [{[value-data] :component-data}]
              {:height (value-unit->css value-data
                                        {:zero-unit nil
                                         :number {:unit "rem"
                                                  :value-fn div-4}
                                         :fraction {:unit "%"
                                                    :value-fn mul-100}})})}


   {:id :min-height
    :rules "
    min-height = <'min-h-'> (number | length | length-unit | fraction | percentage-full |
                             auto | screen-100vh | min-content | max-content)
    "
    :garden (fn [{[value-data] :component-data}]
              {:min-height (value-unit->css value-data
                                            {:zero-unit nil
                                             :number {:unit "rem"
                                                      :value-fn div-4}
                                             :fraction {:unit "%"
                                                        :value-fn mul-100}})})}


   {:id :max-height
    :rules "
    max-height = <'max-h-'> (number | length | length-unit | fraction | percentage-full |
                             none | screen-100vh | min-content | max-content)
    "
    :garden (fn [{[value-data] :component-data}]
              {:max-height (value-unit->css value-data
                                            {:zero-unit nil
                                             :number {:unit "rem"
                                                      :value-fn div-4}
                                             :fraction {:unit "%"
                                                        :value-fn mul-100}})})}])
