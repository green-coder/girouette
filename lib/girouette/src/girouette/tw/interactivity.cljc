(ns ^:no-doc girouette.tw.interactivity
  (:require
   [girouette.tw.color :refer [color->css]]))

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


   {:id :outline
    :rules "
    outline = <'outline-'> ('none' | 'white' | 'black')
    "
    :garden (fn [{[type] :component-data}]
              {:outline ({"none" "2px solid transparent"
                          "white" "2px dotted white"
                          "black" "2px dotted black"} type)
               :outline-offset "2px"})}


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
