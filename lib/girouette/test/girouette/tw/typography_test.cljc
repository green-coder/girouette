(ns girouette.tw.typography-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.typography :refer [placeholder-pseudo-element]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "font-sans"
    [".font-sans" {:font-family "ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, \"Noto Sans\", sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\""}]

    "text-sm"
    [".text-sm" {:font-size (str 0.875 "rem")
                 :line-height (str 1.25 "rem")}]

    "font-size-10"
    [".font-size-10" {:font-size "2.5rem"}]

    "font-size-10%"
    [".font-size-10\\%" {:font-size "10%"}]

    "font-size-10vw"
    [".font-size-10vw" {:font-size "10vw"}]

    "antialiased"
    [".antialiased" {:-webkit-font-smoothing "antialiased"
                     :-moz-osx-font-smoothing "grayscale"}]

    "italic"
    [".italic" {:font-style "italic"}]

    "font-thin"
    [".font-thin" {:font-weight 100}]

    "tracking-wide"
    [".tracking-wide" {:letter-spacing (str 0.025 "em")}]

    "leading-0"
    [".leading-0" {:line-height 0}]

    "leading-3"
    [".leading-3" {:line-height (str 0.75 "rem")}]

    "leading-normal"
    [".leading-normal" {:line-height 1.5}]

    "line-height-0"
    [".line-height-0" {:line-height 0}]

    "line-height-0pt"
    [".line-height-0pt" {:line-height "0pt"}]

    "line-height-1_2"
    [".line-height-1_2" {:line-height 1.2}]

    "line-height-1/2"
    [".line-height-1\\/2" {:line-height "50%"}]

    "line-height-80%"
    [".line-height-80\\%" {:line-height "80%"}]

    "placeholder-current"
    [".placeholder-current" [placeholder-pseudo-element
                             {:color "currentColor"}]]

    "placeholder-green-300"
    [".placeholder-green-300" [placeholder-pseudo-element
                               {:--gi-placeholder-opacity 1
                                :color "rgba(134,239,172,var(--gi-placeholder-opacity))"}]]

    "placeholder-green-300-50"
    [".placeholder-green-300-50" [placeholder-pseudo-element
                                  {:color "#86efac7f"}]]

    "placeholder-opacity-20"
    [".placeholder-opacity-20" {:--gi-placeholder-opacity 0.2}]

    "text-black"
    [".text-black" {:--gi-text-opacity 1
                    :color "rgba(0,0,0,var(--gi-text-opacity))"}]

    "text-current"
    [".text-current" {:color "currentColor"}]

    "text-green-300"
    [".text-green-300" {:--gi-text-opacity 1
                        :color "rgba(134,239,172,var(--gi-text-opacity))"}]

    "text-green-300-50"
    [".text-green-300-50" {:color "#86efac7f"}]

    "text-#733"
    [".text-#733" {:--gi-text-opacity 1
                   :color "rgba(119,51,51,var(--gi-text-opacity))"}]

    "text-#7338"
    [".text-#7338" {:color "#77333388"}]

    "text-#703030ff"
    [".text-#703030ff" {:color "#703030ff"}]

    "text-rgb-733"
    [".text-rgb-733" {:--gi-text-opacity 1
                      :color "rgba(119,51,51,var(--gi-text-opacity))"}]

    "text-rgba-7338"
    [".text-rgba-7338" {:color "#77333388"}]

    "text-rgba-703030ff"
    [".text-rgba-703030ff" {:color "#703030ff"}]

    "text-hsl-0-100-50"
    [".text-hsl-0-100-50" {:--gi-text-opacity 1
                           :color "rgba(255,0,0,var(--gi-text-opacity))"}]

    "text-hsla-0-100-50-50"
    [".text-hsla-0-100-50-50" {:color "#ff00007f"}]

    "text-opacity-0"
    [".text-opacity-0" {:--gi-text-opacity 0}]

    "text-opacity-5"
    [".text-opacity-5" {:--gi-text-opacity 0.05}]

    "text-opacity-100"
    [".text-opacity-100" {:--gi-text-opacity 1}]))
