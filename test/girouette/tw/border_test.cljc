(ns girouette.tw.border-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))
    ;; Border Radius – https://tailwindcss.com/docs/border-radius
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

    ;; Border Width - https://tailwindcss.com/docs/border-width

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
                          :border--color "rgba(134, 239, 172, var(--gi-border-opacity))"}]
    ;; Border Opacity
    ;; Border Style
    ;; Divide Width
    ;; Divide Color
    ;; Divide Opacity
    ;; Divide Style
    ;; Ring Width
    ;; Ring Color
    ;; Ring Opacity
    ;; Ring Offset Width
    ;; Ring Offset Color
    ,))






