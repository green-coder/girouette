(ns ^:no-doc girouette.tw.typography
  (:require [clojure.string :as str]
            [garden.selectors :refer [defpseudoelement]]
            [girouette.tw.common :refer [matches-nothing value-unit->css div-4 mul-100 div-100]]
            [girouette.tw.color :refer [color->css]]))


(defpseudoelement
  ^{:doc "CSS ::placeholder pseudo element selector."}
  placeholder-pseudo-element)


(def default-font-family-map
  {"sans" "ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, \"Noto Sans\", sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\""
   "serif" "ui-serif, Georgia, Cambria, \"Times New Roman\", Times, serif"
   "mono" "ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, \"Liberation Mono\", \"Courier New\", monospace"})


(defn font-family-rules [font-family-map]
  (let [font-family-names (if (seq font-family-map)
                            (->> (keys font-family-map)
                                 (map (fn [font-family-name] (str "'" font-family-name "'")))
                                 (str/join " | "))
                            matches-nothing)]
    (str "
  <font-family-name> = " font-family-names "
")))


(def components
  [{:id :content
    :rules "
    content = <'content-'> <'['> #'[^\\] ]*' <']'>
    "
    :garden (fn [{[value] :component-data}]
              {:content (-> value
                            ;; negative-lookbehind isn't supported in javascript
                            (str/replace #"(^|[^\\])'" "$1\"")
                            (str/replace #"\\'" "'")
                            (str/replace #"(^|[^\\])_" "$1 ")
                            (str/replace #"\\_" "_"))})}


   {:id :font-family
    :rules "
    font-family = <'font-'> font-family-name
    "
    :garden (fn [{font-family-map :font-family-map
                  [font-family-name] :component-data}]
              {:font-family (font-family-map font-family-name)})}


   {:id :font-size
    :rules "
    font-size = <'text-'> ('xs' | 'sm' | 'base' | 'lg' | 'xl' | '2xl' | '3xl' |
                           '4xl' | '5xl' | '6xl' | '7xl' | '8xl' | '9xl')
    "
    :garden (fn [{[font-size] :component-data}]
             (let [[size height] ({"xs"   [0.75  "1rem"]
                                   "sm"   [0.875 "1.25rem"]
                                   "base" [1     "1.5rem"]
                                   "lg"   [1.125 "1.75rem"]
                                   "xl"   [1.25  "1.75rem"]
                                   "2xl"  [1.5   "2rem"]
                                   "3xl"  [1.875 "2.25rem"]
                                   "4xl"  [2.25  "2.5rem"]
                                   "5xl"  [3     "1"]
                                   "6xl"  [3.75  "1"]
                                   "7xl"  [4.5   "1"]
                                   "8xl"  [6     "1"]
                                   "9xl"  [8     "1"]} font-size)]
                  {:font-size (str size "rem")
                   :line-height height}))}


   ;; This is an extra, not from Tailwind.
   {:id :font-size-2
    :rules "
    font-size-2 = <'font-size-'> (number | length | fraction | percentage)
    "
    :garden (fn [{[value-data] :component-data}]
              {:font-size (value-unit->css value-data
                                           {:number {:unit "rem"
                                                     :value-fn div-4}
                                            :fraction {:unit "%"
                                                       :value-fn mul-100}})})}


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
              {:font-weight ({"thin"       100
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
                           'diagonal-fractions' | 'stacked-fractions'
    "
    :garden (fn [{[variant-numeric] :component-data}]
              {:font-variant-numeric variant-numeric})}

   {:id :letter-spacing
    :rules "
    letter-spacing = <'tracking-'> ('tighter' | 'tight' | 'normal' |
                                    'wide' | 'wider' | 'widest')
    "
    :garden (fn [{[size] :component-data}]
              {:letter-spacing (str ({"tighter" -0.05
                                      "tight"   -0.025
                                      "normal"  0
                                      "wide"    0.025
                                      "wider"   0.05
                                      "widest"  0.1} size)
                                    "em")})}


   {:id :line-height
    :rules "
    line-height = <'leading-'> (line-height-size-name | number)
    line-height-size-name = 'none' | 'tight' | 'snug' | 'normal' | 'relaxed' | 'loose'
    "
    :garden (fn [{[value-data] :component-data}]
              {:line-height (if (= (first value-data) :line-height-size-name)
                              ({"none"    1
                                "tight"   1.25
                                "snug"    1.375
                                "normal"  1.5
                                "relaxed" 1.625
                                "loose"   2} (second value-data))
                              (value-unit->css value-data
                                               {:zero-unit nil
                                                :number {:unit "rem"
                                                         :value-fn div-4}}))})}


   ;; This is an extra, not from Tailwind.
   {:id :line-height-2
    :rules "
    line-height-2 = <'line-height-'> (number | length | fraction | percentage)
    "
    :garden (fn [{[value-data] :component-data}]
              {:line-height (value-unit->css value-data
                                             {:fraction {:unit "%"
                                                         :value-fn mul-100}})})}


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
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              [placeholder-pseudo-element
               (let [color (read-color color)]
                 (if (string? color)
                   {:color color}
                   (let [[r g b a] color]
                     (if (some? a)
                       {:color (color->css color)}
                       {:--gi-placeholder-opacity 1
                        :color (color->css [r g b "var(--gi-placeholder-opacity)"])}))))])
    :before-rules #{:placeholder-opacity}}


   {:id :placeholder-opacity
    :rules "
    placeholder-opacity = <'placeholder-opacity-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-placeholder-opacity (value-unit->css value {:value-fn div-100})})}


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
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              (let [color (read-color color)]
                (if (string? color)
                  {:color color}
                  (let [[r g b a] color]
                    (if (some? a)
                      {:color (color->css color)}
                      {:--gi-text-opacity 1
                       :color (color->css [r g b "var(--gi-text-opacity)"])})))))
    :before-rules #{:text-opacity}}


   {:id :text-indent
    :rules "
    text-indent = <'indent-'> ( number | length | fraction )
    "
    :garden (fn [{[value-data] :component-data}]
              {:text-indent (value-unit->css value-data
                                             {:zero-unit "px"
                                              :number {:unit "rem"
                                                       :value-fn div-4}
                                              :fraction {:unit "%"
                                                         :value-fn mul-100}})})}


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

   {:id :text-decoration-color
    :rules "
    text-decoration-color = <'decoration-'> ( 'inherit' | color )
    "
    :garden (fn [{[value] :component-data read-color :read-color}]
              {:text-decoration-color (if (= value "inherit")
                                        value
                                        (color->css (read-color value)))})}

   {:id :text-decoration-style
    :rules "
    text-decoration-style = <'decoration-'> ( 'solid' | 'double' | 'dotted' |
                                              'dashed' | 'wavy' )
    "
    :garden (fn [{[value] :component-data}]
              {:text-decoration-style value})}

   {:id :text-decoration-thickness
    :rules "
    text-decoration-thickness = <'decoration-'> ( text-decoration-thickness-from | text-decoration-thickness-value )
    text-decoration-thickness-from = 'from-font'
    text-decoration-thickness-value = auto | number | length | fraction | percentage
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [text-decoration-thickness-from
                            text-decoration-thickness-value]}
                    (into {} component-data)]
                {:text-decoration-thickness
                 (if text-decoration-thickness-from
                   "from-font"
                   (value-unit->css text-decoration-thickness-value
                                    {:number {:unit "px"}
                                     :fraction {:unit "%"
                                                :value-fn mul-100}}))}))}

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
    text-overflow = 'truncate' | 'text-ellipsis' | 'text-clip'
    "
    :garden (fn [{[overflow] :component-data}]
              (case overflow
                "truncate" {:overflow "hidden"
                            :text-overflow "ellipsis"
                            :white-space "nowrap"}
                "text-ellipsis" {:text-overflow "ellipsis"}
                "text-clip" {:text-overflow "clip"}))}

   {:id :text-underline-offset
    :rules "
    text-underline-offset = <'underline-'> (auto | number | length | fraction | percentage)
    "
    :garden (fn [{[value] :component-data}]
              {:text-underline-offset
               (value-unit->css value
                                {:number {:unit "px"}
                                 :fraction {:unit "%"
                                            :value-fn mul-100}})})}

   {:id :vertical-alignment
    :rules "
    vertical-alignment = <'align-'> ('baseline' | 'top' | 'middle' | 'bottom' | 'text-top' | 'text-bottom' | 'sub' | 'super')
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
