(ns acme.frontend.my-grammar
  (:require
    [girouette.tw.core :refer [make-api]]
    [girouette.util :as util]
    [girouette.tw.common :as common]
    [girouette.tw.color :as color]
    [girouette.tw.layout :as layout]
    [girouette.tw.flexbox :as flexbox]
    [girouette.tw.grid :as grid]
    [girouette.tw.box-alignment :as box-alignment]
    [girouette.tw.spacing :as spacing]
    [girouette.tw.sizing :as sizing]
    [girouette.tw.typography :as typography]
    [girouette.tw.background :as background]
    [girouette.tw.border :as border]
    ; [girouette.tw.effect :as effect]
    ; [girouette.tw.table :as table]
    ; [girouette.tw.animation :as animation]
    ; [girouette.tw.transform :as transform]
    ; [girouette.tw.interactivity :as interactivity]
    ; [girouette.tw.svg :as svg]
    ; [girouette.tw.accessibility :as accessibility]
    ,))


(def my-custom-components
  [{:id :rainbow-text
    :rules "
    rainbow-text = <'rainbow-text'>
    "
    :garden (fn [_]
              {:background-image "linear-gradient(to left, violet, indigo, blue, green, yellow, orange, red)"
               :background-clip "text"
               ;:-webkit-background-clip "text"
               :color "transparent"})}]);})}])


(def my-chosen-components
  (util/into-one-vector
    [common/components
     layout/components
     flexbox/components
     grid/components
     box-alignment/components
     spacing/components
     sizing/components
     typography/components
     background/components
     border/components
     ;effect/components
     ;table/components
     ;animation/components
     ;transform/components
     ;interactivity/components
     ;svg/components
     ;accessibility/components
     my-custom-components
     ,]))


;; Adds colors to the existing default ones.
(def my-color-map
  (assoc color/default-color-map
    "cat-white"  "eeeeee"
    "cat-orange" "e58c56"
    "cat-black"  "333333"))


;; This example shows how to Girouette on a custom grammar.
;; Here, we use only a subset of the Girouette components and we add your own.
(def class-name->garden
  (:class-name->garden (make-api my-chosen-components {:color-map my-color-map
                                                       :font-family-map typography/default-font-family-map})))
