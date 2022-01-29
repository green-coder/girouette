(ns ^:no-doc girouette.tw.interactivity)

(def components
  [{:id :appearance
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


   {:id :user-select
    :rules "
    user-select = <'select-'> ('none' | 'text' | 'all' | 'auto')
    "
    :garden (fn [{[type] :component-data}]
              {:user-select type})}])
