(ns acme.frontend.app
  (:require [reagent.dom :as rdom]
            [girouette.core :refer [css]]))

(defn simple-example []
  [:main

   [:div.bg-white.dark:bg-gray-800.p-10
    [:h1.text-3xl.text-gray-600.dark:text-gray-100 "Dark Mode Test"]]

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
    [:div.flex-1.p-4.text-center.text-4xl.text-cat-orange.bg-cat-black "Miaw!!!"]]

   ;; Demonstrate the shadow colors
   [:div.m-4.p-4.grid.grid-cols-3.gap-4.justify-items-center.text-lg.border-1.rounded-lg
    [:p.font-medium.text-blueGray-500.font-mono.text-center.mb-3.dark:text-blueGray-400 "shadow-cyan-500/50"]
    [:p.font-medium.text-blueGray-500.font-mono.text-center.mb-3.dark:text-blueGray-400 "shadow-blue-500/50"]
    [:p.font-medium.text-blueGray-500.font-mono.text-center.mb-3.dark:text-blueGray-400 "shadow-indigo-500/50"]
    [:button.py-2.px-3.mb-4.bg-cyan-500.text-white.font-semibold.rounded-md.shadow-lg.shadow-cyan-500-50.focus:outline-none "Clojure rules"]
    [:button.py-2.px-3.mb-4.bg-blue-500.text-white.font-semibold.rounded-md.shadow-lg.shadow-blue-500-50.focus:outline-none "Clojure rules"]
    [:button.py-2.px-3.mb-4.bg-indigo-500.text-white.font-semibold.rounded-md.shadow-lg.shadow-indigo-500-50.focus:outline-none "Clojure rules"]]])

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
