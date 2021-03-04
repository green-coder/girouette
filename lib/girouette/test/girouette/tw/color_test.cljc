(ns girouette.tw.color-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.color :refer [read-color color->css]]))

(deftest read-color-test
  (are [color-data expected-output]
    (= expected-output (read-color color-data))

    [:color [:color-rgb "123"]]
    [0x11 0x22 0x33 nil]

    [:color [:color-rgb "112233"]]
    [0x11 0x22 0x33 nil]

    [:color [:color-rgba "1234"]]
    [0x11 0x22 0x33 0x44]

    [:color [:color-rgba "11223344"]]
    [0x11 0x22 0x33 0x44]

    [:color [:color-hsl [:number "0"]
                        [:number "100"]
                        [:number "50"]]]
    [0xff 0 0 nil]

    [:color [:color-hsl [:number "120"]
                        [:number "100"]
                        [:number "50"]]]
    [0 0xff 0 nil]

    [:color [:color-hsl [:number "-120"]
                        [:number "100"]
                        [:number "50"]]]
    [0 0 0xff nil]

    [:color [:color-hsla [:number "0"]
                         [:number "100"]
                         [:number "50"]
                         [:number "0"]]]
    [0xff 0 0 0]

    [:color [:color-hsla [:number "0"]
                         [:number "100"]
                         [:number "50"]
                         [:number "50"]]]
    [0xff 0 0 0x7f]

    [:color [:color-hsla [:number "0"]
                         [:number "100"]
                         [:number "50"]
                         [:number "100"]]]
    [0xff 0 0 0xff]

    [:color [:default-color-single-name "transparent"]]
    "transparent"

    [:color [:default-color-single-name "current"]]
    "currentColor"

    [:color [:default-color-darkness-opacity "green" "300"]]
    [134 239 172 nil]

    [:color [:default-color-darkness-opacity "green" "300" [:integer "50"]]]
    [134 239 172 127]

    [:color [:default-color-darkness-opacity "green" "300" [:number "100"]]]
    [134 239 172 255]))
