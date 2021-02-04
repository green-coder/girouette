(ns girouette.tw.color-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.color :refer [read-color color->css]]))

(deftest read-color-test
  (are [color-data expected-output]
    (= expected-output (read-color color-data))

    [:color [:rgb-color-code "123"]]
    [0x11 0x22 0x33 nil]

    [:color [:rgb-color-code "112233"]]
    [0x11 0x22 0x33 nil]

    [:color [:rgba-color-code "1234"]]
    [0x11 0x22 0x33 0x44]

    [:color [:rgba-color-code "11223344"]]
    [0x11 0x22 0x33 0x44]

    [:color [:default-color-single-name "transparent"]]
    "transparent"

    [:color [:default-color-single-name "current"]]
    "currentColor"

    [:color [:default-color-darkness-opacity "green" "300"]]
    [134 239 172 nil]

    [:color [:default-color-darkness-opacity "green" "300" [:integer "50"]]]
    [134 239 172 127]

    [:color [:default-color-darkness-opacity "green" "300" [:integer "100"]]]
    [134 239 172 255]))
