(ns girouette.tw.core
  (:require
    [clojure.string :as str]
    [clojure.set :as set]
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
         [media-query-orientation] :media-query-orientation
         state-variants :state-variant} (util/group-by first second prefixes)]
    (assoc predef-props
      :class-name class-name
      :prefixes {:media-query-min-width media-query-min-width
                 :media-query-color-scheme media-query-color-scheme
                 :media-query-reduced-motion media-query-reduced-motion
                 :media-query-orientation media-query-orientation
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


(defn- complement-before-rules-after-rules
  "Ensures that each :before-rules relation has a :after-rules relation, and vice-versa."
  [id->component]
  (reduce (fn [id->component [id {:keys [before-rules after-rules]}]]
            (let [;; Ensure the presence of all :after-rules from :before-rules
                  id->component (reduce (fn [id->component before-id]
                                          (update-in id->component [before-id :after-rules]
                                                     (fnil conj #{}) id))
                                        id->component
                                        before-rules)
                  ;; Ensure the presence of all :before-rules from :after-rules
                  id->component (reduce (fn [id->component after-id]
                                          (update-in id->component [after-id :before-rules]
                                                     (fnil conj #{}) id))
                                        id->component
                                        after-rules)]
              id->component))
          id->component
          id->component))


(defn- assoc-ordering-level
  "Sorts the components typologically and assign them an ordering level in the graph."
  [id->component]
  (loop [id->component id->component
         visited-ids   #{}
         level         0
         candidates-ids (keys id->component)]
    (if (empty? candidates-ids)
      id->component
      (let [root-ids (into #{}
                           (filter (fn [id]
                                     (empty? (set/difference (get-in id->component [id :after-rules])
                                                             visited-ids))))
                           candidates-ids)
            id->component (reduce (fn [id->component id]
                                    (assoc-in id->component [id :ordering-level] level))
                                  id->component
                                  root-ids)
            visited-ids (into visited-ids root-ids)
            level (inc level)
            candidates-ids (into #{}
                                 (mapcat (fn [id]
                                           (get-in id->component [id :before-rules])))
                                 root-ids)]
        (recur id->component visited-ids level candidates-ids)))))


(defn make-api
  "Creates an API based on a collection of Girouette components."
  [components {:keys [color-map
                      font-family-map
                      unitless-length-conversion]
               :or {unitless-length-conversion {:unit "rem"
                                                :value-fn common/div-4}}}]
  (let [components (util/into-one-vector components) ;; flatten the structure
        flattened-color-map (color/flatten-color-map color-map)
        grammar (assemble-grammar components {:color-map flattened-color-map
                                              :font-family-map font-family-map})
        parser (insta/parser grammar)
        component-by-id (-> (into {}
                                  (map (juxt :id identity))
                                  components)
                            complement-before-rules-after-rules
                            assoc-ordering-level)
        predef-props {:read-color (partial color/read-color flattened-color-map)
                      :font-family-map font-family-map
                      :unitless-length-conversion unitless-length-conversion}
        class-name->garden (fn [class-name]
                             (let [parsed-data (insta/parse parser class-name)]
                               (when-not (insta/failure? parsed-data)
                                 (let [props (parsed-data->props class-name parsed-data predef-props)
                                       component (component-by-id (:component-id props))
                                       garden-fn (:garden component)
                                       pipeline (:pipeline component common/default-pipeline)
                                       transform (pipeline->transform pipeline)]
                                   (-> (garden-fn props)
                                       (transform props)
                                       (with-meta {:girouette/props props
                                                   :girouette/component component
                                                   :girouette/component-by-id component-by-id}))))))]
    {:grammar            grammar
     :parser             parser
     :component-by-id    component-by-id
     :class-name->garden class-name->garden}))
