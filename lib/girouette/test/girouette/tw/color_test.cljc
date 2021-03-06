(ns girouette.tw.color-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.color :refer [read-color default-color-map color->css]]))

(deftest read-color-test
  (are [color-data expected-output]
    (= expected-output (read-color default-color-map color-data))

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

    [:color [:special-color "transparent"]]
    "transparent"

    [:color [:special-color "current"]]
    "currentColor"

    [:color [:predefined-color-opacity "green-300"]]
    [134 239 172 nil]

    [:color [:predefined-color-opacity "green-300" [:integer "50"]]]
    [134 239 172 127]

    [:color [:predefined-color-opacity "green-300" [:number "95_5"]]]
    [134 239 172 243]))
