(ns ^:no-doc girouette.tw.interactivity
  (:require
   [girouette.tw.color :refer [color->css]]
   [girouette.tw.common :refer [value-unit->css div-4]]))

(def components
  [{:id :accent-color
    :rules "
    accent-color = <'accent-'> ( 'inherit' | 'current' | color )
    "
    :garden (fn [{[color] :component-data read-color :read-color}]
              {:accent-color (case color
                               "inherit" "inherit"
                               "current" "currentColor"
                               (color->css (read-color color)))})}

   {:id :appearance
    :rules "
    appearance = 'appearance-none'
    "
    :garden (fn [_]
              {:appearance "none"})}


   {:id :cursor
    :rules "
    cursor = <'cursor-'> ('auto' | 'default' | 'pointer' | 'wait' |
                          'text' | 'move' | 'not-allowed' | 'help' )
    "
    :garden (fn [{[type] :component-data}]
              {:cursor type})}


   {:id :pointer-events
    :rules "
    pointer-events = <'pointer-events-'> ('none' | 'auto')
    "
    :garden (fn [{[type] :component-data}]
              {:pointer-events type})}


   {:id :resize
    :rules "
    resize = <'resize'> (<'-'> ('none' | 'x' | 'y'))?
    "
    :garden (fn [{[type] :component-data}]
              {:resize ({"none" "none"
                         "x" "horizontal"
                         "y" "vertical"
                         nil "both"} type)})}

   {:id :scroll-behaviour
    :rules "
    scroll-behaviour = <'scroll-'> ( 'auto' | 'smooth' )
    "
    :garden (fn [{[value] :component-data}]
              {:scroll-behavior value})}

   {:id :scroll-snap-align
    :rules "
    scroll-snap-align = <'snap-'> ( 'start' | 'end' | 'center' | 'align-none' )
    "
    :garden (fn [{[value] :component-data}]
              {:scroll-snap-align (if (= value "align-none")
                                    "none"
                                    value)})}

   {:id :scroll-snap-stop
    :rules "
    scroll-snap-stop = <'snap-'> ( 'normal' | 'always' )
    "
    :garden (fn [{[value] :component-data}]
              {:scroll-snap-stop value})}

   {:id :scroll-snap-type
    :rules "
    scroll-snap-type = <'snap-'> ( scroll-snap-type-dir | scroll-snap-type-type )
    scroll-snap-type-dir = 'none' | 'x' | 'y' | 'both'
    scroll-snap-type-type = 'mandatory' | 'proxmity'
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [scroll-snap-type-dir scroll-snap-type-type]}
                    (into {} component-data)]
                (cond
                  scroll-snap-type-type
                 {:--gi-scroll-snap-strictness scroll-snap-type-type}

                 (= scroll-snap-type-dir "none")
                 {:scroll-snap-type "none"}

                 :else
                 {:scroll-snap-type
                  (str scroll-snap-type-dir
                       " var(--gi-scroll-snap-strictness,proximity)")})))}

   {:id :scroll-margin
    :rules "
    scroll-margin = signus? <'scroll-m'> (direction | axis)? <'-'> scroll-margin-value
    scroll-margin-value = number | length | length-unit
    "
    :garden (fn [{:keys [component-data]}]
              (let [{:keys [signus direction axis scroll-margin-value]}
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
                    value-css (value-unit->css scroll-margin-value
                                               {:signus signus
                                                :zero-unit "px"
                                                :number {:unit "rem"
                                                         :value-fn div-4}})]
                (if (nil? directions)
                  {:scroll-margin value-css}
                  (into {}
                        (map (fn [dir] [(keyword (str "scroll-margin-" dir))
                                        value-css]))
                        directions))))}

   {:id :scroll-padding
    :rules "
    scroll-padding = signus? <'scroll-p'> (direction | axis)? <'-'> scroll-padding-value
    scroll-padding-value = number | length | length-unit
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
                    value-css (value-unit->css scroll-padding-value
                                               {:signus signus
                                                :zero-unit "px"
                                                :number {:unit "rem"
                                                         :value-fn div-4}})]
                (if (nil? directions)
                  {:scroll-padding value-css}
                  (into {}
                        (map (fn [dir] [(keyword (str "scroll-padding-" dir))
                                        value-css]))
                        directions))))}

   {:id :touch-action
    :rules "
    touch-action = <'touch-'> ( 'auto' | 'none' | 'pan-x' | 'pan-y' |
                                'pan-left' | 'pan-right' | 'pan-up' | 'pan-down' |
                                'pinch-zoom' | 'manipulation' )
    "
    :garden (fn [{[value] :component-data}]
              {:touch-action value})}

   {:id :user-select
    :rules "
    user-select = <'select-'> ('none' | 'text' | 'all' | 'auto')
    "
    :garden (fn [{[type] :component-data}]
              {:user-select type})}

   {:id :will-change
    :rules "
    will-change = <'will-change-'> ( 'auto' | 'scroll' | 'contents' | 'transform' )
    "
    :garden (fn [{[value] :component-data}]
              {:will-change (if (= value "scroll")
                              "scroll-position"
                              value)})}])
