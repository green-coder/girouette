(ns acme.frontend.foobar
  (:require [girouette.core :refer [css]]))

(css "text-green-300")

(defn simple-example []
  [:h1 {:class (css :flex)}
   [:div {:class (css "flex-10")} "hello"]
   [:div {:class (css "flex-20")} "the"]
   [:div {:class (css "flex-21")} "the"]
   [:div {:class (css "flex-22")} "the"]
   [:div {:class (css "flex-23")} "the"]
   [:div {:class (css "flex-24")} "the"]
   [:div {:class (css "flex-90/3")} "world"]])
