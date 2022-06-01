(ns girouette.tw.table-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [tw-v3-class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (tw-v3-class-name->garden class-name))

    "border-separate"
    [".border-separate" {:border-collapse "separate"}]

    "table-auto"
    [".table-auto" {:table-layout "auto"}]))
