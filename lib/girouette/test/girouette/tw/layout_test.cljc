(ns girouette.tw.layout-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [tw-v3-class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (tw-v3-class-name->garden class-name))

    ;; Aspect Ratio
    "aspect-auto"
    [".aspect-auto" {:aspect-ratio "auto"}]

    "aspect-square"
    [".aspect-square" {:aspect-ratio "1 / 1"}]

    "aspect-video"
    [".aspect-video" {:aspect-ratio "16 / 9"}]

    "aspect-23/57"
    [".aspect-23\\/57" {:aspect-ratio "23 / 57"}]

    ;; Container
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

    ;; Columns
    "columns-4"
    [".columns-4" {:columns "4"}]

    "columns-auto"
    [".columns-auto" {:columns "auto"}]

    "columns-2xl"
    [".columns-2xl" {:columns "42rem"}]

    "columns-2-auto"
    [".columns-2-auto" {:columns "2 auto"}]

    "columns-2-300px"
    [".columns-2-300px" {:columns "2 300px"}]

    "columns-auto-auto"
    [".columns-auto-auto" {:columns "auto auto"}]

    ;; Break after
    "break-after-auto"
    [".break-after-auto" {:break-after "auto"}]

    ;; Break before
    "break-before-page"
    [".break-before-page" {:break-before "page"}]

    ;; Break inside
    "break-inside-avoid-column"
    [".break-inside-avoid-column" {:break-inside "avoid-column"}]

    ;; Box decoration break
    "box-decoration-clone"
    [".box-decoration-clone" {:box-decoration-break "clone"}]

    "box-decoration-slice"
    [".box-decoration-slice" {:box-decoration-break "slice"}]

    ;; Box sizing
    "box-content"
    [".box-content" {:box-sizing "content-box"}]

    ;; Display
    "flex"
    [".flex" {:display "flex"}]

    "sm:flex"
    #garden.types.CSSAtRule {:identifier :media
                             :value      {:media-queries {:min-width "640px"}
                                          :rules ([".sm\\:flex"
                                                   {:display "flex"}])}}

    "inline-table"
    [".inline-table" {:display "inline-table"}]

    "list-item"
    [".list-item" {:display "list-item"}]

    "hidden"
    [".hidden" {:display "none"}]

    ;; Floats

    ;; Clear

    ;; Isolation
    "isolate"
    [".isolate" {:isolation "isolate"}]

    "isolation-auto"
    [".isolation-auto" {:isolation "auto"}]

    ;; Object fit

    ;; Object position
    "object-left-top"
    [".object-left-top" {:object-position "left top"}]

    ;; Overflow
    "overflow-hidden"
    [".overflow-hidden" {:overflow "hidden"}]

    "overflow-clip"
    [".overflow-clip" {:overflow "clip"}]

    "overflow-x-auto"
    [".overflow-x-auto" {:overflow-x "auto"}]

    "overflow-x-clip"
    [".overflow-x-clip" {:overflow-x "clip"}]

    ;; Overscroll behavior
    "overscroll-x-auto"
    [".overscroll-x-auto" {:overscroll-x "auto"}]

    "overscroll-none"
    [".overscroll-none" {:overscroll "none"}]

    ;; Position

    ;; (Positioning) Top / Right / Bottom / Left
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
    [".top-0" {:top 0}]

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

    ;; Visibility
    "invisible"
    [".invisible" {:visibility "hidden"}]

    ;; Z-index
    "z-0"
    [".z-0" {:z-index 0}]

    "z-1"
    [".z-1" {:z-index 1}]

    "z-auto"
    [".z-auto" {:z-index "auto"}]))
