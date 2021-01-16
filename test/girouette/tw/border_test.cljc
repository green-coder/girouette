(ns girouette.tw.border-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    ;; Border Radius – https://tailwindcss.com/docs/border-radius

      ;"rounded-none"
      ;[".rounded-none" {:border-radius "0px"}]
      ;
      ;"rounded"
      ;[".rounded" {:border-radius "0.25rem"}]
      ;
      ;"rounded-2xl"
      ;[".rounded-2xl" {:border-radius "1rem"}]
      ;
      ;"rounded-t-sm"
      ;[".rounded-t-sm" {:border-top-left-radius  "0.125rem"
      ;                  :border-top-right-radius "0.125rem"}]
      ;
      ;"rounded-tl-full"
      ;[".rounded-tl-full" {:border-top-left-radius "9999px"}])

    ;; Border Width - https://tailwindcss.com/docs/border-width

    "border-2"
    [".border-2" {:border-width "2px"}]

    "border"
    [".border" {:border-width "1px"}]

    "border-t"
    [".border-t" {:border-top-width "1px"}]

    "border-l-4"
    [".border-l-4" {:border-left-width "4px"}]

    "border-b-2em"
    [".border-b-2em" {:border-bottom-width "2em"}]

    ;; Border Color
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






