(ns girouette.tw.typography-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.typography :refer [placeholder-pseudo-element]]
            [girouette.tw.default-api :refer [tw-v3-class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (tw-v3-class-name->garden class-name))

    ;; Font family
    "font-sans"
    [".font-sans" {:font-family "ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, \"Noto Sans\", sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\""}]

    ;; Font size
    "text-sm"
    [".text-sm" {:font-size (str 0.875 "rem")
                 :line-height (str 1.25 "rem")}]

    "text-5xl"
    [".text-5xl" {:font-size (str 3 "rem")
                  :line-height (str 1)}]

    ;; Font size 2
    "font-size-10"
    [".font-size-10" {:font-size "2.5rem"}]

    "font-size-10%"
    [".font-size-10\\%" {:font-size "10%"}]

    "font-size-10vw"
    [".font-size-10vw" {:font-size "10vw"}]

    ;; Font smoothing
    "antialiased"
    [".antialiased" {:-webkit-font-smoothing "antialiased"
                     :-moz-osx-font-smoothing "grayscale"}]

    ;; Font style
    "italic"
    [".italic" {:font-style "italic"}]

    ;; Font weight
    "font-thin"
    [".font-thin" {:font-weight 100}]

    ;; Font variant numeric

    ;; Letter spacing
    "tracking-wide"
    [".tracking-wide" {:letter-spacing (str 0.025 "em")}]

    ;; Line height
    "leading-0"
    [".leading-0" {:line-height 0}]

    "leading-3"
    [".leading-3" {:line-height (str 0.75 "rem")}]

    "leading-normal"
    [".leading-normal" {:line-height 1.5}]

    ;; Line height 2
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

    ;; List style type

    ;; List style position

    ;; Placeholder color
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

    "placeholder-green-300/50"
    [".placeholder-green-300\\/50" [placeholder-pseudo-element
                                    {:color "#86efac7f"}]]

    ;; Placeholder opacity
    "placeholder-opacity-20"
    [".placeholder-opacity-20" {:--gi-placeholder-opacity 0.2}]

    ;; Text align

    ;; Text color
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
    [".text-\\#733" {:--gi-text-opacity 1
                     :color "rgba(119,51,51,var(--gi-text-opacity))"}]

    "text-#7338"
    [".text-\\#7338" {:color "#77333388"}]

    "text-#703030ff"
    [".text-\\#703030ff" {:color "#703030ff"}]

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

    ;; Text opacity
    "text-opacity-0"
    [".text-opacity-0" {:--gi-text-opacity 0}]

    "text-opacity-5"
    [".text-opacity-5" {:--gi-text-opacity 0.05}]

    "text-opacity-100"
    [".text-opacity-100" {:--gi-text-opacity 1}]

    ;; Text decoration
    "underline"
    [".underline" {:text-decoration-line "underline"}]

    ;; Text decoration color
    "decoration-inherit"
    [".decoration-inherit" {:text-decoration-color "inherit"}]

    "decoration-orange-100"
    [".decoration-orange-100" {:text-decoration-color "#ffedd5"}]

    "decoration-#abcdef"
    [".decoration-\\#abcdef" {:text-decoration-color "#abcdef"}]

    ;; Text decoration style
    "decoration-wavy"
    [".decoration-wavy" {:text-decoration-style "wavy"}]

    ;; Text decoration thickness
    "decoration-auto"
    [".decoration-auto" {:text-decoration-thickness "auto"}]

    "decoration-from-font"
    [".decoration-from-font" {:text-decoration-thickness "from-font"}]

    "decoration-3"
    [".decoration-3" {:text-decoration-thickness "3px"}]

    "decoration-1rem"
    [".decoration-1rem" {:text-decoration-thickness "1rem"}]

    "decoration-1/5"
    [".decoration-1\\/5" {:text-decoration-thickness "20%"}]

    ;; Text underline offset
    "underline-auto"
    [".underline-auto" {:text-underline-offset "auto"}]

    "underline-3"
    [".underline-3" {:text-underline-offset "3px"}]

    "underline-1rem"
    [".underline-1rem" {:text-underline-offset "1rem"}]

    "underline-1/5"
    [".underline-1\\/5" {:text-underline-offset "20%"}]

    ;; Text transform

    ;; Text overflow
    "text-clip"
    [".text-clip" {:text-overflow "clip"}]

    ;; Text indent
    "indent-0"
    [".indent-0" {:text-indent "0px"}]

    "indent-1"
    [".indent-1" {:text-indent "0.25rem"}]

    "indent-px"
    [".indent-px" {:text-indent "1px"}]

    "indent-2em"
    [".indent-2em" {:text-indent "2em"}]

    ;; Vertical alignment
    "align-sub"
    [".align-sub" {:vertical-align "sub"}]

    ;; Whitespace control

    ;; Word break

    ;; Content

    "content-['foo']"
    [".content-\\[\\'foo\\'\\]" {:content "\"foo\""}]

    "content-['foo\\'s']"
    [".content-\\[\\'foo\\\\\\'s\\'\\]" {:content "\"foo's\""}]

    "content-['foo_bar']"
    [".content-\\[\\'foo_bar\\'\\]" {:content "\"foo bar\""}]

    "content-['foo\\_bar']"
    [".content-\\[\\'foo\\\\_bar\\'\\]" {:content "\"foo_bar\""}]

    "content-[attr(foo)]"
    [".content-\\[attr\\(foo\\)\\]" {:content "attr(foo)"}]))
