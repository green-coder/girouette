(ns ^:no-doc girouette.tw.layout
  (:require [clojure.string :as str]
            [girouette.tw.common :as common :refer [value-unit->css breakpoint->pixels div-4 mul-100 ratio-str]]))

(def components
  [{:id :aspect-ratio
    :since-version [:tw 3]
    :rules "
    aspect-ratio = <'aspect-'> (aspect-ratio-fixed | aspect-ratio-as-ratio)
    aspect-ratio-fixed = 'auto' | 'square' | 'video'
    aspect-ratio-as-ratio = ratio
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [aspect-ratio-fixed aspect-ratio-as-ratio]} (into {} component-data)]
                {:aspect-ratio (if (some? aspect-ratio-fixed)
                                 (case aspect-ratio-fixed
                                   "auto" "auto"
                                   "square" "1 / 1"
                                   "video" "16 / 9")
                                 (value-unit->css aspect-ratio-as-ratio {:ratio {:value-fn ratio-str}}))}))}


   {:id :container
    :since-version [:tw 2]
    :rules "
    container = <'container'>
    "
    :garden (fn [props]
              (if-some [media-query-min-width (-> props :prefixes :media-query-min-width)]
                {:max-width (breakpoint->pixels media-query-min-width)}
                {:width "100%"}))}


   {:id :columns
    :since-version [:tw 3]
    :rules  "
    columns = <'columns-'> (columns-count | columns-width | columns-count <'-'> columns-width)
    columns-count = integer | 'auto'
    columns-width = '3xs' | '2xs' | 'xs' | 'sm' | 'md' | 'lg' |
                    'xl' | '2xl' | '3xl' | '4xl' | '5xl' | '6xl' | '7xl' |
                    length | 'auto'
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [columns-count columns-width]} (into {} component-data)]
                {:columns (->> [(when (some? columns-count)
                                  (if (= columns-count "auto")
                                    "auto"
                                    (value-unit->css columns-count)))
                                (when (some? columns-width)
                                  (case columns-width
                                    "3xs"  "16rem"
                                    "2xs"  "18rem"
                                    "xs"   "20rem"
                                    "sm"   "24rem"
                                    "md"   "28rem"
                                    "lg"   "32rem"
                                    "xl"   "36rem"
                                    "2xl"  "42rem"
                                    "3xl"  "48rem"
                                    "4xl"  "56rem"
                                    "5xl"  "64rem"
                                    "6xl"  "72rem"
                                    "7xl"  "80rem"
                                    "auto" "auto"
                                    (value-unit->css columns-width)))]
                               (remove nil?)
                               (str/join " "))}))}


   {:id :break-after
    :since-version [:tw 3]
    :rules "
    break-after = <'break-after-'> ('auto' | 'avoid' | 'all' | 'avoid-page' |
                                    'page' | 'left' | 'right' | 'column')
    "
    :garden (fn [{[value] :component-data}]
              {:break-after value})}


   {:id :break-before
    :since-version [:tw 3]
    :rules "
    break-before = <'break-before-'> ('auto' | 'avoid' | 'all' | 'avoid-page' |
                                      'page' | 'left' | 'right' | 'column')
    "
    :garden (fn [{[value] :component-data}]
              {:break-before value})}


   {:id :break-inside
    :since-version [:tw 3]
    :rules "
    break-inside = <'break-inside-'> ('auto' | 'avoid' | 'avoid-page' | 'avoid-column')
    "
    :garden (fn [{[value] :component-data}]
              {:break-inside value})}


   {:id :box-decoration-break
    :since-version [:tw 3]
    :rules "
    box-decoration-break = <'box-decoration-'> ('clone' | 'slice')
    "
    :garden (fn [{[decoration-break] :component-data}]
              {:box-decoration-break decoration-break})}


   {:id :box-sizing
    :since-version [:tw 2]
    :rules "
    box-sizing = <'box-'> ('border' | 'content')
    "
    :garden (fn [{[box-model] :component-data}]
              {:box-sizing (case box-model
                             "border" "border-box"
                             "content" "content-box")})}


   {:id :display
    :since-version [:tw 2]
    :rules "
    display = 'block' | 'inline-block' | 'inline' | 'flex' | 'inline-flex' |
        'table' | 'inline-table' | 'table-caption' | 'table-cell' | 'table-column' |
        'table-column-group' | 'table-footer-group' | 'table-header-group' | 'table-row-group' |
        'table-row' | 'flow-root' | 'grid' |'inline-grid' | 'contents' | 'list-item' | 'hidden'
    "
    :garden (fn [{[display-mode] :component-data}]
              {:display (if (= "hidden" display-mode)
                          "none"
                          display-mode)})}


   {:id :floats
    :since-version [:tw 2]
    :rules "
    floats = <'float-'> ('left' | 'right' | 'none')
    "
    :garden (fn [{[direction] :component-data}]
              {:float direction})}


   {:id :clear
    :since-version [:tw 2]
    :rules "
    clear = <'clear-'> ('left' | 'right' | 'both' | 'none')
    "
    :garden (fn [{[direction] :component-data}]
              {:clear direction})}

   {:id :isolation
    :since-version [:tw 3]
    :rules "
    isolation = 'isolate' | <'isolation-'> 'auto'
    "
    :garden (fn [{[isolation] :component-data}]
              {:isolation isolation})}

   {:id :object-fit
    :since-version [:tw 2]
    :rules "
    object-fit = <'object-'> ('contain' | 'cover' | 'fill' | 'none' | 'scale-down')
    "
    :garden (fn [{[fitness] :component-data}]
              {:object-fit fitness})}


   {:id :object-position
    :since-version [:tw 2]
    :rules  "
    object-position = <'object-'> object-position-side
    <object-position-side> = 'left' | 'left-bottom' | 'left-top' |
                             'right' | 'right-bottom' | 'right-top' |
                             'center' | 'bottom' | 'top'
    "
    :garden (fn [{[position] :component-data}]
              {:object-position (str/escape position {\- \space})})}


   {:id :overflow
    :since-version [:tw 2]
    :rules "
    overflow = <'overflow-'> (axis <'-'>)? overflow-mode
    overflow-mode = 'auto' | 'hidden' | 'clip' | 'visible' | 'scroll'
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [axis overflow-mode]} (into {} component-data)
                    property (str "overflow" (when axis (str "-" axis)))]
                {(keyword property) overflow-mode}))}


   {:id :overscroll
    :since-version [:tw 2]
    :rules "
    overscroll = <'overscroll-'> (axis <'-'>)? overscroll-mode
    overscroll-mode = 'auto' | 'contain' | 'none'
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [axis overscroll-mode]} (into {} component-data)
                    property (str "overscroll" (when axis (str "-" axis)))]
                {(keyword property) overscroll-mode}))}


   {:id :position
    :since-version [:tw 2]
    :rules "
    position = 'static' | 'fixed' | 'absolute' | 'relative' | 'sticky'
    "
    :garden (fn [{[position] :component-data}]
              {:position position})}


   {:id :positioning
    :since-version [:tw 2]
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
                                                :number (common/default-number-value-option)
                                                :fraction {:unit "%"
                                                           :value-fn mul-100}})]
                (into {}
                      (map (fn [direction] [direction value-css]))
                      directions)))}


   {:id :visibility
    :since-version [:tw 2]
    :rules "
    visibility = 'visible' | 'invisible'
    "
    :garden (fn [{[visibility] :component-data}]
              {:visibility ({"visible"   "visible"
                             "invisible" "hidden"} visibility)})}


   {:id :z-index
    :since-version [:tw 2]
    :rules "
    z-index = <'z-'> (integer | auto)
    "
    :garden (fn [{[index] :component-data}]
              {:z-index (value-unit->css index)})}])
