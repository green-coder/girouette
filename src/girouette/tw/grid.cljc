(ns girouette.tw.grid
  (:require [girouette.tw.common :refer [value-unit->css]]))

(def components
  [{:id :grid-template-columns
    :rules "
    grid-template-columns = <'grid-cols-'> (integer | none)
    "
    :garden (fn [{[repeat] :component-data}]
              (let [repeat (value-unit->css repeat)]
                {:grid-template-columns (if (#{"none" 0} repeat)
                                          "none"
                                          (str "repeat(" repeat ", minmax(0, 1fr))"))}))}


   {:id :grid-column-auto
    :rules "
    grid-column-auto = 'col-auto'
    "
    :garden (fn [props]
              {:grid-column "auto"})}


   {:id :grid-column-span
    :rules "
    grid-column-span = <'col-span-'> (integer | full)
    "
    :garden (fn [{[param] :component-data}]
              (let [param (value-unit->css param)]
                {:grid-column (if (= param "full")
                                "-1 / 1"
                                ;; TODO: do we need to repeat the span twice?
                                (str "span " param " / span " param))}))}


   {:id :grid-column-start
    :rules "
    grid-column-start = <'col-start-'> (integer | auto)
    "
    :garden (fn [{[param] :component-data}]
              {:grid-column-start (value-unit->css param)})}


   {:id :grid-column-end
    :rules "
    grid-column-end = <'col-end-'> (integer | auto)
    "
    :garden (fn [{[param] :component-data}]
              {:grid-column-end (value-unit->css param)})}


   {:id :grid-template-rows
    :rules "
    grid-template-rows = <'grid-rows-'> (integer | none)
    "
    :garden (fn [{[repeat] :component-data}]
              (let [repeat (value-unit->css repeat)]
                {:grid-template-rows (if (#{"none" 0} repeat)
                                       "none"
                                       (str "repeat(" repeat ", minmax(0, 1fr))"))}))}


   {:id :grid-row-auto
    :rules "
    grid-row-auto = 'row-auto'
    "
    :garden (fn [props]
              {:grid-row "auto"})}


   {:id :grid-row-span
    :rules "
    grid-row-span = <'row-span-'> (integer | full)
    "
    :garden (fn [{[param] :component-data}]
              (let [param (value-unit->css param)]
                {:grid-row (if (= param "full")
                             "-1 / 1"
                             ;; TODO: do we need to repeat the span twice?
                             (str "span " param " / span " param))}))}


   {:id :grid-row-start
    :rules "
    grid-row-start = <'row-start-'> (integer | auto)
    "
    :garden (fn [{[param] :component-data}]
              {:grid-row-start (value-unit->css param)})}


   {:id :grid-row-end
    :rules "
    grid-row-end = <'row-end-'> (integer | auto)
    "
    :garden (fn [{[param] :component-data}]
              {:grid-row-end (value-unit->css param)})}


   {:id :grid-auto-flow
    :rules "
    grid-auto-flow = <'grid-flow-'> ('row' | 'col' | 'row-dense' | 'col-dense')
    "
    :garden (fn [{[param] :component-data}]
              {:grid-auto-flow ({"row" "row"
                                 "col" "column"
                                 "row-dense" "row dense"
                                 "col-dense" "column dense"} param)})}


   {:id :grid-auto-columns
    :rules "
    grid-auto-columns = <'auto-cols-'> ('auto' | 'min' | 'max' | 'fr')
    "
    :garden (fn [{[param] :component-data}]
              {:grid-auto-columns ({"auto" "auto"
                                    "min" "min-content"
                                    "max" "max-content"
                                    "fr" "minmax(0, 1fr)"} param)})}


   {:id :grid-auto-rows
    :rules "
    grid-auto-rows = <'auto-rows-'> ('auto' | 'min' | 'max' | 'fr')
    "
    :garden (fn [{[param] :component-data}]
              {:grid-auto-rows ({"auto" "auto"
                                 "min" "min-content"
                                 "max" "max-content"
                                 "fr" "minmax(0, 1fr)"} param)})}


   {:id :gap
    :rules "
    gap = <'gap-'> (axis <'-'>)? gap-value
    gap-value = number | length | length-unit | percentage
    "
    :garden (fn [{data :component-data}]
              (let [{:keys [axis gap-value]} (into {} data)]
                {({"x" :column-gap
                   "y" :row-gap
                   nil :gap} axis)
                 (value-unit->css gap-value {:number-unit :quarter-rem})}))}])
