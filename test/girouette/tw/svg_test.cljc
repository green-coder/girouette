(ns girouette.tw.svg-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.core :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "p-0"
    [".p-0" {:padding "0"}]))
