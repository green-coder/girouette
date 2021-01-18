(ns girouette.tw.default-api
  (:require
    [girouette.tw.core :refer [make-api]]
    [girouette.util :as util]
    [girouette.tw.common :as common]
    [girouette.tw.layout :as layout]
    [girouette.tw.flexbox :as flexbox]
    [girouette.tw.grid :as grid]
    [girouette.tw.box-alignment :as box-alignment]
    [girouette.tw.spacing :as spacing]
    [girouette.tw.sizing :as sizing]
    [girouette.tw.typography :as typography]
    [girouette.tw.background :as background]
    [girouette.tw.effect :as effect]
    [girouette.tw.transform :as transform]
    [girouette.tw.accessibility :as accessibility]))

(def default-components
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
     effect/components
     transform/components
     accessibility/components]))


;; This is how to build the API using the default components.
;; For a customized experience of Girouette, create your own API in the same way, using altered components.
(let [{:keys [parser class-name->garden]} (make-api default-components)]
  (def parser parser)
  (def class-name->garden class-name->garden))
