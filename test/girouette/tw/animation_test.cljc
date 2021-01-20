(ns girouette.tw.animation-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "transition"
    [".transition"
     {:transition-property "background-color,border-color,color,fill,stroke,opacity,box-shadow,transform",
      :transition-timing-function "cubic-bezier(0.4,0,0.2,1)",
      :transition-duration "150ms"}]

    "transition-colors"
    [".transition-colors"
     {:transition-property "background-color,border-color,color,fill,stroke",
      :transition-timing-function "cubic-bezier(0.4,0,0.2,1)",
      :transition-duration "150ms"}]

    "duration-100"
    [".duration-100" {:transition-duration "100ms"}]

    "ease-linear"
    [".ease-linear" {:transition-timing-function "linear"}]

    "ease-in"
    [".ease-in" {:transition-timing-function "cubic-bezier(0.4,0,1,1)"}]

    "delay-100"
    [".delay-100" {:transition-delay "100ms"}]

    "animate-none"
    [".animate-none" {:animation "none"}]

    "animate-spin"
    [".animate-spin"
     [{:animation "spin 1s linear infinite"}
      #garden.types.CSSAtRule{:identifier :keyframes,
                              :value {:identifier "spin",
                                      :frames ([:from {:transform "rotate(0deg)"}] [:to {:transform "rotate(360deg)"}])}}]]

    "animate-ping"
    [".animate-ping"
     [{:animation "ping 1s cubic-bezier(0,0,0.2,1) infinite"}
      #garden.types.CSSAtRule{:identifier :keyframes,
                              :value {:identifier "ping", :frames (["75%" "100%" {:transform "scale(2)", :opacity 0}])}}]]

    "animate-pulse"
    [".animate-pulse"
     [{:animation "pulse 2s cubic-bezier(0.4,0,0.6,1) infinite"}
      #garden.types.CSSAtRule{:identifier :keyframes,
                              :value {:identifier "pulse", :frames (["0%" "100%" {:opacity 1}] ["50%" {:opacity 0.5}])}}]]

    "animate-bounce"
    [".animate-bounce"
     [{:animation "bounce 1s infinite"}
      #garden.types.CSSAtRule{:identifier :keyframes,
                              :value {:identifier "bounce",
                                      :frames (["0%"
                                                "100%"
                                                {:transform "translateY(-25%)",
                                                 :animationTimingFunction "cubic-bezier(0.8,0,1,1)"}]
                                               ["50%"
                                                {:transform "translateY(0)",
                                                 :animationTimingFunction "cubic-bezier(0,0,0.2,1)"}])}}]]))
