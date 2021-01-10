(ns girouette.tw.box-alignment)

(def components
  [{:id :justify-content
    :rules "
    justify-content = <'justify-'> ('start' | 'end' | 'center' | 'between' | 'around' | 'evenly')
    "
    :garden (fn [{[param] :component-data}]
              {:justify-content ({"start" "flex-start"
                                  "end" "flex-end"
                                  "center" "center"
                                  "between" "space-between"
                                  "around" "space-around"
                                  "evenly" "space-evenly"} param)})}


   {:id :justify-items
    :rules "
    justify-items = <'justify-items-'> ('auto' | 'start' | 'end' | 'center' | 'stretch')
    "
    :garden (fn [{[param] :component-data}]
              {:justify-items param})}


   {:id :justify-self
    :rules "
    justify-self = <'justify-self-'> ('auto' | 'start' | 'end' | 'center' | 'stretch')
    "
    :garden (fn [{[param] :component-data}]
              {:justify-self param})}


   {:id :align-content
    :rules "
    align-content = <'content-'> ('start' | 'end' | 'center' | 'between' | 'around' | 'evenly')
    "
    :garden (fn [{[param] :component-data}]
              {:align-content ({"start" "flex-start"
                                "end" "flex-end"
                                "center" "center"
                                "between" "space-between"
                                "around" "space-around"
                                "evenly" "space-evenly"} param)})}


   {:id :align-items
    :rules "
    align-items = <'items-'> ('start' | 'end' | 'center' | 'baseline' | 'stretch')
    "
    :garden (fn [{[param] :component-data}]
              {:align-items ({"start" "flex-start"
                              "end" "flex-end"
                              "center" "center"
                              "baseline" "baseline"
                              "stretch" "stretch"} param)})}


   {:id :align-self
    :rules "
    align-self = <'self-'> ('auto' | 'start' | 'end' | 'center' | 'stretch')
    "
    :garden (fn [{[param] :component-data}]
              {:align-self ({"auto" "auto"
                             "start" "flex-start"
                             "end" "flex-end"
                             "center" "center"
                             "stretch" "stretch"} param)})}


   {:id :place-content
    :rules "
    place-content = <'place-content-'> ('start' | 'end' | 'center' | 'between' | 'around' | 'evenly' | 'stretch')
    "
    :garden (fn [{[param] :component-data}]
              {:place-content ({"start" "start"
                                "end" "end"
                                "center" "center"
                                "between" "space-between"
                                "around" "space-around"
                                "evenly" "space-evenly"
                                "stretch" "stretch"} param)})}


   {:id :place-items
    :rules "
    place-items = <'place-items-'> ('auto' | 'start' | 'end' | 'center' | 'stretch')
    "
    :garden (fn [{[param] :component-data}]
              {:place-items param})}


   {:id :place-self
    :rules "
    place-self = <'place-self-'> ('auto' | 'start' | 'end' | 'center' | 'stretch')
    "
    :garden (fn [{[param] :component-data}]
              {:place-self param})}])
