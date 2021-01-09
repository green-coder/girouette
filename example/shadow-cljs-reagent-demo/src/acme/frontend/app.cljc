(ns acme.frontend.app
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [girouette.tw.reagent :refer [defc]]))

(enable-console-print!)

(defc simple-example []
  [:h1.flex
   [:div.flex-1 "hello"]
   [:div.flex-2 "the"]
   [:div {:class "flex-9/3"} "world"]])

(defn render []
  (rdom/render [simple-example] (js/document.getElementById "app")))

(defn init []
  (println "(init)")
  (render))

(defn ^:dev/before-load stop []
  (println "(stop)"))

(defn ^:dev/after-load start []
  (println "(start)")
  (render))
