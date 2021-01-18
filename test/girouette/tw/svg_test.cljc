(ns girouette.tw.svg-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "fill-current"
    [".fill-current" {:fill "currentColor"}]

    "stroke-current"
    [".stroke-current" {:stroke "currentColor"}]

    "stroke-0"
    [".stroke-0" {:stroke-width "0"}]

    "stroke-3_5"
    [".stroke-3_5" {:stroke-width "3.5"}]))
