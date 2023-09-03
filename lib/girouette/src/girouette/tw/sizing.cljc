(ns ^:no-doc girouette.tw.sizing
  (:require [girouette.tw.common :refer [value-unit->css div-4 mul-100]]))

(def components
  [{:id :width
    :since-version [:tw 2]
    :rules "
    width = <'w-'> (number | length | length-unit | fraction | percentage | full-100% |
                    auto | screen-100vw | min-content | max-content | fit-content)
    "
    :garden (fn [{[value-data] :component-data
                  :keys [unitless-length-conversion]}]
              {:width (value-unit->css value-data
                                       {:zero-unit nil
                                        :number unitless-length-conversion
                                        :fraction {:unit "%"
                                                   :value-fn mul-100}})})}


   {:id :min-width
    :since-version [:tw 2]
    :rules "
    min-width = <'min-w-'> (number | length | length-unit | fraction | percentage | full-100% |
                            auto | screen-100vw | min-content | max-content | fit-content)
    "
    :garden (fn [{[value-data] :component-data
                  :keys [unitless-length-conversion]}]
              {:min-width (value-unit->css value-data
                                           {:zero-unit nil
                                            :number unitless-length-conversion
                                            :fraction {:unit "%"
                                                       :value-fn mul-100}})})}


   {:id     :max-width
    :since-version [:tw 2]
    :rules  "
    max-width = <'max-w-'> (max-width-fixed-size | max-width-generic-size)
    max-width-fixed-size = 'xs' | 'sm' | 'md' | 'lg' | 'xl' | '2xl' | '3xl' | '4xl' | '5xl' | '6xl' | '7xl' |
                           'prose' | 'screen-sm' | 'screen-md' | 'screen-lg' | 'screen-xl' | 'screen-2xl'
    max-width-generic-size = number | length | length-unit | fraction | percentage | full-100% |
                             none | screen-100vw | min-content | max-content | fit-content
    "
    :garden (fn [{:keys [component-data unitless-length-conversion]}]
              (let [{:keys [max-width-fixed-size max-width-generic-size]} (into {} component-data)]
                {:max-width (if (some? max-width-fixed-size)
                              ({"xs"  "20rem"
                                "sm"  "24rem"
                                "md"  "28rem"
                                "lg"  "32rem"
                                "xl"  "36rem"
                                "2xl" "42rem"
                                "3xl" "48rem"
                                "4xl" "56rem"
                                "5xl" "64rem"
                                "6xl" "72rem"
                                "7xl" "80rem"
                                "prose" "65ch"
                                "screen-sm" "640px"
                                "screen-md" "768px"
                                "screen-lg" "1024px"
                                "screen-xl" "1280px"
                                "screen-2xl" "1536px"} max-width-fixed-size)
                              (value-unit->css max-width-generic-size
                                               {:zero-unit nil
                                                :number    unitless-length-conversion
                                                :fraction  {:unit     "%"
                                                            :value-fn mul-100}}))}))}


   {:id :height
    :since-version [:tw 2]
    :rules "
    height = <'h-'> (number | length | length-unit | fraction | percentage | full-100% |
                     auto | screen-100vh | min-content | max-content | fit-content)
    "
    :garden (fn [{[value-data] :component-data
                  :keys [unitless-length-conversion]}]
              {:height (value-unit->css value-data
                                        {:zero-unit nil
                                         :number unitless-length-conversion
                                         :fraction {:unit "%"
                                                    :value-fn mul-100}})})}


   {:id :min-height
    :since-version [:tw 2]
    :rules "
    min-height = <'min-h-'> (number | length | length-unit | fraction | percentage | full-100% |
                             auto | screen-100vh | min-content | max-content | fit-content)
    "
    :garden (fn [{[value-data] :component-data
                  :keys [unitless-length-conversion]}]
              {:min-height (value-unit->css value-data
                                            {:zero-unit nil
                                             :number unitless-length-conversion
                                             :fraction {:unit "%"
                                                        :value-fn mul-100}})})}


   {:id :max-height
    :since-version [:tw 2]
    :rules "
    max-height = <'max-h-'> (number | length | length-unit | fraction | percentage | full-100% |
                             none | screen-100vh | min-content | max-content | fit-content)
    "
    :garden (fn [{[value-data] :component-data
                  :keys [unitless-length-conversion]}]
              {:max-height (value-unit->css value-data
                                            {:zero-unit nil
                                             :number unitless-length-conversion
                                             :fraction {:unit "%"
                                                        :value-fn mul-100}})})}])
