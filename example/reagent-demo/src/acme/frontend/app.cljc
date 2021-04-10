(ns acme.frontend.app
  (:require [reagent.dom :as rdom]
            [girouette.core :refer [css]]))

(enable-console-print!)

(defn simple-example []
  [:main

   ;; Demonstrates the use of arbitrary values in flex layouts
   [:h1.flex.space-x-2
    [:div.flex-1.p-4.text-center.rounded-lg.bg-red-200 "hello"]
    [:div.flex-2.p-4.text-center.rounded-lg.bg-green-200 "the"]
    [:div.p-4.text-center.rounded-lg.bg-blue-200 {:class "flex-9/3"} "world"]]

   ;; Demonstrates the use of a custom Girouette component which provides the CSS class "rainbow-text"
   [:div.rainbow-text.text-center.font-size-10vw
    "Everybody needs a rainbow in their life"]

   ;; Demonstrate the use of custom colors (e.g. cat-white, cat-orange, cat-black)
   [:div.flex
    [:div.flex-1.bg-cat-white]
    [:div.flex-1.bg-cat-orange]
    [:div.flex-1.bg-cat-black]
    [:div.flex-1.p-4.text-center.text-4xl.text-cat-black.bg-cat-white "Miaw!!!"]
    [:div.flex-1.p-4.text-center.text-4xl.text-cat-white.bg-cat-orange "Miaw!!!"]
    [:div.flex-1.p-4.text-center.text-4xl.text-cat-orange.bg-cat-black "Miaw!!!"]]])

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
