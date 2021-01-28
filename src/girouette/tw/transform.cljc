(ns girouette.tw.transform
  (:require [clojure.string :as str]
            [girouette.tw.common :refer [read-number value->css value-unit->css]]))

(def components
  [{:id :transform
    :rules "
    transform = 'transform' | 'transform-gpu' | 'transform-none'
    "
    :garden (fn [{[transform-type] :component-data}]
              (case transform-type
                "transform" {:--gi-translate-x 0
                             :--gi-translate-y 0
                             :--gi-rotate 0
                             :--gi-skew-x 0
                             :--gi-skew-y 0
                             :--gi-scale-x 1
                             :--gi-scale-y 1
                             :transform (str "translateX(var(--gi-translate-x)) "
                                             "translateY(var(--gi-translate-y)) "
                                             "rotate(var(--gi-rotate)) "
                                             "skewX(var(--gi-skew-x)) "
                                             "skewY(var(--gi-skew-y)) "
                                             "scaleX(var(--gi-scale-x)) "
                                             "scaleY(var(--gi-scale-y))")}
                "transform-gpu" {:--gi-translate-x 0
                                 :--gi-translate-y 0
                                 :--gi-rotate 0
                                 :--gi-skew-x 0
                                 :--gi-skew-y 0
                                 :--gi-scale-x 1
                                 :--gi-scale-y 1
                                 :transform (str "translate3d(var(--gi-translate-x),var(--gi-translate-y),0) "
                                                 "rotate(var(--gi-rotate)) "
                                                 "skewX(var(--gi-skew-x)) "
                                                 "skewY(var(--gi-skew-y)) "
                                                 "scaleX(var(--gi-scale-x)) "
                                                 "scaleY(var(--gi-scale-y))")}
                "transform-none" {:transform "none"}))}


   {:id :transform-origin
    :rules  "
    transform-origin = <'origin-'> ('top-left'| 'top' | 'top-right' |
                                    'left' | 'center' | 'right' |
                                    'bottom-left' | 'bottom' | 'bottom-right')
    "
    :garden (fn [{[direction] :component-data}]
              {:transform-origin (str/escape direction {\- \space})})}


   {:id :scale
    :rules "
    scale = signus? <'scale-'> (axis <'-'>)? scale-value
    scale-value = integer
    "
    :garden (fn [{data :component-data}]
              (let [{:keys [signus axis scale-value]} (into {} data)
                    axes ({"x" ["x"]
                           "y" ["y"]
                           nil ["x" "y"]} axis)
                    ;; TODO: improve the utility functions to handle this kind of case
                    value (str signus (value->css (/ (read-number (second scale-value)) 100.0)))]
                (into {}
                      (map (fn [axis]
                             [(keyword (str "--gi-scale-" axis)) value]))
                      axes)))}


   {:id :rotate
    :rules "
    rotate = signus? <'rotate-'> rotate-value
    rotate-value = integer
    "
    :garden (fn [{data :component-data}]
              (let [{:keys [signus rotate-value]} (into {} data)
                    ;; TODO: improve the utility functions to handle this kind of case
                    value (str signus (value->css (read-number (second rotate-value))) "deg")]
                {:--gi-rotate value}))}


   {:id :translate
    :rules "
    translate = signus? <'translate-'> axis <'-'> translate-value
    translate-value = number | length | length-unit | fraction | percentage-full
    "
    :garden (fn [{data :component-data}]
              (let [{:keys [signus axis translate-value]} (into {} data)
                    attribute ({"x" :--gi-translate-x
                                "y" :--gi-translate-y} axis)]
                {attribute (value-unit->css translate-value
                                            {:signus signus
                                             :number-unit :quarter-rem
                                             :fraction-unit "%"})}))}


   {:id :skew
    :rules  "
    skew = signus? <'skew-'> axis <'-'> skew-value
    skew-value = integer
    "
    :garden (fn [{data :component-data}]
              (let [{:keys [signus axis skew-value]} (into {} data)
                    attribute (keyword (str "--gi-skew-" axis))
                    ;; TODO: improve the utility functions to handle this kind of case
                    value (str signus (value->css (read-number (second skew-value))) "deg")]
                {attribute value}))}])
