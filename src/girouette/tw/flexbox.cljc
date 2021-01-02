(ns girouette.tw.flexbox
  (:require [girouette.tw.common :refer [value-unit->css]]))

(def components
  [{:id :flex-grow
    :rules "
    flex-grow = <'flex-grow'> (<'-'> flex-grow-value)?
    flex-grow-value = number | fraction
    "
    :garden (fn [{[flex-grow-value] :component-data}]
              {:flex-grow (if-let [[_ data] flex-grow-value]
                            (value-unit->css nil data {})
                            "1")})}


   {:id :flex-shrink
    :rules "
    flex-shrink = <'flex-shrink'> (<'-'> flex-shrink-value)?
    flex-shrink-value = number | fraction
    "
    :garden (fn [{[flex-shrink-value] :component-data}]
              {:flex-shrink (if-let [[_ data] flex-shrink-value]
                              (value-unit->css nil data {})
                              "1")})}


   {:id :flex-basis
    :rules "
    flex-basis = <'flex-basis'> (<'-'> flex-basis-value)?
    flex-basis-value = number | length | length-unit | fraction | percentage-full | auto
    "
    :garden (fn [{[flex-basis-value] :component-data}]
              {:flex-basis (if-let [[_ data] flex-basis-value]
                             (value-unit->css nil data {:number-unit :quarter-rem
                                                        :fraction-unit "%"})
                             "1")})}


   {:id :flex-shorthand
    :rules "
    flex-shorthand = <'flex-'> (flex-shorthand-1-arg | flex-shorthand-2-args | flex-shorthand-3-args)
    flex-shorthand-1-arg = number | 'auto' | 'initial' | 'none'
    flex-shorthand-2-args = flex-grow-value <'-'> (flex-shrink-value | flex-basis-value)
    flex-shorthand-3-args = flex-grow-value <'-'> flex-shrink-value <'-'> flex-basis-value
    "
    :garden (fn [{[[shorthand-type & args]] :component-data}]
              {:flex (case shorthand-type
                       :flex-shorthand-1-arg
                       (case (first args)
                         "none"    "none"
                         "initial" "0 1 auto"
                         "auto"    "1 1 auto"
                         (let [size (value-unit->css nil (first args) {})]
                           (str size " " size " 0%")))

                       :flex-shorthand-2-args
                       (let [[[_ grow-data] [_ shrink-basis-data]] args
                             grow-value (value-unit->css nil grow-data {})
                             shrink-basis-value (value-unit->css nil shrink-basis-data {})]
                         (str grow-value " " shrink-basis-value))

                       :flex-shorthand-3-args
                       (let [[[_ grow-data] [_ shrink-data] [_ basis-data]] args
                             grow-value (value-unit->css nil grow-data {})
                             shrink-value (value-unit->css nil shrink-data {})
                             basis-value (value-unit->css nil basis-data {:number-unit :quarter-rem
                                                                          :fraction-unit "%"})]
                         (str grow-value " " shrink-value " " basis-value)))})}


   {:id :flex-direction
    :rules "
    flex-direction = <'flex-'> ('row' | 'row-reverse' | 'col' | 'col-reverse')
    "
    :garden (fn [{[direction] :component-data}]
              {:flex-direction ({"row" "row"
                                 "row-reverse" "row-reverse"
                                 "col" "column"
                                 "col-reverse" "column-reverse"} direction)})}


   {:id :flex-wrap
    :rules "
    flex-wrap = <'flex-'> ('wrap' | 'wrap-reverse' | 'nowrap')
    "
    :garden (fn [{[wrap] :component-data}]
              {:flex-wrap wrap})}


   {:id :order
    :rules "
    order = signus? <'order-'> order-param
    order-param = integer | 'first' | 'last' | 'none'
    "
    :garden (fn [props]
              (let [{:keys [signus order-param]} (into {} (:component-data props))]
                {:order (case order-param
                          "first" "-9999"
                          "last" "9999"
                          "none" "0"
                          (value-unit->css signus order-param {}))}))}])
