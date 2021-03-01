(ns ^:no-doc girouette.tw.table)

(def components
  [{:id :border-collapse
    :rules "
    border-collapse = <'border-'> ('collapse' | 'separate')
    "
    :garden (fn [{[type] :component-data}]
              {:border-collapse type})}


   {:id :table-layout
    :rules "
    table-layout = <'table-'> ('auto' | 'fixed')
    "
    :garden (fn [{[type] :component-data}]
              {:table-layout type})}])
