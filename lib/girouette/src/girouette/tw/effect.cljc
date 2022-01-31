(ns ^:no-doc girouette.tw.effect
  (:require [girouette.tw.common :refer [value-unit->css div-100 mul-100]]))

(def components
  [{:id :background-blend-mode
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
                {:filter (str "blur(" blur ")")}))}


   {:id :brightness
    :rules "
    brightness = <'brightness-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:filter (str "brightness(" (value-unit->css
                                            value
                                            {:value-fn div-100})
                            ")")})}


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
              {:filter (str "contrast(" (value-unit->css
                                          value
                                          {:value-fn div-100})
                            ")")})}


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
                {:filter v}))}


   {:id :grayscale
    :rules "
    grayscale = <'grayscale'> (<'-'> (number | fraction))?
    "
    :garden (fn [{[value] :component-data}]
              (if (nil? value)
                {:filter "grayscale(100%)"}
                {:filter (str "grayscale("
                              (value-unit->css value
                                               {:number {:unit "%"
                                                         :zero-unit nil}
                                                :fraction {:unit "%"
                                                           :value-fn mul-100}})
                              ")")}))}


   {:id :hue-rotate
    :rules "
    hue-rotate = <'hue-rotate-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:filter (str "hue-rotate("
                            (value-unit->css value {:number {:unit "deg"}})
                            ")")})}

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
              {:opacity (value-unit->css value {:value-fn div-100})})}])
