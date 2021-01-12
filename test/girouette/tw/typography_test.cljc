(ns girouette.tw.typography-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.core :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "font-sans"
    [".font-sans" {:font-family "ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, \"Noto Sans\", sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\""}]

    "text-sm"
    [".text-sm" {:font-size [[0.875 "rem"]], :line-height [[1.25 "rem"]]}]

    "antialiased"
    [".antialiased" {:-webkit-font-smoothing "antialiased", :-moz-osx-font-smoothing "grayscale"}]

    "italic"
    [".italic" {:font-style "italic"}]

    "font-thin"
    [".font-thin" {:font-style 100}]

    "tracking-wide"
    [".tracking-wide" {:letter-spacing [[0.025 "em"]]}]

    "leading-3"
    [".leading-3" {:line-height [[0.75 "rem"]]}]

    "leading-normal"
    [".leading-normal" {:line-height 1.5}]

    "text-black"
    [".text-black" {:--gi-text-opacity 1
                    :color "#000000ff"}]

    "text-current"
    [".text-current" {:color "currentColor"}]

    "text-green-300"
    [".text-green-300" {:--gi-text-opacity 1
                        :color "#86efacff"}]

    "text-rgb-733"
    [".text-rgb-733" {:--gi-text-opacity 1
                      :color "#703030ff"}]

    "text-rgba-7338"
    [".text-rgba-7338" {:--gi-text-opacity 0.5019607843137255
                        :color "#70303080"}]

    "text-rgba-703030ff"
    [".text-rgba-703030ff" {:--gi-text-opacity 1.0, :color "#703030ff"}]

    "text-opacity-0"
    [".text-opacity-0" {:--gi-text-opacity 0}]

    "text-opacity-5"
    [".text-opacity-5" {:--gi-text-opacity 0.05}]

    "text-opacity-100"
    [".text-opacity-100" {:--gi-text-opacity 1}]))
