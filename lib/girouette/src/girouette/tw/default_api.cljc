(ns girouette.tw.default-api
  (:require
    [girouette.tw.core :as gtw]
    [girouette.version :as version]
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
    [girouette.tw.effect :as effect]
    [girouette.tw.filter :as filter]
    [girouette.tw.table :as table]
    [girouette.tw.animation :as animation]
    [girouette.tw.transform :as transform]
    [girouette.tw.interactivity :as interactivity]
    [girouette.tw.svg :as svg]
    [girouette.tw.accessibility :as accessibility]))

(def all-tw-components
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
   effect/components
   filter/components
   table/components
   animation/components
   transform/components
   interactivity/components
   svg/components
   accessibility/components])


;; The API built using the Tailwind v2 components.
(let [{:keys [parser class-name->garden]} (-> all-tw-components
                                              (version/filter-components-by-version [:tw 2])
                                              (gtw/make-api {:color-map color/tw-v2-colors
                                                             :font-family-map typography/tw-v2-font-family-map}))]
  (def tw-v2-parser parser)
  (def tw-v2-class-name->garden class-name->garden))


;; The API built using the Tailwind v3 components.
(let [{:keys [parser class-name->garden]} (-> all-tw-components
                                              (version/filter-components-by-version [:tw 3])
                                              (gtw/make-api {:color-map color/tw-v3-unified-colors-extended
                                                             :font-family-map typography/tw-v2-font-family-map}))]
  (def tw-v3-parser parser)
  (def tw-v3-class-name->garden class-name->garden))


;; Feel free to fork the snippet above and add your own components,
;; as that's what Girouette was made for: customization.
