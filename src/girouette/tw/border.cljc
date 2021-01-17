(ns girouette.tw.border
    (:require [girouette.tw.common :refer [value-unit->css]]
              [girouette.tw.color :refer [read-color as-transparent color->css]]))

(def components
  [{:id     :border-radius
    :rules  "
    border-radius = <'rounded'>  (<'-'> border-radius-position)? (<'-'> border-radius-size)?
    border-radius-position  = border-radius-side | border-radius-corner
    border-radius-side = 't' | 'r' | 'b' | 'l'
    border-radius-corner = 'tl' | 'tr' | 'br' | 'bl'
    border-radius-size = none | full | media-query-min-width
    "
    :garden (fn [{:keys [component-data]}]
              (let [{position :border-radius-position
                     size     :border-radius-size} (into {} component-data)
                    {side   :border-radius-side
                     corner :border-radius-corner} (into {} [position]) ;; TODO: find more idiomatic way
                    [size-unit val] size
                    radius (case size-unit
                             :none "0px"
                             :full "9999px"
                             (:media-query-min-width nil) (str (* 0.25 (case val
                                                                         "sm" 0.5
                                                                         "md" 1.5
                                                                         "lg" 2
                                                                         "xl" 3
                                                                         "2xl" 4
                                                                         1)) "rem"))]
                (if (nil? side)
                  (let [css-prop (case corner
                                   "tl" :border-top-left-radius
                                   "tr" :border-top-right-radius
                                   "br" :border-bottom-right-radius
                                   "bl" :border-bottom-left-radius
                                   :border-radius)]
                    {css-prop radius})
                  (let [css-props (case side
                                    "t" [:border-top-left-radius :border-top-right-radius]
                                    "r" [:border-top-right-radius :border-bottom-right-radius]
                                    "b" [:border-bottom-right-radius :border-bottom-left-radius]
                                    "l" [:border-bottom-left-radius :border-top-left-radius])]
                    (into {} (map (juxt identity (constantly radius)) css-props))))))}

   {:id     :border-width
    :rules  "
    border-width = <'border'> (<'-'> direction)? (<'-'> border-width-value)?
    border-width-value = number | length | length-unit
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [border-width-value direction]} (into {} component-data)
                    css-prop (case direction
                               "t" :border-top-width
                               "r" :border-right-width
                               "b" :border-bottom-width
                               "l" :border-left-width
                               :border-width)]
                {css-prop (if (nil? border-width-value)
                            "1px"
                            (value-unit->css nil border-width-value {:number-unit "px"}))}))}
   {:id :border-color
    :rules "
    border-color = <'border-'> color
    "
    ;; Copy-pasted from background; could be extracted into util?
    :garden (fn [{[[_ color]] :component-data}]
              (let [color (read-color color)]
                (if (string? color)
                  {:border-color color}
                  (let [[r g b a] color]
                    (if (some? a)
                      {:border-color (color->css color)}
                      {:--gi-border-opacity 1
                       :border--color (color->css [r g b "var(--gi-border-opacity)"])})))))}

   {:id :border-opacity
    :rules "
    border-opacity = <'border-opacity-'> number
    "
    :garden (fn [] nil)}
   {:id :border-style
    :rules "
    border-style = <'border-'> border-style-name
    border-style-name = 'solid' | 'dashed' | 'dotted' | 'double' | 'none'
    "
    :garden (fn [] nil)}
   {:id :divide-width
    :rules "
    divide-width = <'divide-'> axis (<'-'> divide-width-value)?
    divide-width-value = number | length | length-unit
    "
    :garden (fn [] nil)}
   {:id :divide-color
    :rules "
    divide-color = <'divide-'> color
    "
    :garden (fn [] nil)}
   {:id :divide-opacity
    :rules "
    divide-opacity = <'divide-'> number
    "
    :garden (fn [] nil)}
   {:id :divide-style
    :rules "
    divide-style = <'divide-style-'> number <'-'> border-style-name
    "
    :garden (fn [] nil)}
   {:id :ring-width
    :rules "
    ring-width = <'ring'> (<'-'> ring-width-size)?
    ring-width-size = number | 'inset'
    "
    :garden (fn [] nil)}
   {:id :ring-color
    :rules "
    ring-color = <'ring-'> color
    "
    :garden (fn [] nil)}
   {:id :ring-opacity
    :rules "
    ring-opacity = <'ring-opacity-'> number
    "
    :garden (fn [] nil)}
   {:id :ring-offset-width
    :rules "
    ring-offset-width = <'ring-offset-'> number
    "
    :garden (fn [] nil)}
   {:id :ring-offset-color
    :rules "
    ring-offset-color = <'ring-offset-'> color
    "
    :garden (fn [] nil)}])



