(ns ^:no-doc girouette.tw.layout
  (:require [clojure.string :as str]
            [girouette.tw.common :refer [value-unit->css breakpoint->pixels div-4 mul-100]]))

(def components
  [{:id :container
    :rules "
    container = <'container'>
    "
    :garden (fn [props]
              (if-let [media-query-min-width (-> props :prefixes :media-query-min-width)]
                {:max-width (breakpoint->pixels media-query-min-width)}
                {:width "100%"}))}


   {:id :box-sizing
    :rules "
    box-sizing = 'box-border' | 'box-content'
    "
    :garden (fn [{[box-model] :component-data}]
              {:box-sizing (case box-model
                             "box-border" "border-box"
                             "box-content" "content-box")})}


   {:id :display
    :rules "
    display = 'block' | 'inline-block' | 'inline' | 'flex' | 'inline-flex' |
        'table' | 'table-caption' | 'table-cell' | 'table-column' | 'table-column-group' |
        'table-footer-group' | 'table-header-group' | 'table-row-group' | 'table-row' | 'inline-table' |
        'flow-root' | 'grid' |'inline-grid' | 'contents' | 'list-item' | 'hidden'
    "
    :garden (fn [{[display-mode] :component-data}]
              {:display (if (= "hidden" display-mode)
                          "none"
                          display-mode)})}


   {:id :floats
    :rules "
    floats = <'float-'> ('left' | 'right' | 'none')
    "
    :garden (fn [{[direction] :component-data}]
              {:float direction})}


   {:id :clear
    :rules "
    clear = <'clear-'> ('left' | 'right' | 'both' | 'none')
    "
    :garden (fn [{[direction] :component-data}]
              {:clear direction})}

   {:id :isolation
    :rules "
    isolation = 'isolate' | <'isolation-'> 'auto'
    "
    :garden (fn [{[isolation] :component-data}]
              {:isolation isolation})}

   {:id :object-fit
    :rules "
    object-fit = <'object-'> ('contain' | 'cover' | 'fill' | 'none' | 'scale-down')
    "
    :garden (fn [{[fitness] :component-data}]
              {:object-fit fitness})}


   {:id :object-position
    :rules  "
    object-position = <'object-'> object-position-side
    <object-position-side> = 'left' | 'left-bottom' | 'left-top' |
                             'right' | 'right-bottom' | 'right-top' |
                             'center' | 'bottom' | 'top'
    "
    :garden (fn [{[position] :component-data}]
              {:object-position (str/escape position {\- \space})})}


   {:id :overflow
    :rules "
    overflow = <'overflow-'> (axis <'-'>)? overflow-mode
    overflow-mode = 'auto' | 'hidden' | 'visible' | 'scroll'
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [axis overflow-mode]} (into {} component-data)
                    property (str "overflow" (when axis (str "-" axis)))]
                {(keyword property) overflow-mode}))}


   {:id :overscroll
    :rules "
    overscroll = <'overscroll-'> (axis <'-'>)? overscroll-mode
    overscroll-mode = 'auto' | 'contain' | 'none'
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [axis overscroll-mode]} (into {} component-data)
                    property (str "overscroll" (when axis (str "-" axis)))]
                {(keyword property) overscroll-mode}))}


   {:id :position
    :rules "
    position = 'static' | 'fixed' | 'absolute' | 'relative' | 'sticky'
    "
    :garden (fn [{[position] :component-data}]
              {:position position})}


   {:id :positioning
    :rules "
    positioning = signus? positioning-mode <'-'> positioning-value
    positioning-mode = 'top' | 'right' | 'bottom' | 'left' | #'inset(-x|-y)?'
    positioning-value = number | length | length-unit | fraction | percentage | full-100% | auto
    "
    :garden (fn [{component-data :component-data}]
              (let [{:keys [signus positioning-mode positioning-value]} (into {} component-data)
                    directions ({"inset" [:top :right :bottom :left]
                                 "inset-x" [:right :left]
                                 "inset-y" [:top :bottom]
                                 "top" [:top]
                                 "right" [:right]
                                 "bottom" [:bottom]
                                 "left" [:left]} positioning-mode)
                    value-css (value-unit->css positioning-value
                                               {:signus signus
                                                :zero-unit nil
                                                :number {:unit "rem"
                                                         :value-fn div-4}
                                                :fraction {:unit "%"
                                                           :value-fn mul-100}})]
                (into {}
                      (map (fn [direction] [direction value-css]))
                      directions)))}


   {:id :visibility
    :rules "
    visibility = 'visible' | 'invisible'
    "
    :garden (fn [{[visibility] :component-data}]
              {:visibility ({"visible"   "visible"
                             "invisible" "hidden"} visibility)})}


   {:id :z-index
    :rules "
    z-index = <'z-'> (integer | auto)
    "
    :garden (fn [{[index] :component-data}]
              {:z-index (value-unit->css index)})}])
