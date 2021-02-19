(ns acme.frontend.app
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [girouette.core :refer [css]]))

(enable-console-print!)

(css "text-green-300")

#_(defn simple-example1 []
    ^:hiccup
    [:h1.flex
     [:div.flex-1 "hello"]
     [:div.flex-2 "the"]
     [:div {:class "flex-9/3"} "world"]
     (if true
       [:div.true]
       [:div.false])
     (for [a (range 3)]
       [:div.item "my item"])])

(defn simple-example2 []
  [:h1 {:class (css :flex)}
   [:div {:class (css "flex-1")} "hello"]
   [:div {:class (css "flex-2")} "the"]
   [:div {:class (css "flex-9/3")} "world"]])

(defn render []
  (rdom/render [simple-example2] (js/document.getElementById "app")))

(defn init []
  (println "(init)")
  (render))

(defn ^:dev/before-load stop []
  (println "(stop)"))

(defn ^:dev/after-load start []
  (println "(start)")
  (render))
