(ns ^:no-doc girouette.tw.table)

(def components
  [{:id :border-collapse
    :since-version [:tw 2]
    :rules "
    border-collapse = <'border-'> ('collapse' | 'separate')
    "
    :garden (fn [{[type] :component-data}]
              {:border-collapse type})}


   {:id :table-layout
    :since-version [:tw 2]
    :rules "
    table-layout = <'table-'> ('auto' | 'fixed')
    "
    :garden (fn [{[type] :component-data}]
              {:table-layout type})}])
