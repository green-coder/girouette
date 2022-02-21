(ns ^:no-doc girouette.tw.border
  (:require [girouette.tw.common :refer [value-unit->css div-100 between-children-selector]]
            [girouette.tw.color :refer [color->css]]))

(def components
  [{:id :border-radius
    :rules "
    border-radius = <'rounded'> (<'-'> border-radius-position)? (<'-'> border-radius-size)?
    border-radius-position  = 't' | 'r' | 'b' | 'l' | 'tl' | 'tr' | 'br' | 'bl'
    border-radius-size = 'none' | 'full' | 'sm' | 'md' | 'lg' | 'xl' | '2xl' | '3xl'
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [border-radius-position border-radius-size]} (into {} component-data)
                    radius (case border-radius-size
                             "none" "0px"
                             "full" "9999px"
                             (str (* 0.25 (case border-radius-size
                                            "sm" 0.5
                                            "md" 1.5
                                            "lg" 2
                                            "xl" 3
                                            "2xl" 4
                                            "3xl" 6
                                            1)) "rem"))
                    directions (case border-radius-position
                                 "t" ["top-left" "top-right"]
                                 "r" ["top-right" "bottom-right"]
                                 "b" ["bottom-right" "bottom-left"]
                                 "l" ["bottom-left" "top-left"]
                                 "tl" ["top-left"]
                                 "tr" ["top-right"]
                                 "br" ["bottom-right"]
                                 "bl" ["bottom-left"]
                                 nil)]
                (if (nil? directions)
                  {:border-radius radius}
                  (into {}
                        (map (fn [direction] [(keyword (str "border-" direction "-radius")) radius]))
                        directions))))}


   {:id :border-width
    :rules "
    border-width = <'border'> (<'-'> (axis | direction))? (<'-'> border-width-value)?
    border-width-value = number | length | length-unit
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [axis direction border-width-value]} (into {} component-data)
                    css-props (case direction
                                "t" [:border-top-width]
                                "r" [:border-right-width]
                                "b" [:border-bottom-width]
                                "l" [:border-left-width]
                                (case axis
                                  "x" [:border-right-width :border-left-width]
                                  "y" [:border-top-width :border-bottom-width]
                                  [:border-width]))]
                (into {}
                      (map (fn [css-prop]
                             [css-prop (if (nil? border-width-value)
                                         "1px"
                                         (value-unit->css border-width-value
                                                          {:zero-unit nil
                                                           :number {:unit "px"}}))]))
                      css-props)))}


   {:id :border-color
    :rules "
    border-color = <'border'> (<'-'> (axis | direction))? <'-'> color
    "
    ;; Copy-pasted from background; could be extracted into util?
    :garden (fn [{:keys [component-data read-color]}]
              (let [{:keys [axis direction color]} (into {} component-data)
                    color (read-color [:color color])
                    sides (case direction
                            "t" ["top"]
                            "r" ["right"]
                            "b" ["bottom"]
                            "l" ["left"]
                            (case axis
                              "x" ["right" "left"]
                              "y" ["top" "bottom"]
                              nil))
                    [result css-color] (if (string? color)
                                         [{} color]
                                         (let [[r g b a] color]
                                           (if (some? a)
                                             [{} (color->css color)]
                                             [{:--gi-border-opacity 1} (color->css [r g b "var(--gi-border-opacity)"])])))
                    props (if (nil? sides)
                            [:border-color]
                            (mapv (fn [side]
                                    (keyword (str "border-" side "-color")))
                                  sides))]
                (into result
                      (map (fn [prop] [prop css-color]))
                      props)))
    :before-rules #{:border-opacity}}


   {:id :border-opacity
    :rules "
    border-opacity = <'border-opacity-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-border-opacity (value-unit->css value {:value-fn div-100})})}


   {:id :border-style
    :rules "
    border-style = <'border-'> ('solid' | 'dashed' | 'dotted' | 'double' | 'hidden' | 'none')
    "
    :garden (fn [{[border-style] :component-data}]
              {:border-style border-style})}


   {:id :divide-width
    :rules "
    divide-width = <'divide-'> axis (<'-'> (divide-width-value | divide-width-reverse))?
    divide-width-value = number | length | length-unit
    divide-width-reverse = 'reverse'
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [axis divide-width-value divide-width-reverse]} (into {} component-data)
                    width (if (nil? divide-width-value)
                            "1px"
                            (value-unit->css divide-width-value {:zero-unit nil
                                                                 :number {:unit "px"}}))]
                  [between-children-selector
                   (case axis
                     "x" (if (nil? divide-width-reverse)
                           {:--gi-divide-x-reverse 0
                            :border-right-width (str "calc(" width " * calc(1 - var(--gi-divide-x-reverse)))")
                            :border-left-width  (str "calc(" width " * var(--gi-divide-x-reverse))")}
                           {:--gi-divide-x-reverse 1})
                     "y" (if (nil? divide-width-reverse)
                           {:--gi-divide-y-reverse 0
                            :border-top-width    (str "calc(" width " * calc(1 - var(--gi-divide-y-reverse)))")
                            :border-bottom-width (str "calc(" width " * var(--gi-divide-y-reverse))")}
                           {:--gi-divide-y-reverse 1}))]))}


   {:id :divide-color
    :rules "
    divide-color = <'divide-'> color
    "
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              (let [color (read-color color)]
                [between-children-selector
                 (if (string? color)
                   {:border-color color}
                   (let [[r g b a] color]
                     (if (some? a)
                       {:border-color (color->css color)}
                       {:--gi-divide-opacity 1
                        :border-color (color->css [r g b "var(--gi-divide-opacity)"])})))]))
    :before-rules #{:divide-opacity}}


   {:id :divide-opacity
    :rules "
    divide-opacity = <'divide-opacity-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-divide-opacity (value-unit->css value {:value-fn div-100})})}


   {:id :divide-style
    :rules "
    divide-style = <'divide-'> ('solid' | 'dashed' | 'dotted' | 'double' | 'none')
    "
    :garden (fn [{[border-style] :component-data}]
              [between-children-selector
               {:border-style border-style}])}


   {:id :outline-width
    :rules "
    outline-width = <'outline-'> (number | length | length-unit)
    "
    :garden (fn [{[value] :component-data}]
              {:outline-width (value-unit->css value {:number {:unit "px"}})})}


   {:id :outline-color
    :rules "
    outline-color = <'outline-'> ('inherit' | color)
    "
    :garden (fn [{[value] :component-data read-color :read-color}]
              {:outline-color (case value
                                "inherit" "inherit"
                                (color->css (read-color value)))})}


   {:id :outline-style
    :rules "
    outline-style = <'outline'> (<'-'> ('none' | 'dashed' | 'dotted' | 'double' | 'hidden'))?
    "
    :garden (fn [{[style] :component-data}]
              (cond
                (= style "none")
                {:outline "2px solid transparent"
                 :outline-offset "2px"}

                (nil? style)
                {:outline-style "solid"}

                :else
                {:outline-style style}))}


   {:id :outline-offset
    :rules "
    outline-offset = <'outline-offset-'> (number | length | length-unit)
    "
    :garden (fn [{[value] :component-data}]
              {:outline-offset (value-unit->css value {:number {:unit "px"}})})}


   {:id :ring-width
    :rules "
    ring-width = <'ring'> (<'-'> ('inset' | number | length | length-unit))?
    "
    :garden (fn [{[value] :component-data}]
              (if (= value "inset")
                {:--gi-ring-inset "inset"}
                (let [size (if (nil? value)
                             "3px"
                             (value-unit->css value {:zero-unit nil
                                                     :number {:unit "px"}}))]
                  {:box-shadow (str "var(--gi-ring-inset) 0 0 0 "
                                    "calc(" size " + var(--gi-ring-offset-width)) "
                                    "var(--gi-ring-color)")})))}


   {:id :ring-color
    :rules "
    ring-color = <'ring-'> color
    "
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              (let [color (read-color color)]
                (if (string? color)
                  {:--gi-ring-color color}
                  (let [[r g b a] color]
                    (if (some? a)
                      {:--gi-ring-color (color->css color)}
                      {:--gi-ring-color (color->css [r g b "var(--gi-ring-opacity)"])})))))}


   {:id :ring-opacity
    :rules "
    ring-opacity = <'ring-opacity-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-ring-opacity (value-unit->css value {:value-fn div-100})})}


   {:id :ring-offset-width
    :rules "
    ring-offset-width = <'ring-offset-'> (number | length | length-unit)
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-ring-offset-width (value-unit->css value {:zero-unit nil
                                                               :number {:unit "px"}})
               :box-shadow (str "0 0 0 var(--gi-ring-offset-width) var(--gi-ring-offset-color), "
                                "var(--gi-ring-shadow)")})}


   {:id :ring-offset-color
    :rules "
    ring-offset-color = <'ring-offset-'> color
    "
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              {:--gi-ring-offset-color (let [color (read-color color)]
                                         (if (string? color)
                                           color
                                           (let [[r g b a] color]
                                             (if (some? a)
                                               (color->css color)
                                               (color->css [r g b "var(--gi-ring-opacity)"])))))
               :box-shadow (str "0 0 0 var(--gi-ring-offset-width) var(--gi-ring-offset-color), "
                                "var(--gi-ring-shadow)")})}])
