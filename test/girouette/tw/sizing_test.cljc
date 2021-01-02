(ns girouette.tw.sizing-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.core :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= (class-name->garden class-name) expected-garden)

    "w-10"
    [".w-10" {:width "2.5rem"}]

    "w-px"
    [".w-px" {:width "1px"}]

    "w-30%"
    [".w-30\\%" {:width "30%"}]

    "w-1/4"
    [".w-1\\/4" {:width "25%"}]

    "w-auto"
    [".w-auto" {:width "auto"}]

    "w-full"
    [".w-full" {:width "100%"}]

    "w-screen"
    [".w-screen" {:width "100vw"}]

    "w-min"
    [".w-min" {:width "min-content"}]

    "w-max"
    [".w-max" {:width "max-content"}]

    "min-w-auto"
    [".min-w-auto" {:width "auto"}]

    "min-w-full"
    [".min-w-full" {:width "100%"}]

    "min-w-min"
    [".min-w-min" {:width "min-content"}]

    "min-w-max"
    [".min-w-max" {:width "max-content"}]

    "max-w-none"
    [".max-w-none" {:width "none"}]

    "max-w-full"
    [".max-w-full" {:width "100%"}]

    "max-w-min"
    [".max-w-min" {:width "min-content"}]

    "max-w-max"
    [".max-w-max" {:width "max-content"}]))

