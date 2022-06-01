(ns ^:no-doc girouette.tw.interactivity
  (:require
   [girouette.tw.color :refer [color->css]]
   [girouette.tw.common :refer [value-unit->css div-4 mul-100]]))

(def components
  [{:id :accent-color
    :since-version [:tw 3]
    :rules "
    accent-color = <'accent-'> ('inherit' | 'auto' | color)
    "
    :garden (fn [{[color] :component-data read-color :read-color}]
              {:accent-color (case color
                               "inherit" "inherit"
                               "auto" "auto"
                               (color->css (read-color color)))})}

   {:id :appearance
    :since-version [:tw 2]
    :rules "
    appearance = 'appearance-none'
    "
    :garden (fn [_]
              {:appearance "none"})}


   {:id :cursor
    :since-version [:tw 2]
    :rules "
    cursor = <'cursor-'> ('auto' | 'default' | 'pointer' | 'wait' |
                          'text' | 'move' | 'help' | 'not-allowed' |
                          'none' | 'context-menu' | 'progress' | 'cell' |
                          'crosshair' | 'vertical-text' | 'alias' | 'copy' |
                          'no-drop' | 'grab' | 'grabbing' | 'all-scroll' |
                          'col-resize' | 'row-resize' |
                          'n-resize' | 'e-resize' | 's-resize' | 'w-resize' |
                          'ne-resize' | 'nw-resize' | 'se-resize' | 'sw-resize' |
                          'nesw-resize' | 'nwse-resize' |
                          'zoom-in' | 'zoom-out')
    "
    :garden (fn [{[type] :component-data}]
              {:cursor type})}


   {:id :outline
    :since-version [:tw 2]
    :removed-in-version [:tw 3]
    :rules "
    outline = <'outline-'> ('none' | 'white' | 'black')
    "
    :garden (fn [{[type] :component-data}]
              {:outline ({"none" "2px solid transparent"
                          "white" "2px dotted white"
                          "black" "2px dotted black"} type)
               :outline-offset "2px"})}


   {:id :pointer-events
    :since-version [:tw 2]
    :rules "
    pointer-events = <'pointer-events-'> ('none' | 'auto')
    "
    :garden (fn [{[type] :component-data}]
              {:pointer-events type})}


   {:id :resize
    :since-version [:tw 2]
    :rules "
    resize = <'resize'> (<'-'> ('none' | 'x' | 'y'))?
    "
    :garden (fn [{[type] :component-data}]
              {:resize ({"none" "none"
                         "x" "horizontal"
                         "y" "vertical"
                         nil "both"} type)})}


   {:id :scroll-behavior
    :since-version [:tw 3]
    :rules "
    scroll-behavior = <'scroll-'> ('auto' | 'smooth')
    "
    :garden (fn [{[value] :component-data}]
              {:scroll-behavior value})}


   {:id :scroll-margin
    :since-version [:tw 3]
    :rules "
    scroll-margin = signus? <'scroll-m'> (direction | axis)? <'-'> scroll-margin-value
    scroll-margin-value = number | length | length-unit
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [signus direction axis scroll-margin-value]} (into {} component-data)
                    directions (case direction
                                 "t" ["top"]
                                 "r" ["right"]
                                 "b" ["bottom"]
                                 "l" ["left"]
                                 (case axis
                                   "x" ["left" "right"]
                                   "y" ["top" "bottom"]
                                   nil))
                    props (if (nil? directions)
                            [:scroll-margin]
                            (mapv (fn [dir]
                                    (keyword (str "scroll-margin-" dir)))
                                  directions))
                    value-css (value-unit->css scroll-margin-value
                                               {:signus signus
                                                :zero-unit "px"
                                                :number {:unit "rem"
                                                         :value-fn div-4}})]
                (into {}
                      (map (fn [prop]
                             [prop value-css]))
                      props)))}


   {:id :scroll-padding
    :since-version [:tw 3]
    :rules "
    scroll-padding = signus? <'scroll-p'> (direction | axis)? <'-'> scroll-padding-value
    scroll-padding-value = number | length | length-unit | fraction | percentage
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [signus direction axis scroll-padding-value]}
                    (into {} component-data)
                    directions (case direction
                                 "t" ["top"]
                                 "r" ["right"]
                                 "b" ["bottom"]
                                 "l" ["left"]
                                 (case axis
                                   "x" ["left" "right"]
                                   "y" ["top" "bottom"]
                                   nil))
                    props (if (nil? directions)
                            [:scroll-padding]
                            (mapv (fn [dir]
                                    (keyword (str "scroll-padding-" dir)))
                                  directions))
                    value-css (value-unit->css scroll-padding-value
                                               {:signus signus
                                                :zero-unit "px"
                                                :number {:unit "rem"
                                                         :value-fn div-4}
                                                :fraction {:unit "%"
                                                           :value-fn mul-100}})]
                (into {}
                      (map (fn [prop]
                             [prop value-css]))
                      props)))}


   {:id :scroll-snap-align
    :since-version [:tw 3]
    :rules "
    scroll-snap-align = <'snap-'> ('start' | 'end' | 'center' | (<'align-'> 'none'))
    "
    :garden (fn [{[value] :component-data}]
              {:scroll-snap-align value})}


   {:id :scroll-snap-stop
    :since-version [:tw 3]
    :rules "
    scroll-snap-stop = <'snap-'> ('normal' | 'always')
    "
    :garden (fn [{[value] :component-data}]
              {:scroll-snap-stop value})}


   {:id :scroll-snap-type
    :since-version [:tw 3]
    :rules "
    scroll-snap-type = <'snap-'> (scroll-snap-type-dir | scroll-snap-type-strictness)
    scroll-snap-type-dir = 'none' | 'x' | 'y' | 'both'
    scroll-snap-type-strictness = 'mandatory' | 'proximity'
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [scroll-snap-type-dir scroll-snap-type-strictness]} (into {} component-data)]
                (cond
                  (some? scroll-snap-type-strictness)
                  {:--gi-scroll-snap-strictness scroll-snap-type-strictness}

                  (= scroll-snap-type-dir "none")
                  {:scroll-snap-type "none"}

                  :else
                  {:scroll-snap-type (str scroll-snap-type-dir " var(--gi-scroll-snap-strictness)")})))}


   {:id :touch-action
    :since-version [:tw 3]
    :rules "
    touch-action = <'touch-'> ('auto' | 'none' |
                               'pan-x' | 'pan-y' |
                               'pan-up' | 'pan-right' | 'pan-down' |'pan-left' |
                               'pinch-zoom' | 'manipulation')
    "
    :garden (fn [{[value] :component-data}]
              {:touch-action value})}


   {:id :user-select
    :since-version [:tw 2]
    :rules "
    user-select = <'select-'> ('none' | 'text' | 'all' | 'auto')
    "
    :garden (fn [{[selection-type] :component-data}]
              {:user-select selection-type})}


   {:id :will-change
    :since-version [:tw 3]
    :rules "
    will-change = <'will-change-'> ('auto' | 'scroll' | 'contents' | 'transform')
    "
    :garden (fn [{[value] :component-data}]
              {:will-change ({"auto"      "auto"
                              "scroll"    "scroll-position"
                              "contents"  "contents"
                              "transform" "transform"} value)})}])
