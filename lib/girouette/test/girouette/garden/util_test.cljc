(ns girouette.garden.util-test
  (:require [clojure.test :refer [deftest testing is are]]
            [garden.core :refer [css]]
            [girouette.tw.default-api :refer [class-name->garden]]
            [girouette.garden.util :refer [garden-comp]]))

(deftest garden-comp-test
  (are [class-name garden-rules expected-garden]
    (= expected-garden (garden-comp class-name garden-rules))

    "my-class"
    (mapv class-name->garden ["p-3"
                              "m-3"
                              "hover:p-4"
                              "sm:p-1"
                              "sm:m-1"
                              "sm:hover:p-2"])
    [[".my-class" {:padding "0.75rem"
                   :margin "0.75rem"}]
     [".my-class" [:&:hover {:padding "1rem"}]]
     #garden.types.CSSAtRule{:identifier :media,
                             :value      {:media-queries {:min-width "640px"}
                                          :rules         ([".my-class" {:padding "0.25rem"
                                                                        :margin "0.25rem"}]
                                                          [".my-class" [:&:hover {:padding "0.5rem"}]])}}]))
