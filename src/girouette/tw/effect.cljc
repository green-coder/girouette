(ns girouette.tw.effect
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


   {:id :opacity
    :rules "
    opacity = <'opacity-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:opacity (value-unit->css value {:value-fn div-100})})}])
