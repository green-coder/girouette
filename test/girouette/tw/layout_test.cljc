(ns girouette.tw.layout-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

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

    "flex"
    [".flex" {:display "flex"}]

    "hidden"
    [".hidden" {:display "none"}]

    "object-left-top"
    [".object-left-top" {:object-position "left top"}]

    "overflow-x-auto"
    [".overflow-x-auto" {:overflow-x "auto"}]

    "overflow-hidden"
    [".overflow-hidden" {:overflow "hidden"}]

    "overscroll-x-auto"
    [".overscroll-x-auto" {:overscroll-x "auto"}]

    "overscroll-none"
    [".overscroll-none" {:overscroll "none"}]

    "invisible"
    [".invisible" {:visibility "invisible"}]

    "-inset-3/8"
    [".-inset-3\\/8"
     {:bottom "-37.5%"
      :left   "-37.5%"
      :right  "-37.5%"
      :top    "-37.5%"}]

    "inset-x-auto"
    [".inset-x-auto" {:left  "auto"
                      :right "auto"}]

    "top-0"
    [".top-0" {:top "0"}]

    "top-0.5"
    [".top-0\\.5" {:top "0.125rem"}]

    "top-0_5"
    [".top-0_5" {:top "0.125rem"}]

    "top-1"
    [".top-1" {:top "0.25rem"}]

    "top-cm"
    [".top-cm" {:top "1cm"}]

    "top-1px"
    [".top-1px" {:top "1px"}]

    "top-1rem"
    [".top-1rem" {:top "1rem"}]

    "top-1%"
    [".top-1\\%" {:top "1%"}]

    "top-3/8"
    [".top-3\\/8" {:top "37.5%"}]

    "top-full"
    [".top-full" {:top "100%"}]

    "z-0"
    [".z-0" {:z-index "0"}]

    "z-1"
    [".z-1" {:z-index "1"}]

    "z-auto"
    [".z-auto" {:z-index "auto"}]))
