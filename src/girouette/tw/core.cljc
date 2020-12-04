(ns girouette.tw.core
  (:require
    [clojure.string :as str]
    [instaparse.core :as insta]
    [girouette.util :as util]
    [girouette.tw.common :as common]
    [girouette.tw.layout :as layout]))

(def components
  (into []
        cat
        [common/components
         layout/components]))

(def components-by-id
  (into {}
        (map (juxt :id identity))
        components))

(defn- assemble-grammar [components]
  (let [root-rule (str "css-class = prefixes ("
                       (->> components
                            (map (comp name :id))
                            (str/join " | "))
                       ")\n")]
    (->> (concat
           [root-rule]
           (map :rules components)
           [common/common-rules])
         (apply str))))

(def grammar (assemble-grammar components))
(def parser (insta/parser grammar))

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

(defn class-name->garden [class-name]
  (let [parsed-data (insta/parse parser class-name)]
    (when-not (insta/failure? parsed-data)
      (let [props (parsed-data->props class-name parsed-data)
            component (components-by-id (:component-id props))
            garden-fn (:garden component)
            transform (:transform component common/default-transform)]
        (-> (garden-fn props)
            (transform props))))))
