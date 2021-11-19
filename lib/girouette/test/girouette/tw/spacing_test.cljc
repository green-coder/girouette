(ns girouette.tw.spacing-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "p-0"
    [".p-0" {:padding 0}]

    "-p-0"
    [".-p-0" {:padding 0}]

    "p-1"
    [".p-1" {:padding "0.25rem"}]

    "-p-1"
    [".-p-1" {:padding "-0.25rem"}]

    "p-2"
    [".p-2" {:padding "0.5rem"}]

    "p-px"
    [".p-px" {:padding "1px"}]

    "-p-px"
    [".-p-px" {:padding "-1px"}]

    "px-2"
    [".px-2" {:padding-left "0.5rem"
              :padding-right "0.5rem"}]

    "py-3"
    [".py-3" {:padding-top "0.75rem"
              :padding-bottom "0.75rem"}]

    "pt-1"
    [".pt-1" {:padding-top "0.25rem"}]

    "m-40"
    [".m-40" {:margin "10rem"}]

    "mx-auto"
    [".mx-auto" {:margin-left "auto"
                 :margin-right "auto"}]

    "-mx-2"
    [".-mx-2" {:margin-left "-0.5rem"
               :margin-right "-0.5rem"}]

    "space-x-2"
    [".space-x-2" [#garden.selectors.CSSSelector{:selector "&>*+*"} {:margin-left "0.5rem"}]]

    "space-y-2"
    [".space-y-2" [#garden.selectors.CSSSelector{:selector "&>*+*"} {:margin-top "0.5rem"}]]

    "space-x-2-reverse"
    [".space-x-2-reverse" [#garden.selectors.CSSSelector{:selector "&>*+*"} {:margin-right "0.5rem"}]]

    "space-y-2-reverse"
    [".space-y-2-reverse" [#garden.selectors.CSSSelector{:selector "&>*+*"} {:margin-bottom "0.5rem"}]]))
