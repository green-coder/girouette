(ns girouette.tw.core
  (:require
    [clojure.string :as str]
    [instaparse.core :as insta]
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
    [girouette.tw.background :as background]))


(defn- assemble-grammar [components]
  (let [root-rule (str "css-class = prefixes ("
                       (->> components
                            (map (comp name :id))
                            (str/join " | "))
                       ")\n")]
    (->> (concat
           [root-rule]
           (map :rules components)
           [common/common-rules color/color-rules])
         (apply str))))


(defn- parsed-data->props [class-name parsed-data]
  (let [[_
         [_ & prefixes]
         [component-id & component-data]] parsed-data
        grouped-prefixes (util/group-by first second prefixes)]
    {:class-name class-name
     :prefixes {:media-query-min-width (first (:media-query-min-width grouped-prefixes))
                :media-query-color-scheme (first (:media-query-color-scheme grouped-prefixes))
                :media-query-reduced-motion (first (:media-query-reduced-motion grouped-prefixes))
                :state-variants (:state-variant grouped-prefixes)}
     :component-id component-id
     :component-data (vec component-data)}))


(defn- pipeline->transform [pipeline]
  (fn [rule props]
    (reduce (fn [rule f] (f rule props))
            rule
            (->> pipeline
                 ((juxt :media-queries
                        :outer-state-variants
                        :class-name
                        :inner-state-variants))
                 (apply concat)
                 reverse))))


(defn make-api [components]
  (let [grammar (assemble-grammar components)
        parser (insta/parser grammar)
        component-by-id (into {}
                              (map (juxt :id identity))
                              components)
        class-name->garden (fn [class-name]
                             (let [parsed-data (insta/parse parser class-name)]
                               (when-not (insta/failure? parsed-data)
                                 (let [props (parsed-data->props class-name parsed-data)
                                       component (component-by-id (:component-id props))
                                       garden-fn (:garden component)
                                       pipeline (:pipeline component common/default-pipeline)
                                       transform (pipeline->transform pipeline)]
                                   (-> (garden-fn props)
                                       (transform props))))))]
    {:grammar            grammar
     :parser             parser
     :component-by-id    component-by-id
     :class-name->garden class-name->garden}))


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
     background/components]))


;; This is how to build the API using the default components.
;; For a customized experience of Girouette, create your own API in the same way, using altered components.
(let [{:keys [parser class-name->garden]} (make-api default-components)]
  (def parser parser)
  (def class-name->garden class-name->garden))
