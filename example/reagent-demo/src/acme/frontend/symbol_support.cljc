(ns acme.frontend.symbol-support
  (:require [girouette.core :refer [css]]))

;; This proves that symbols work as well.
;; Symbols do not however work when there is a slash
;; as it semantically indicates a namespace to the parser.
;; `[:tag {class: '[property-n/n]}]` will fail.

(defn symbol-example []
  [:p {:class '[font-serif dark:text-gray-200 "flex-4/3"]}])
