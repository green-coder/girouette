(ns girouette.tw.layout-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.core :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= (class-name->garden class-name) expected-garden)

    "container"
    [".container" {:width "100%"}]

    "sm:container"
    #garden.types.CSSAtRule {:identifier :media
                             :value {:media-queries {:min-width "640px"}
                                     :rules ([".sm\\:container" {:max-width "640px"}])}}

    "md:container"
    #garden.types.CSSAtRule {:identifier :media
                             :value {:media-queries {:min-width "768px"}
                                     :rules ([".md\\:container" {:max-width "768px"}])}}

    "box-content"
    [".box-content" {:box-sizing "content-box"}]

    "inset-x-auto"
    [".inset-x-auto" {:left  "auto"
                      :right "auto"}]

    "top-0"
    [".top-0" {:top "0px"}]

    "top-0.5"
    [".top-0\\.5" {:top "0.125rem"}]

    "top-0_5"
    [".top-0_5" {:top "0.125rem"}]

    "top-1"
    [".top-1" {:top "0.25rem"}]

    "top-1px"
    [".top-1px" {:top "1px"}]

    "top-1rem"
    [".top-1rem" {:top "1rem"}]

    "top-1%"
    [".top-1\\%" {:top "1%"}]

    "z-0"
    [".z-0" {:z-index "0"}]))
