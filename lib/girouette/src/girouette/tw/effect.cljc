(ns ^:no-doc girouette.tw.effect
  (:require [girouette.tw.common :refer [value-unit->css div-100]]))

(def components
  [{:id :box-shadow
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
