(ns girouette.tw.accessibility-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [tw-v3-class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (tw-v3-class-name->garden class-name))

    "sr-only"
    [".sr-only" {:position "absolute"
                 :width "1px"
                 :height "1px"
                 :padding 0
                 :margin "-1px"
                 :overflow "hidden"
                 :clip "rect(0,0,0,0)"
                 :white-space "nowrap"
                 :border-width 0}]))
