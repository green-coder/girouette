(ns ^:no-doc girouette.tw.effect
  (:require [clojure.string :as str]
            [girouette.tw.common :refer [value-unit->css div-100]]
            [girouette.tw.color :refer [color->css]]))

(def components
  [{:id :box-shadow
    :since-version [:tw 2]
    :removed-in-version [:tw 3]
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

   {:id      :box-shadow
    :since-version [:tw 3]
    :rules   "
    box-shadow = <'shadow'> (<'-'> box-shadow-value)?
    box-shadow-value = 'sm' | 'md' | 'lg' | 'xl' | '2xl' | 'inner' | 'none'
    "
    :garden  (fn [{data :component-data}]
               (let [{:keys [box-shadow-value]} (into {} data)
                     [shadows-params shadow-color] (case box-shadow-value
                                                     "sm" [["0 1px 2px 0"] "rgba(0,0,0,0.05)"]
                                                     nil [["0 1px 3px 0"
                                                           "0 1px 2px -1px"] "rgba(0,0,0,0.1)"]
                                                     "md" [["0 4px 6px -1px"
                                                            "0 2px 4px -2px"] "rgba(0,0,0,0.1)"]
                                                     "lg" [["0 10px 15px -3px"
                                                            "0 4px 6px -4px"] "rgba(0,0,0,0.1)"]
                                                     "xl" [["0 20px 25px -5px"
                                                            "0 8px 10px -6px"] "rgba(0,0,0,0.1)"]
                                                     "2xl" [["0 25px 50px -12px"] "rgba(0,0,0,0.25)"]
                                                     "inner" [["inset 0 2px 4px 0"] "rgba(0,0,0,0.05)"]
                                                     "none" [["0 0"] "#0000"])]
                 {:--gi-shadow         (->> shadows-params
                                            (map (fn [shadow-params]
                                                   (str shadow-params " " shadow-color)))
                                            (str/join ","))
                  :--gi-shadow-colored (->> shadows-params
                                            (map (fn [shadow-params]
                                                   (str shadow-params " var(--gi-shadow-color)")))
                                            (str/join ","))
                  :box-shadow          (str "var(--gi-ring-offset-shadow,0 0 #0000),"
                                            "var(--gi-ring-shadow,0 0 #0000),"
                                            "var(--gi-shadow)")}))}


   {:id :box-shadow-color
    :since-version [:tw 2]
    :rules "
    box-shadow-color = <'shadow-'> ('inherit' | color)
    "
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              {:--gi-shadow-color (if (= color "inherit")
                                    "inherit"
                                    (color->css (read-color color)))
               :--gi-shadow "var(--gi-shadow-colored)"})}


   {:id :opacity
    :since-version [:tw 2]
    :rules "
    opacity = <'opacity-'> (number | percentage | fraction)
    "
    :garden (fn [{[value] :component-data}]
              {:opacity (value-unit->css value {:number {:value-fn div-100}})})}


   {:id :mix-blend-mode
    :since-version [:tw 3]
    :rules "
    mix-blend-mode = <'mix-blend-'> mix-blend-mode-value
    <mix-blend-mode-value> = 'normal' | 'multiply' | 'screen' | 'overlay' |
                             'darken' | 'lighten' | 'color-dodge' |
                             'color-burn' | 'hard-light' | 'soft-light' |
                             'difference' | 'exclusion' | 'hue' | 'saturation' |
                             'color' | 'luminosity'
    "
    :garden (fn [{[blend-mode] :component-data}]
              {:mix-blend-mode blend-mode})}


   {:id :background-blend-mode
    :since-version [:tw 3]
    :rules "
    background-blend-mode = <'bg-blend-'> background-blend-mode-value
    <background-blend-mode-value> = 'normal' | 'multiply' | 'screen' | 'overlay' |
                                    'darken' | 'lighten' | 'color-dodge' |
                                    'color-burn' | 'hard-light' | 'soft-light' |
                                    'difference' | 'exclusion' | 'hue' | 'saturation' |
                                    'color' | 'luminosity'
    "
    :garden (fn [{[blend-mode] :component-data}]
              {:background-blend-mode blend-mode})}])
