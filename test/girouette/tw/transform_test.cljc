(ns girouette.tw.transform-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "transform"
    [".transform" {:--gi-rotate 0
                   :--gi-scale-x 1
                   :--gi-scale-y 1
                   :--gi-skew-x 0
                   :--gi-skew-y 0
                   :--gi-translate-x 0
                   :--gi-translate-y 0
                   :transform (str "translateX(var(--gi-translate-x)) "
                                   "translateY(var(--gi-translate-y)) "
                                   "rotate(var(--gi-rotate)) "
                                   "skewX(var(--gi-skew-x)) "
                                   "skewY(var(--gi-skew-y)) "
                                   "scaleX(var(--gi-scale-x)) "
                                   "scaleY(var(--gi-scale-y))")}]

    "transform-none"
    [".transform-none" {:transform "none"}]

    "origin-top-right"
    [".origin-top-right" {:transform-origin "top right"}]

    "scale-10"
    [".scale-10" {:--gi-scale-x 0.1
                  :--gi-scale-y 0.1}]

    "scale-x-20"
    [".scale-x-20" {:--gi-scale-x 0.2}]

    "-scale-y-30"
    [".-scale-y-30" {:--gi-scale-y -0.3}]

    "rotate-0"
    [".rotate-0" {:--gi-rotate 0}]

    "rotate-45"
    [".rotate-45" {:--gi-rotate "45deg"}]

    "-rotate-45"
    [".-rotate-45" {:--gi-rotate "-45deg"}]

    "-rotate-2turn"
    [".-rotate-2turn" {:--gi-rotate "-2turn"}]

    "translate-x-0"
    [".translate-x-0" {:--gi-translate-x 0}]

    "translate-x-1"
    [".translate-x-1" {:--gi-translate-x "0.25rem"}]

    "translate-x-1_5"
    [".translate-x-1_5" {:--gi-translate-x "0.375rem"}]

    "translate-x-px"
    [".translate-x-px" {:--gi-translate-x "1px"}]

    "translate-y-1/2"
    [".translate-y-1\\/2" {:--gi-translate-y "50%"}]

    "translate-y-20%"
    [".translate-y-20\\%" {:--gi-translate-y "20%"}]

    "translate-y-full"
    [".translate-y-full" {:--gi-translate-y "100%"}]

    "skew-x-0"
    [".skew-x-0" {:--gi-skew-x 0}]

    "skew-y-10"
    [".skew-y-10" {:--gi-skew-y "10deg"}]

    "-skew-y-10"
    [".-skew-y-10" {:--gi-skew-y "-10deg"}]

    "-skew-y-0_5rad"
    [".-skew-y-0_5rad" {:--gi-skew-y "-0.5rad"}]))
