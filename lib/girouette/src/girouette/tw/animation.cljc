(ns ^:no-doc girouette.tw.animation
  (:require [garden.stylesheet :as gs]
            [girouette.tw.common :refer [value-unit->css]]))

(def components
  [{:id :transition-property
    :since-version [:tw 2]
    :rules "
    transition-property = <'transition'> (<'-'> ('none' | 'all' | 'colors' |
                                                 'opacity' | 'shadow' | 'transform'))?
    "
    :garden (fn [{[type] :component-data}]
              (if (= type "none")
                {:transition-property "none"}
                {:transition-property ({"all" "all"
                                        nil "background-color,border-color,color,fill,stroke,opacity,box-shadow,transform"
                                        "colors" "background-color,border-color,color,fill,stroke"
                                        "opacity" "opacity"
                                        "shadow" "box-shadow"
                                        "transform" "transform"} type)
                 :transition-timing-function "cubic-bezier(0.4,0,0.2,1)"
                 :transition-duration "150ms"}))}


   {:id :transition-duration
    :since-version [:tw 2]
    :rules "
    transition-duration = <'duration-'> (number | time)
    "
    :garden (fn [{[duration] :component-data}]
              {:transition-duration (value-unit->css duration {:zero-unit "s"
                                                               :number {:unit "ms"}})})}


   {:id :transition-timing-function
    :since-version [:tw 2]
    :rules "
    transition-timing-function = <'ease-'> ('linear' | 'in' | 'out' | 'in-out')
    "
    :garden (fn [{[type] :component-data}]
              {:transition-timing-function ({"linear" "linear"
                                             "in" "cubic-bezier(0.4,0,1,1)"
                                             "out" "cubic-bezier(0,0,0.2,1)"
                                             "in-out" "cubic-bezier(0.4,0,0.2,1)"} type)})}


   {:id :transition-delay
    :since-version [:tw 2]
    :rules "
    transition-delay = <'delay-'> (number | time)
    "
    :garden (fn [{[duration] :component-data}]
              {:transition-delay (value-unit->css duration {:zero-unit "s"
                                                            :number {:unit "ms"}})})}


   {:id :animation
    :since-version [:tw 2]
    :rules "
    animation = <'animate-'> ('none' | 'spin' | 'ping' | 'pulse' | 'bounce')
    "
    ;; TODO: keyframes should be expressed outside of any prefix's scope
    ;; It is time to introduce grammar component's dependencies
    :garden (fn [{[type] :component-data}]
              (case type
                "none"
                {:animation "none"}

                "spin"
                [{:animation "spin 1s linear infinite"}
                 (gs/at-keyframes
                   "spin"
                   [:from {:transform "rotate(0)"}]
                   [:to {:transform "rotate(360deg)"}])]

                "ping"
                [{:animation "ping 1s cubic-bezier(0,0,0.2,1) infinite"}
                 (gs/at-keyframes
                   "ping"
                   ["75%" "100%" {:transform "scale(2)"
                                  :opacity 0}])]

                "pulse"
                [{:animation "pulse 2s cubic-bezier(0.4,0,0.6,1) infinite"}
                 (gs/at-keyframes
                    "pulse"
                    ["0%" "100%" {:opacity 1}]
                    ["50%" {:opacity 0.5}])]

                "bounce"
                [{:animation "bounce 1s infinite"}
                 (gs/at-keyframes
                    "bounce"
                    ["0%" "100%" {:transform "translateY(-25%)"
                                  :animation-timing-function "cubic-bezier(0.8,0,1,1)"}]
                    ["50%" {:transform "translateY(0)"
                            :animation-timing-function "cubic-bezier(0,0,0.2,1)"}])]))}])
