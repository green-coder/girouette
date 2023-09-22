(ns ^:no-doc girouette.tw.grid
  (:require [girouette.tw.common :refer [value-unit->css div-4]]))

(def components
  [{:id :grid-template-columns
    :since-version [:tw 2]
    :rules "
    grid-template-columns = <'grid-cols-'> (integer | none)
    "
    :garden (fn [{[repeat] :component-data}]
              (let [repeat (value-unit->css repeat)]
                {:grid-template-columns (if (#{"none" 0} repeat)
                                          "none"
                                          (str "repeat(" repeat ", minmax(0, 1fr))"))}))}


   {:id :grid-column-auto
    :since-version [:tw 2]
    :rules "
    grid-column-auto = 'col-auto'
    "
    :garden (fn [props]
              {:grid-column "auto"})}


   {:id :grid-column-span
    :since-version [:tw 2]
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
    :since-version [:tw 2]
    :rules "
    grid-column-start = <'col-start-'> (integer | auto)
    "
    :garden (fn [{[param] :component-data}]
              {:grid-column-start (value-unit->css param)})}


   {:id :grid-column-end
    :since-version [:tw 2]
    :rules "
    grid-column-end = <'col-end-'> (integer | auto)
    "
    :garden (fn [{[param] :component-data}]
              {:grid-column-end (value-unit->css param)})}


   {:id :grid-template-rows
    :since-version [:tw 2]
    :rules "
    grid-template-rows = <'grid-rows-'> (integer | none)
    "
    :garden (fn [{[repeat] :component-data}]
              (let [repeat (value-unit->css repeat)]
                {:grid-template-rows (if (#{"none" 0} repeat)
                                       "none"
                                       (str "repeat(" repeat ", minmax(0, 1fr))"))}))}


   {:id :grid-row-auto
    :since-version [:tw 2]
    :rules "
    grid-row-auto = 'row-auto'
    "
    :garden (fn [props]
              {:grid-row "auto"})}


   {:id :grid-row-span
    :since-version [:tw 2]
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
    :since-version [:tw 2]
    :rules "
    grid-row-start = <'row-start-'> (integer | auto)
    "
    :garden (fn [{[param] :component-data}]
              {:grid-row-start (value-unit->css param)})}


   {:id :grid-row-end
    :since-version [:tw 2]
    :rules "
    grid-row-end = <'row-end-'> (integer | auto)
    "
    :garden (fn [{[param] :component-data}]
              {:grid-row-end (value-unit->css param)})}


   {:id :grid-auto-flow
    :since-version [:tw 2]
    :rules "
    grid-auto-flow = <'grid-flow-'> ('row' | 'col' | 'row-dense' | 'col-dense')
    "
    :garden (fn [{[param] :component-data}]
              {:grid-auto-flow ({"row" "row"
                                 "col" "column"
                                 "row-dense" "row dense"
                                 "col-dense" "column dense"} param)})}


   {:id :grid-auto-columns
    :since-version [:tw 2]
    :rules "
    grid-auto-columns = <'auto-cols-'> ('auto' | 'min' | 'max' | 'fr')
    "
    :garden (fn [{[param] :component-data}]
              {:grid-auto-columns ({"auto" "auto"
                                    "min" "min-content"
                                    "max" "max-content"
                                    "fr" "minmax(0, 1fr)"} param)})}


   {:id :grid-auto-rows
    :since-version [:tw 2]
    :rules "
    grid-auto-rows = <'auto-rows-'> ('auto' | 'min' | 'max' | 'fr')
    "
    :garden (fn [{[param] :component-data}]
              {:grid-auto-rows ({"auto" "auto"
                                 "min" "min-content"
                                 "max" "max-content"
                                 "fr" "minmax(0, 1fr)"} param)})}


   {:id :gap
    :since-version [:tw 2]
    :rules "
    gap = <'gap-'> (axis <'-'>)? gap-value
    gap-value = number | length | length-unit | percentage
    "
    :garden (fn [{data :component-data
                  :keys [unitless-length-conversion]}]
              (let [{:keys [axis gap-value]} (into {} data)]
                {({"x" :column-gap
                   "y" :row-gap
                   nil :gap} axis)
                 (value-unit->css gap-value {:zero-unit nil
                                             :number unitless-length-conversion})}))}])
