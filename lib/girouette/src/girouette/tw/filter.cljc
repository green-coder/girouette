(ns ^:no-doc girouette.tw.filter
  (:require [girouette.tw.common :refer [value-unit->css div-100 mul-100]]))

(def ^:private filter-rule
  (str "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) "
       "var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) "
       "var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"))

(def ^:private backdrop-filter-rule
  (str "var(--gi-backdrop-blur) var(--gi-backdrop-brightness) var(--gi-backdrop-contrast) "
       "var(--gi-backdrop-grayscale) var(--gi-backdrop-hue-rotate) var(--gi-backdrop-invert) "
       "var(--gi-backdrop-opacity) var(--gi-backdrop-saturate) var(--gi-backdrop-sepia)"))

(def components
  [{:id :blur
    :since-version [:tw 3]
    :rules "
    blur = <'blur'> (<'-'> (blur-value-fix | blur-value-number))?
    blur-value-fix = 'sm' | 'md' | 'lg' | 'xl' | '2xl' | '3xl' | 'none'
    blur-value-number = length
    "
    :garden (fn [{value :component-data}]
              (let [blur (let [{:keys [blur-value-number blur-value-fix]} (into {} value)]
                           (if (some? blur-value-number)
                             (value-unit->css blur-value-number)
                             ({"none" "0"
                               "sm"   "4px"
                               nil    "8px"
                               "md"   "12px"
                               "lg"   "16px"
                               "xl"   "24px"
                               "2xl"  "40px"
                               "3xl"  "64px"} blur-value-fix)))]
                {:--gi-blur (str "blur(" blur ")")
                 :filter filter-rule}))}


   {:id :brightness
    :since-version [:tw 3]
    :rules "
    brightness = <'brightness-'> (number | percentage | fraction)
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-brightness (str "brightness("
                                     (value-unit->css value {:number {:value-fn div-100}})
                                     ")")
               :filter filter-rule})}


   {:id :contrast
    :since-version [:tw 3]
    :rules "
    contrast = <'contrast-'> (number | percentage | fraction)
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-contrast (str "contrast("
                                   (value-unit->css value {:number {:value-fn div-100}})
                                   ")")
               :filter filter-rule})}


   {:id :drop-shadow
    :since-version [:tw 3]
    :rules "
    drop-shadow = <'drop-shadow'> (<'-'> drop-shadow-value)?
    <drop-shadow-value> = 'sm' | 'md' | 'lg' | 'xl' | '2xl' | 'none'
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-drop-shadow (case value
                                   "sm" "drop-shadow(0 1px 1px rgb(0 0 0 / 0.05))"
                                   nil "drop-shadow(0 1px 2px rgb(0 0 0 / 0.1)) drop-shadow(0 1px 1px rgb(0 0 0 / 0.06))"
                                   "md" "drop-shadow(0 4px 3px rgb(0 0 0 / 0.07)) drop-shadow(0 2px 2px rgb(0 0 0 / 0.06))"
                                   "lg" "drop-shadow(0 10px 8px rgb(0 0 0 / 0.04)) drop-shadow(0 4px 3px rgb(0 0 0 / 0.1))"
                                   "xl" "drop-shadow(0 20px 13px rgb(0 0 0 / 0.03)) drop-shadow(0 8px 5px rgb(0 0 0 / 0.08))"
                                   "2xl" "drop-shadow(0 25px 25px rgb(0 0 0 / 0.15))"
                                   "none" "drop-shadow(0 0 #0000)")
               :filter filter-rule})}


   {:id :grayscale
    :since-version [:tw 3]
    :rules "
    grayscale = <'grayscale'> (<'-'> (number | percentage | fraction))?
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-grayscale (str "grayscale("
                                    (if (nil? value)
                                      "100%"
                                      (value-unit->css value {:number {:value-fn div-100}}))
                                    ")")
               :filter filter-rule})}


   {:id :hue-rotate
    :since-version [:tw 3]
    :rules "
    hue-rotate = <'hue-rotate-'> (number | angle)
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-hue-rotate (str "hue-rotate("
                                     (value-unit->css value {:number {:unit "deg"}})
                                     ")")
               :filter filter-rule})}


   {:id :invert
    :since-version [:tw 3]
    :rules "
    invert = <'invert'> (<'-'> (number | percentage | fraction))?
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-invert (str "invert("
                                 (if (nil? value)
                                   "100%"
                                   (value-unit->css value {:number {:value-fn div-100}}))
                                 ")")
               :filter filter-rule})}


   {:id :saturate
    :since-version [:tw 3]
    :rules "
    saturate = <'saturate-'> (number | percentage | fraction)
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-saturate (str "saturate("
                                   (value-unit->css value {:number {:value-fn div-100}})
                                   ")")
               :filter filter-rule})}


   {:id :sepia
    :since-version [:tw 3]
    :rules "
    sepia = <'sepia'> (<'-'> (number | percentage | fraction))?
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-sepia (str "sepia("
                                (if (nil? value)
                                  "100%"
                                  (value-unit->css value {:number {:value-fn div-100}}))
                                ")")
               :filter filter-rule})}


   {:id :backdrop-blur
    :since-version [:tw 3]
    :rules "
    backdrop-blur = <'backdrop-blur'> (<'-'> (backdrop-blur-value-fix | backdrop-blur-value-number))?
    backdrop-blur-value-fix = 'sm' | 'md' | 'lg' | 'xl' | '2xl' | '3xl' | 'none'
    backdrop-blur-value-number = length
    "
    :garden (fn [{value :component-data}]
              (let [blur (let [{:keys [backdrop-blur-value-number
                                       backdrop-blur-value-fix]} (into {} value)]
                           (if (some? backdrop-blur-value-number)
                             (value-unit->css backdrop-blur-value-number)
                             ({"none" "0"
                               "sm"  "4px"
                               nil   "8px"
                               "md"  "12px"
                               "lg"  "16px"
                               "xl"  "24px"
                               "2xl" "40px"
                               "3xl" "64px"} backdrop-blur-value-fix)))]
                {:--gi-backdrop-blur (str "blur(" blur ")")
                 :backdrop-filter backdrop-filter-rule}))}


   {:id :backdrop-brightness
    :since-version [:tw 3]
    :rules "
    backdrop-brightness = <'backdrop-brightness-'> (number | percentage | fraction)
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-brightness (str "brightness("
                                              (value-unit->css value {:number {:value-fn div-100}})
                                              ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :backdrop-contrast
    :since-version [:tw 3]
    :rules "
    backdrop-contrast = <'backdrop-contrast-'> (number | percentage | fraction)
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-contrast (str "contrast("
                                            (value-unit->css value {:number {:value-fn div-100}})
                                            ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :backdrop-grayscale
    :since-version [:tw 3]
    :rules "
    backdrop-grayscale = <'backdrop-grayscale'> (<'-'> (number | percentage | fraction))?
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-grayscale (str "grayscale("
                                             (if (nil? value)
                                               "100%"
                                               (value-unit->css value {:number {:value-fn div-100}}))
                                             ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :backdrop-hue-rotate
    :since-version [:tw 3]
    :rules "
    backdrop-hue-rotate = <'backdrop-hue-rotate-'> (number | angle)
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-hue-rotate (str "hue-rotate("
                                              (value-unit->css value {:number {:unit "deg"}})
                                              ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :backdrop-invert
    :since-version [:tw 3]
    :rules "
    backdrop-invert = <'backdrop-invert'> (<'-'> (number | percentage | fraction))?
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-invert (str "invert("
                                          (if (nil? value)
                                            "100%"
                                            (value-unit->css value {:number {:value-fn div-100}}))
                                          ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :backdrop-opacity
    :since-version [:tw 3]
    :rules "
    backdrop-opacity = <'backdrop-opacity-'> (number | percentage | fraction)
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-opacity (str "opacity("
                                           (value-unit->css value {:number {:value-fn div-100}})
                                           ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :backdrop-saturate
    :since-version [:tw 3]
    :rules "
    backdrop-saturate = <'backdrop-saturate-'> (number | percentage | fraction)
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-saturate (str "saturate("
                                            (value-unit->css value {:number {:value-fn div-100}})
                                            ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :backdrop-sepia
    :since-version [:tw 3]
    :rules "
    backdrop-sepia = <'backdrop-sepia'> (<'-'> (number | percentage | fraction))?
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-sepia (str "sepia("
                                         (value-unit->css value {:number {:value-fn div-100}})
                                         ")")
               :backdrop-filter backdrop-filter-rule})}])
