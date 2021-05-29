(ns girouette.garden.util-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]
            [girouette.tw.common :refer [dot]]
            [girouette.garden.util :refer [apply-class-rules]]))

(deftest apply-class-rules-test
  (are [target-class-name gi-class-names expected-garden]
       (= expected-garden
          (apply-class-rules (dot target-class-name)
                             (mapv class-name->garden gi-class-names)
                             (mapv dot gi-class-names)))

       "my-class"
       ["p-3"
        "m-3"
        "hover:p-4"
        "sm:p-1"
        "sm:m-1"
        "sm:hover:p-2"]
       '([".my-class" {:margin "0.75rem"
                       :padding "0.75rem"}]
         [".my-class" [:&:hover {:padding "1rem"}]]
         #garden.types.CSSAtRule {:identifier :media
                                  :value {:media-queries {:min-width "640px"}
                                          :rules [[".my-class" {:padding "0.25rem"
                                                                :margin "0.25rem"}]
                                                  [".my-class" [:&:hover {:padding "0.5rem"}]]]}})))
