(ns acme.frontend.drop-in
  (:require [girouette.core :refer [css]]))

;; This proves that girouette
;; can work as a drop-in replacement for tailwind.

(defn drop-in-example []
  [:h1 {:class '[text-5xl underline font-serif dark:text-gray-100]}])
