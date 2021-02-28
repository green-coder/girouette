(ns acme.frontend.annotated
  (:require [girouette.core :refer [css]]))

;; -----------------------------------------------
;;   Use {:retrieve :annotated} to collect those
;; -----------------------------------------------

(css "text-green-300")

;; This might be supported by the Girouette processor in a next version.
#_ (css (str "text-" "red" "-300"))

(defn annotated-example []
  [:h1 {:class (css "flex")}
   [:div {:class (css "flex-10")} "hello"]
   [:div {:class (css "flex-20")} "the"]
   [:div {:class (css "flex-90/3")} "world"]])
