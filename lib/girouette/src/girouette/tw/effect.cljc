(ns ^:no-doc girouette.tw.effect
  (:require [girouette.tw.common :refer [value-unit->css div-100 mul-100]]))

(def ^:private filter-rule
  "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )")

(def ^:private backdrop-filter-rule
  "var(--gi-backdrop-blur) var(--gi-backdrop-brightness) var(--gi-backdrop-contrast) var(--gi-backdrop-grayscale) var(--gi-backdrop-hue-rotate) var(--gi-backdrop-invert) var(--gi-backdrop-opacity) var(--gi-backdrop-saturate) var(--gi-backdrop-sepia)")

(def components
  [{:id :backdrop-blur
    :rules "
    backdrop-blur = <'backdrop-blur'> (<'-'> ( backdrop-blur-value-fix |
                                               backdrop-blur-value-number ))?
    backdrop-blur-value-fix = 'sm' | 'md' | 'lg' | 'xl' | '2xl' | '3xl' | 'none'
    backdrop-blur-value-number = length
    "
    :garden (fn [{value :component-data}]
              (let [blur (if (empty? value)
                           "8px"
                           (let [{:keys [backdrop-blur-value-number
                                         backdrop-blur-value-fix]}
                                 (into {} value)]
                             (if backdrop-blur-value-number
                               (value-unit->css backdrop-blur-value-number {})
                               ({"sm" "4px"
                                 "md" "12px"
                                 "lg" "16px"
                                 "xl" "24px"
                                 "2xl" "40px"
                                 "3xl" "64px"
                                 "none" "0"} backdrop-blur-value-fix))))]
                {:--gi-backdrop-blur (str "blur(" blur ")")
                 :backdrop-filter backdrop-filter-rule}))}


   {:id :backdrop-brightness
    :rules "
    backdrop-brightness = <'backdrop-brightness-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-brightness (str "brightness(" (value-unit->css
                                                              value
                                                              {:value-fn div-100})
                                              ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :backdrop-contrast
    :rules "
    backdrop-contrast = <'backdrop-contrast-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-contrast (str "contrast(" (value-unit->css
                                                          value
                                                          {:value-fn div-100})
                                            ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :backdrop-grayscale
    :rules "
    backdrop-grayscale = <'backdrop-grayscale'> (<'-'> (number | fraction))?
    "
    :garden (fn [{[value] :component-data}]
              {:backdrop-filter backdrop-filter-rule
               :--gi-backdrop-grayscale
               (str "grayscale("
                    (if (nil? value)
                      "100%"
                      (value-unit->css value
                                       {:number {:unit "%"
                                                 :zero-unit nil}
                                        :fraction {:unit "%"
                                                   :value-fn mul-100}}))
                    ")")})}

   {:id :backdrop-hue-rotate
    :rules "
    backdrop-hue-rotate = <'backdrop-hue-rotate-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-hue-rotate (str "hue-rotate("
                                              (value-unit->css value
                                                               {:number {:unit "deg"}})
                                              ")")
               :backdrop-filter backdrop-filter-rule})}

   {:id :backdrop-invert
    :rules "
    backdrop-invert = <'backdrop-invert'> (<'-'> number )?
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-invert (str "invert("
                                          (if (nil? value)
                                            "100%"
                                            (value-unit->css value {:number {:unit "%"}}))
                                          ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :backdrop-opacity
    :rules "
    backdrop-opacity = <'backdrop-opacity-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-opacity (str "opacity("
                                           (value-unit->css value {:value-fn div-100})
                                           ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :backdrop-saturate
    :rules "
    backdrop-saturate = <'backdrop-saturate-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-saturate (str "saturate("
                                            (value-unit->css value {:value-fn div-100})
                                            ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :backdrop-sepia
    :rules "
    backdrop-sepia = <'backdrop-sepia'> (<'-'> number)?
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-backdrop-sepia (str "sepia("
                                         (value-unit->css value {:number {:unit "%"}})
                                         ")")
               :backdrop-filter backdrop-filter-rule})}


   {:id :background-blend-mode
    :rules "
    <background-blend-mode-value> =
                         'normal' | 'multiply' | 'screen' | 'overlay' |
                         'darken' | 'lighten'  | 'color-dodge' |
                         'color-burn' | 'hard-light' | 'soft-light' |
                         'difference' | 'exclusion' | 'hue' | 'saturation' |
                         'color' | 'luminosity'
    background-blend-mode = <'bg-blend-'> background-blend-mode-value
    "
    :garden (fn [{[blend-mode] :component-data}]
              {:background-blend-mode blend-mode})}


   {:id :blur
    :rules "
    blur = <'blur'> (<'-'> ( blur-value-fix | blur-value-number ))?
    blur-value-fix = 'sm' | 'md' | 'lg' | 'xl' | '2xl' | '3xl' | 'none'
    blur-value-number = length
    "
    :garden (fn [{value :component-data}]
              (let [blur (if (empty? value)
                           "8px"
                           (let [{:keys [blur-value-number blur-value-fix]}
                                 (into {} value)]
                             (if blur-value-number
                               (value-unit->css blur-value-number {})
                               ({"sm" "4px"
                                 "md" "12px"
                                 "lg" "16px"
                                 "xl" "24px"
                                 "2xl" "40px"
                                 "3xl" "64px"
                                 "none" "0"} blur-value-fix))))]
                {:--gi-blur (str "blur(" blur ")")
                 :filter filter-rule}))}


   {:id :brightness
    :rules "
    brightness = <'brightness-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-brightness (str "brightness(" (value-unit->css
                                                     value
                                                     {:value-fn div-100})
                                     ")")
               :filter filter-rule})}


   {:id :box-shadow
    :rules "
    box-shadow = <'shadow'> (<'-'> box-shadow-value)?
    box-shadow-value = 'sm' | 'md' | 'lg' | 'xl' | '2xl' | 'inner' | 'none'
    "
    :garden (fn [{data :component-data}]
              (let [{:keys [box-shadow-value]} (into {} data)
                    shadow-params (case box-shadow-value
                                    "sm" "0 1px 2px 0 rgba(0,0,0,0.05)"
                                    nil (str "0 1px 3px 0 rgba(0,0,0,0.1),"
                                             "0 1px 2px 0 rgba(0,0,0,0.06)")
                                    "md" (str "0 4px 6px -1px rgba(0,0,0,0.1),"
                                              "0 2px 4px -1px rgba(0,0,0,0.06)")
                                    "lg" (str "0 10px 15px -3px rgba(0,0,0,0.1),"
                                              "0 4px 6px -2px rgba(0,0,0,0.05)")
                                    "xl" (str "0 20px 25px -5px rgba(0,0,0,0.1),"
                                              "0 10px 10px -5px rgba(0,0,0,0.04)")
                                    "2xl" "0 25px 50px -12px rgba(0,0,0,0.25)"
                                    "inner" "inset 0 2px 4px 0 rgba(0,0,0,0.06)"
                                    "none" "0 0 #0000")]
                {:--gi-shadow shadow-params
                 :box-shadow (str "var(--gi-ring-offset-shadow,0 0 #0000),"
                                  "var(--gi-ring-shadow,0 0 #0000),"
                                  "var(--gi-shadow)")}))}


   {:id :contrast
    :rules "
    contrast = <'contrast-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-contrast (str "contrast(" (value-unit->css
                                                 value
                                                 {:value-fn div-100})
                                   ")")
               :filter filter-rule})}


   {:id :drop-shadow
    :rules "
    drop-shadow = <'drop-shadow'> (<'-'> drop-shadow-value)?
    <drop-shadow-value> = 'sm' | 'md' | 'lg' | 'xl' | '2xl' | 'none'
    "
    :garden (fn [{[value] :component-data}]
              (let [v (case value
                        nil "drop-shadow(0 1px 2px rgb(0 0 0 / 0.1)) drop-shadow(0 1px 1px rgb(0 0 0 / 0.06))"
                        "sm" "drop-shadow(0 1px 1px rgb(0 0 0 / 0.05))"
                        "md" "drop-shadow(0 4px 3px rgb(0 0 0 / 0.07)) drop-shadow(0 2px 2px rgb(0 0 0 / 0.06))"
                        "lg" "drop-shadow(0 10px 8px rgb(0 0 0 / 0.04)) drop-shadow(0 4px 3px rgb(0 0 0 / 0.1))"
                        "xl" "drop-shadow(0 20px 13px rgb(0 0 0 / 0.03)) drop-shadow(0 8px 5px rgb(0 0 0 / 0.08))"
                        "2xl" "drop-shadow(0 25px 25px rgb(0 0 0 / 0.15))"
                        "none" "drop-shadow(0 0 #0000)")]
                {:--gi-drop-shadow v
                 :filter filter-rule}))}


   {:id :grayscale
    :rules "
    grayscale = <'grayscale'> (<'-'> (number | fraction))?
    "
    :garden (fn [{[value] :component-data}]
              {:filter filter-rule
               :--gi-grayscale
               (str "grayscale("
                    (if (nil? value)
                      "100%"
                      (value-unit->css value
                                       {:number {:unit "%"
                                                 :zero-unit nil}
                                        :fraction {:unit "%"
                                                   :value-fn mul-100}}))
                    ")")})}


   {:id :hue-rotate
    :rules "
    hue-rotate = <'hue-rotate-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-hue-rotate (str "hue-rotate("
                                     (value-unit->css value {:number {:unit "deg"}})
                                     ")")
               :filter filter-rule})}

   {:id :invert
    :rules "
    invert = <'invert'> (<'-'> number )?
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-invert (str "invert("
                                 (if (nil? value)
                                   "100%"
                                   (value-unit->css value {:number {:unit "%"}}))
                                 ")")
               :filter filter-rule})}


   {:id :mix-blend-mode
    :rules "
    <mix-blend-mode-value> =
                         'normal' | 'multiply' | 'screen' | 'overlay' |
                         'darken' | 'lighten'  | 'color-dodge' |
                         'color-burn' | 'hard-light' | 'soft-light' |
                         'difference' | 'exclusion' | 'hue' | 'saturation' |
                         'color' | 'luminosity'
    mix-blend-mode = <'mix-blend-'> mix-blend-mode-value
    "
    :garden (fn [{[blend-mode] :component-data}]
              {:mix-blend-mode blend-mode})}

   {:id :opacity
    :rules "
    opacity = <'opacity-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:opacity (value-unit->css value {:value-fn div-100})})}


   {:id :saturate
    :rules "
    saturate = <'saturate-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-saturate (str "saturate("
                                   (value-unit->css value {:value-fn div-100})
                                   ")")
               :filter filter-rule})}

   {:id :sepia
    :rules "
    sepia = <'sepia'> (<'-'> number)?
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-sepia (str "sepia("
                                (value-unit->css value {:number {:unit "%"}})
                                ")")
               :filter filter-rule})}
   ])
