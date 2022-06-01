(ns girouette.tw.filter-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.filter :as filter]
            [girouette.tw.default-api :refer [tw-v3-class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (tw-v3-class-name->garden class-name))

    ;; Blur
    "blur-none"
    [".blur-none" {:--gi-blur "blur(0)"
                   :filter @#'filter/filter-rule}]

    "blur"
    [".blur" {:--gi-blur "blur(8px)"
              :filter @#'filter/filter-rule}]

    "blur-lg"
    [".blur-lg" {:--gi-blur "blur(16px)"
                 :filter @#'filter/filter-rule}]

    "blur-1rem"
    [".blur-1rem" {:--gi-blur "blur(1rem)"
                   :filter @#'filter/filter-rule}]

    "blur-1px"
    [".blur-1px" {:--gi-blur "blur(1px)"
                  :filter @#'filter/filter-rule}]

    ;; Brightness
    "brightness-0"
    [".brightness-0" {:--gi-brightness "brightness(0)"
                      :filter @#'filter/filter-rule}]

    "brightness-75"
    [".brightness-75" {:--gi-brightness "brightness(0.75)"
                       :filter @#'filter/filter-rule}]

    "brightness-75%"
    [".brightness-75\\%" {:--gi-brightness "brightness(75%)"
                          :filter @#'filter/filter-rule}]

    "brightness-150"
    [".brightness-150" {:--gi-brightness "brightness(1.5)"
                        :filter @#'filter/filter-rule}]

    ;; Contrast
    "contrast-0"
    [".contrast-0" {:--gi-contrast "contrast(0)"
                    :filter @#'filter/filter-rule}]

    "contrast-75"
    [".contrast-75" {:--gi-contrast "contrast(0.75)"
                     :filter @#'filter/filter-rule}]

    "contrast-75%"
    [".contrast-75\\%" {:--gi-contrast "contrast(75%)"
                        :filter @#'filter/filter-rule}]

    "contrast-150"
    [".contrast-150" {:--gi-contrast "contrast(1.5)"
                      :filter @#'filter/filter-rule}]

    ;; Drop shadow
    "drop-shadow"
    [".drop-shadow"
     {:--gi-drop-shadow "drop-shadow(0 1px 2px rgb(0 0 0 / 0.1)) drop-shadow(0 1px 1px rgb(0 0 0 / 0.06))"
      :filter @#'filter/filter-rule}]

    "drop-shadow-lg"
    [".drop-shadow-lg"
     {:--gi-drop-shadow "drop-shadow(0 10px 8px rgb(0 0 0 / 0.04)) drop-shadow(0 4px 3px rgb(0 0 0 / 0.1))"
      :filter @#'filter/filter-rule}]

    ;; Grayscale
    "grayscale"
    [".grayscale" {:--gi-grayscale "grayscale(100%)"
                   :filter @#'filter/filter-rule}]

    "grayscale-0"
    [".grayscale-0" {:--gi-grayscale "grayscale(0)"
                     :filter @#'filter/filter-rule}]

    "grayscale-1/2"
    [".grayscale-1\\/2" {:--gi-grayscale "grayscale(0.5)"
                         :filter @#'filter/filter-rule}]

    "grayscale-50"
    [".grayscale-50" {:--gi-grayscale "grayscale(0.5)"
                      :filter @#'filter/filter-rule}]

    "grayscale-100%"
    [".grayscale-100\\%" {:--gi-grayscale "grayscale(100%)"
                          :filter @#'filter/filter-rule}]

    ;; Hue rotate
    "hue-rotate-30"
    [".hue-rotate-30" {:--gi-hue-rotate "hue-rotate(30deg)"
                       :filter @#'filter/filter-rule}]

    "hue-rotate-30deg"
    [".hue-rotate-30deg" {:--gi-hue-rotate "hue-rotate(30deg)"
                          :filter @#'filter/filter-rule}]

    "hue-rotate-0_5turn"
    [".hue-rotate-0_5turn" {:--gi-hue-rotate "hue-rotate(0.5turn)"
                            :filter @#'filter/filter-rule}]

    ;; Invert
    "invert"
    [".invert" {:--gi-invert "invert(100%)"
                :filter @#'filter/filter-rule}]

    "invert-0"
    [".invert-0" {:--gi-invert "invert(0)"
                  :filter @#'filter/filter-rule}]))

    ;; Saturate
    ;; Sepia

    ;; Backdrop blur
    ;; Backdrop brightness
    ;; Backdrop contrast
    ;; Backdrop gray scale
    ;; Backdrop hue rotate
    ;; Backdrop invert
    ;; Backdrop opacity
    ;; Backdrop saturate
    ;; Backdrop sepia

