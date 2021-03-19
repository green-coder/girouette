(ns girouette.tw.core
  (:require
    [clojure.string :as str]
    [instaparse.core :as insta]
    [girouette.util :as util]
    [girouette.tw.common :as common]
    [girouette.tw.color :as color]
    [girouette.tw.typography :as typography]))


(defn- assemble-grammar [components {:keys [color-map font-family-map]}]
  (let [root-rule (str "css-class = prefixes ("
                       (->> components
                            (map (comp name :id))
                            (str/join " | "))
                       ")\n")]
    (->> (concat
           [root-rule]
           (map :rules components)
           [common/common-rules
            (color/color-rules color-map)
            (typography/font-family-rules font-family-map)])
         (apply str))))


(defn- parsed-data->props [class-name parsed-data predef-props]
  (let [[_
         [_ & prefixes]
         [component-id & component-data]] parsed-data
        {[media-query-min-width] :media-query-min-width
         [media-query-color-scheme] :media-query-color-scheme
         [media-query-reduced-motion] :media-query-reduced-motion
         state-variants :state-variant} (util/group-by first second prefixes)]
    (assoc predef-props
      :class-name class-name
      :prefixes {:media-query-min-width media-query-min-width
                 :media-query-color-scheme media-query-color-scheme
                 :media-query-reduced-motion media-query-reduced-motion
                 :state-variants state-variants}
      :component-id component-id
      :component-data (vec component-data))))


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


(defn make-api
  "Creates an API based on a collection of Girouette components."
  [components {:keys [color-map font-family-map] :as options}]
  (let [grammar (assemble-grammar components options)
        parser (insta/parser grammar)
        component-by-id (into {}
                              (map (juxt :id identity))
                              components)
        predef-props {:read-color (partial color/read-color color-map)
                      :font-family-map font-family-map}
        class-name->garden (fn [class-name]
                             (let [parsed-data (insta/parse parser class-name)]
                               (when-not (insta/failure? parsed-data)
                                 (let [props (parsed-data->props class-name parsed-data predef-props)
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
