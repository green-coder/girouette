(ns girouette.tw.reagent
  (:require
    #? (:clj [clojure.walk :refer [macroexpand-all]])
    [girouette.grammar.hiccup-tag :refer [hiccup-tag-parser]]
    [girouette.tw.core :refer [parser class-name->garden]])
  #? (:cljs (:require-macros [girouette.tw.reagent])))

;; This is just good enough for the demo, will be improved shortly after.
#? (:clj (defn- fn-body->class-names [body]
           (->> body
                macroexpand-all
                flatten
                (mapcat (fn [x]
                          (concat
                            (when (keyword? x)
                              (let [[_ & matches] (-> x name hiccup-tag-parser)]
                                (->> matches
                                     (filter (comp #{:class-name} first))
                                     (map second))))
                            (when (map? x)
                              (->> [(:class x)]
                                   flatten
                                   (filter string?))))))
                set)))

;; This is just good enough for the demo, will be improved shortly after.
(defmacro defc [name params body]
  `(defn ~name
     ~{:girouette.css/class-names (fn-body->class-names body)}
     ~params ~body))


(comment
  (defc todo-list [items]
    [:div.m-2#app {:class ["foo" "bar"]}
     (for [i items]
       [:div.p-5 {:class "container"}])])

  (meta #'todo-list))
