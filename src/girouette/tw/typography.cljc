(ns girouette.tw.typography
  (:require [garden.selectors :refer [defpseudoelement]]
            [girouette.tw.common :refer [value-unit->css div-100]]
            [girouette.tw.color :refer [read-color color->css]]))


(defpseudoelement
  ^{:doc "CSS ::placeholder pseudo element selector."}
  placeholder-pseudo-element)


(def components
  [{:id :font-family
    :rules "
    font-family = 'font-sans' | 'font-serif' | 'font-mono'
    "
    :garden (fn [{[font-type] :component-data}]
              {:font-family (case font-type
                              "font-sans" "ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, \"Noto Sans\", sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\""
                              "font-serif" "ui-serif, Georgia, Cambria, \"Times New Roman\", Times, serif"
                              "font-mono" "ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, \"Liberation Mono\", \"Courier New\", monospace")})}


   {:id :font-size
    :rules "
    font-size = <'text-'> ('xs' | 'sm' | 'base' | 'lg' | 'xl' | '2xl' | '3xl' |
                           '4xl' | '5xl' | '6xl' | '7xl' | '8xl' | '9xl')
    "
    :garden (fn [{[font-size] :component-data}]
              (let [[size height] ({"xs"   [0.75  1]
                                    "sm"   [0.875 1.25]
                                    "base" [1     1.5]
                                    "lg"   [1.125 1.75]
                                    "xl"   [1.25  1.75]
                                    "2xl"  [1.5   2]
                                    "3xl"  [1.875 2.25]
                                    "4xl"  [2.25  2.5]
                                    "5xl"  [3     1]
                                    "6xl"  [3.75  1]
                                    "7xl"  [4.5   1]
                                    "8xl"  [6     1]
                                    "9xl"  [8     1]} font-size)]
                   {:font-size (str size "rem")
                    :line-height (str height "rem")}))}


   {:id :font-smoothing
    :rules "
    font-smoothing = 'antialiased' | 'subpixel-antialised'
    "
    :garden (fn [{[smoothing] :component-data}]
              (case smoothing
                "antialiased" {:-webkit-font-smoothing "antialiased"
                               :-moz-osx-font-smoothing "grayscale"}
                "subpixel-antialised" {:-webkit-font-smoothing "auto"
                                       :-moz-osx-font-smoothing "auto"}))}


   {:id :font-style
    :rules "
    font-style = 'italic' | 'not-italic'
    "
    :garden (fn [{[value] :component-data}]
              {:font-style (case value
                             "italic" "italic"
                             "not-italic" "normal")})}


   {:id :font-weight
    :rules "
    font-weight = <'font-'> ('thin' | 'extralight' | 'light' | 'normal' |
                             'medium' | 'semibold' | 'bold' | 'extrabold' | 'black')
    "
    :garden (fn [{[weight] :component-data}]
              {:font-style ({"thin"       100
                             "extralight" 200
                             "light"      300
                             "normal"     400
                             "medium"     500
                             "semibold"   600
                             "bold"       700
                             "extrabold"  800
                             "black"      900} weight)})}


   {:id :font-variant-numeric
    :rules "
    font-variant-numeric = 'normal-nums' | 'ordinal' | 'slashed-zero' | 'lining-nums' |
                           'oldstyle-nums' | 'proportional-nums' | 'tabular-nums' |
                           'diagonal-franctions' | 'stacked-fractions'
    "
    :garden (fn [{[variant-numeric] :component-data}]
              {:font-variant-numeric variant-numeric})}


   {:id :letter-spacing
    :rules "
    letter-spacing = <'tracking-'> ('tighter' | 'tight' | 'normal' |
                                    'wide' | 'wider' | 'widest')
    "
    :garden (fn [{[size] :component-data}]
              {:letter-spacing ({"tighter" (str -0.05  "em")
                                 "tight"   (str -0.025 "em")
                                 "normal"  (str 0      "em")
                                 "wide"    (str 0.025  "em")
                                 "wider"   (str 0.05   "em")
                                 "widest"  (str 0.1    "em")} size)})}


   {:id :line-height
    :rules "
    line-height = <'leading-'> (#'[3-9]' | '10' | 'none' | 'tight' | 'snug' |
                                'normal' | 'relaxed' | 'loose')
    "
    :garden (fn [{[size] :component-data}]
              {:line-height ({"3"       (str 0.75 "rem")
                              "4"       (str 1    "rem")
                              "5"       (str 1.25 "rem")
                              "6"       (str 1.5  "rem")
                              "7"       (str 1.75 "rem")
                              "8"       (str 2    "rem")
                              "9"       (str 2.25 "rem")
                              "10"      (str 2.5  "rem")
                              "none"    1
                              "tight"   1.25
                              "snug"    1.375
                              "normal"  1.5
                              "relaxed" 1.625
                              "loose"   2} size)})}


   {:id :list-style-type
    :rules "
    list-style-type = <'list-'> ('none' | 'disc' | 'decimal')
    "
    :garden (fn [{[type] :component-data}]
              {:list-style-type type})}


   {:id :list-style-position
    :rules "
    list-style-position = <'list-'> ('inside' | 'outside')
    "
    :garden (fn [{[position] :component-data}]
              {:list-style-position position})}


   {:id :placeholder-color
    :rules "
    placeholder-color = <'placeholder-'> color
    "
    :garden (fn [{[[_ color]] :component-data}]
              [placeholder-pseudo-element
               {:color (let [color (read-color color)]
                         (if (string? color)
                           color
                           (color->css color)))}])}


   {:id :text-align
    :rules "
    text-align = <'text-'> ('left' | 'center' | 'right' | 'justify')
    "
    :garden (fn [{[alignment] :component-data}]
              {:text-align alignment})}


   {:id :text-color
    :rules "
    text-color = <'text-'> color
    "
    :garden (fn [{[[_ color]] :component-data}]
              (let [color (read-color color)]
                (if (string? color)
                  {:color color}
                  (let [[r g b a] color]
                    (if (some? a)
                      {:color (color->css color)}
                      {:--gi-text-opacity 1
                       :color (color->css [r g b "var(--gi-text-opacity)"])})))))}


   {:id :text-opacity
    :rules "
    text-opacity = <'text-opacity-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-text-opacity (value-unit->css value {:value-fn div-100})})}


   {:id :text-decoration
    :rules "
    text-decoration = 'underline' | 'line-through' | 'no-underline'
    "
    :garden (fn [{[decoration] :component-data}]
              {:text-decoration ({"underline" "underline"
                                  "line-through" "line-through"
                                  "no-underline" "none"} decoration)})}


   {:id :text-transform
    :rules "
    text-transform = 'uppercase' | 'lowercase' | 'capitalize' | 'normal-case'
    "
    :garden (fn [{[transform] :component-data}]
              {:text-transform ({"uppercase" "uppercase"
                                 "lowercase" "lowercase"
                                 "capitalize" "capitalize"
                                 "normal-case" "none"} transform)})}


   {:id :text-overflow
    :rules "
    text-overflow = 'truncate' | 'overflow-ellipsis' | 'overflow-clip'
    "
    :garden (fn [{[overflow] :component-data}]
              (case overflow
                "truncate" {:overflow "hidden"
                            :text-overflow "ellipsis"
                            :white-space "nowrap"}
                "overflow-ellipsis" {:text-overflow "ellipsis"}
                "overflow-clip" {:text-overflow "clip"}))}


   {:id :vertical-alignment
    :rules "
    vertical-alignment = <'align-'> ('baseline' | 'top' | 'middle' | 'bottom' | 'text-top' | 'text-bottom')
    "
    :garden (fn [{[align] :component-data}]
              {:vertical-align align})}


   {:id :whitespace-control
    :rules "
    whitespace-control = <'whitespace-'> ('normal' | 'nowrap' | 'pre' | 'pre-line' | 'pre-wrap')
    "
    :garden (fn [{[whitespace-control] :component-data}]
              {:white-space whitespace-control})}


   {:id :word-break
    :rules "
    word-break = <'break-'> ('normal' | 'words' | 'all')
    "
    :garden (fn [{[break-type] :component-data}]
              (case break-type
                "normal" {:overflow-wrap "normal"
                          :word-break "normal"}
                "words" {:overflow-wrap "break-word"}
                "all" {:word-break "break-all"}))}])
