(ns girouette.tw.border-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))
    ;; Border Radius
    "rounded-none"
    [".rounded-none" {:border-radius "0px"}]

    "rounded"
    [".rounded" {:border-radius "0.25rem"}]

    "rounded-xl"
    [".rounded-xl" {:border-radius "0.75rem"}]

    "rounded-t-sm"
    [".rounded-t-sm" {:border-top-left-radius  "0.125rem"
                      :border-top-right-radius "0.125rem"}]

    "rounded-tl-full"
    [".rounded-tl-full" {:border-top-left-radius "9999px"}]

    ;; Border Width
    "border"
    [".border" {:border-width "1px"}]

    "border-2"
    [".border-2" {:border-width "2px"}]

    "border-t"
    [".border-t" {:border-top-width "1px"}]

    "border-l-4"
    [".border-l-4" {:border-left-width "4px"}]

    "border-b-2em"
    [".border-b-2em" {:border-bottom-width "2em"}]

    "border-3px"
    [".border-3px" {:border-width "3px"}]

    ;; Border Color
    "border-transparent"
    [".border-transparent" {:border-color "transparent"}]

    "border-green-300"
    [".border-green-300" {:--gi-border-opacity 1
                          :border-color        "rgba(134,239,172,var(--gi-border-opacity))"}]

    ;; Border Opacity
    "border-opacity-25"
    [".border-opacity-25" {:--gi-border-opacity 0.25}]

    ;; Border Style
    "border-dotted"
    [".border-dotted" {:border-style "dotted"}]

    ;; Divide Width
    "divide-x"
    [".divide-x" [#garden.selectors.CSSSelector{:selector "&>*+*"}
     {:--gi-divide-x-reverse 0
      :border-right-width "calc(1px * calc(1 - var(--gi-divide-x-reverse)))"
      :border-left-width  "calc(1px * var(--gi-divide-x-reverse))"}]]

    "divide-y-2"
    [".divide-y-2" [#garden.selectors.CSSSelector{:selector "&>*+*"}
     {:--gi-divide-x-reverse 0
      :border-top-width    "calc(2px * calc(1 - var(--gi-divide-y-reverse)))"
      :border-bottom-width "calc(2px * var(--gi-divide-y-reverse))"}]]

    ;; Divide Color
    "divide-current"
    [".divide-current" [#garden.selectors.CSSSelector{:selector "&>*+*"}
     {:border-color "currentColor"}]]

    "divide-gray-100"
    [".divide-gray-100" [#garden.selectors.CSSSelector{:selector "&>*+*"}
     {:--gi-divide-opacity 1
      :border-color        "rgba(244,244,245,var(--gi-divide-opacity))"}]]

    ;; Divide Opacity
    "divide-opacity-70"
    [".divide-opacity-70" {:--gi-divide-opacity 0.7}]

    ;; Divide Style
    "divide-double"
    [".divide-double" [#garden.selectors.CSSSelector{:selector "&>*+*"}
     {:border-style "double"}]]

    ;; Ring Width
    ;; TODO: Test for `*	box-shadow: 0 0 #0000;`
    "ring"
    [".ring" {:box-shadow "var(--gi-ring-inset) 0 0 0 calc(3px + var(--gi-ring-offset-width)) var(--gi-ring-color)"}]
    "ring-4"
    [".ring-4" {:box-shadow "var(--gi-ring-inset) 0 0 0 calc(4px + var(--gi-ring-offset-width)) var(--gi-ring-color)"}]
    "ring-inset"
    [".ring-inset" {:--gi-ring-inset "inset"}]

    ;; Ring Color
    "ring-pink-400"
    [".ring-pink-400", {:--gi-ring-color "rgba(244,114,182,var(--gi-ring-opacity))"}]

    ;; Ring Opacity
    "ring-opacity-50"
    [".ring-opacity-50" {:--gi-ring-opacity 0.5}]

    ;; Ring Offset Width
    "ring-offset-1"
    [".ring-offset-1" {:--gi-ring-offset-width "1px"
                        :box-shadow             "0 0 0 var(--gi-ring-offset-width) var(--gi-ring-offset-color), var(--gi-ring-shadow)"}]

    ;; Ring Offset Color
    "ring-offset-yellow-300"
    [".ring-offset-yellow-300" {:--gi-ring-offset-color "rgba(253,224,71,var(--gi-ring-opacity))"
                                 :box-shadow             "0 0 0 var(--gi-ring-offset-width) var(--gi-ring-offset-color), var(--gi-ring-shadow)"}]))
