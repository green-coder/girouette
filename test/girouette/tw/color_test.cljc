(ns girouette.tw.color-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.color :refer [read-color color->css]]))

(deftest read-color-test
  (are [color-data expected-output]
    (= expected-output (read-color color-data))

    [:default-color-without-darkness "transparent"]
    "transparent"

    [:default-color-without-darkness "current"]
    "currentColor"

    [:default-color-with-darkness "green" "300" [:integer "50"]]
    [134 239 172 127]

    [:default-color-with-darkness "green" "300" [:integer "100"]]
    [134 239 172 255]))
