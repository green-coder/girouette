(ns ^:no-doc girouette.tw.border
  (:require [garden.selectors :as gs]
            [girouette.tw.common :refer [value-unit->css dot default-pipeline div-100]]
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
                            (value-unit->css border-width-value {:zero-unit nil
                                                                 :number {:unit "px"}}))}))}


   {:id :border-color
    :rules "
    border-color = <'border-'> color
    "
    ;; Copy-pasted from background; could be extracted into util?
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              (let [color (read-color color)]
                (if (string? color)
                  {:border-color color}
                  (let [[r g b a] color]
                    (if (some? a)
                      {:border-color (color->css color)}
                      {:border-color (color->css [r g b "var(--gi-border-opacity,1)"])})))))}


   {:id :border-opacity
    :rules "
    border-opacity = <'border-opacity-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-border-opacity (value-unit->css value {:value-fn div-100})})}


   {:id :border-style
    :rules "
    border-style = <'border-'> ('solid' | 'dashed' | 'dotted' | 'double' | 'none')
    "
    :garden (fn [{[border-style] :component-data}]
              {:border-style border-style})}


   {:id :divide-width
    :rules "
    divide-width = <'divide-'> axis (<'-'> divide-width-value)?
    divide-width-value = number | length | length-unit
    "
    :pipeline (assoc default-pipeline
                :class-name [(fn [rule props]
                               [(gs/> (dot (:class-name props)) (gs/+ :* :*)) rule])])
    :garden (fn [{:keys [component-data]}]
              (let [{axis  :axis
                     value :divide-width-value} (into {} component-data)]
                (let [width (if (nil? value)
                              "1px"
                              (value-unit->css value {:zero-unit nil
                                                      :number {:unit "px"}}))]
                  (case axis
                    "x" {:border-right-width (str "calc(" width " * var(--gi-divide-x-reverse))")
                         :border-left-width  (str "calc(" width " * calc(1 - var(--gi-divide-x-reverse))")}
                    "y" {:border-top-width    (str "calc(" width " * var(--gi-divide-y-reverse))")
                         :border-bottom-width (str "calc(" width " * calc(1 - var(--gi-divide-y-reverse))")}))))}


   {:id :divide-color
    :rules "
    divide-color = <'divide-'> color
    "
    :pipeline (assoc default-pipeline
                :class-name [(fn [rule props]
                               [(gs/> (dot (:class-name props)) (gs/+ :* :*)) rule])])
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              (let [color (read-color color)]
                (if (string? color)
                  {:border-color color}
                  (let [[r g b a] color]
                    (if (some? a)
                      {:border-color (color->css color)}
                      {:border-color (color->css [r g b "var(--gi-divide-opacity,1)"])})))))}


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
    :pipeline (assoc default-pipeline
                :class-name [(fn [rule props]
                               [(gs/> (dot (:class-name props)) (gs/+ :* :*)) rule])])
    :garden (fn [{[border-style] :component-data}]
              {:border-style border-style})}


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
