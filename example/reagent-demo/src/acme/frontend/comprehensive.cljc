(ns acme.frontend.comprehensive
  (:require [girouette.core :refer [css]]))

;; ---------------------------------------------------
;;   Use {:retrieve :comprehensive} to collect those
;;   (it's the default retrieval method)
;; ---------------------------------------------------

(defn compact-example []
  [:h1.flex
   [:div.flex-1 "hello"]
   [:div.flex-2 "the"]
   [:div.flex-3 "world"]])
