(ns girouette.tw.common
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [garden.stylesheet :as gs]
            [girouette.util :as util]))


(defn dot [class]
  (str "." (str/escape class {\. "\\."
                              \: "\\:"
                              \/ "\\/"
                              \% "\\%"
                              \& "\\&"
                              \~ "\\~"
                              \< "\\<"
                              \> "\\>"})))


(def breakpoint->pixels
  {"sm"  "640px"
   "md"  "768px"
   "lg"  "1024px"
   "xl"  "1280px"
   "2xl" "1536px"})


(defn read-decimal [s]
  (-> s
      (str/escape {\_ \.})
      edn/read-string))


(defn size->css
  ([signus size-data]
   (if (= size-data ["auto"])
     "auto"
     (let [{:keys [size unit fraction]} (util/index-by first next size-data)
           [size unit] (case fraction
                         nil [(read-decimal (first size))
                              (first unit)]
                         ["full"] [100 "%"]
                         [(-> 100.0
                              (* (edn/read-string (first fraction)))
                              (/ (edn/read-string (second fraction)))) "%"])]
       (size->css signus size unit))))
  ([signus size unit]
   (let [[size unit] (cond
                       (some? unit) [(or size 1) unit]
                       (zero? size) [0 "px"]
                       :else [(* 0.25 size) "rem"])]
     (str (or signus "") size unit))))


(defn inner-state-variants-transform [rule props]
  (reduce (fn [rule state-variant]
            [(keyword (str "&:" state-variant)) rule])
          rule
          (->> props :prefixes :state-variants reverse
               (remove #{"group-hover" "group-focus"
                         "group-disabled" "group-active"}))))


(defn class-name-transform [rule props]
  [(dot (:class-name props)) rule])


(defn outer-state-variants-transform [rule props]
  (reduce (fn [rule state-variant]
            (case state-variant
              "group-hover" [".group:hover" rule]
              "group-focus" [".group:focus" rule]
              "group-disabled" [".group:disabled" rule]
              "group-active" [".group:active" rule]))
          rule
          (->> props :prefixes :state-variants reverse
               (filter #{"group-hover" "group-focus"
                         "group-disabled" "group-active"}))))


(defn media-queries-transform [rule props]
  (let [prefixes (:prefixes props)
        min-width (-> prefixes :media-query-min-width breakpoint->pixels)
        color-scheme (-> prefixes :media-query-color-scheme)
        reduced-motion (-> prefixes :media-query-reduced-motion
                           {"motion-safe" "no-preference"
                            "motion-reduce" "reduced"})
        media-query (cond-> {}
                      min-width (assoc :min-width min-width)
                      color-scheme (assoc :prefers-color-scheme color-scheme)
                      reduced-motion (assoc :prefers-reduced-motion reduced-motion))]
    (if (seq media-query)
      (gs/at-media media-query rule)
      rule)))

(defn default-transform [rule props]
  (-> rule
      (inner-state-variants-transform props)
      (class-name-transform props)
      (outer-state-variants-transform props)
      (media-queries-transform props)))


(def common-rules "
  prefixes = (media-query <':'>)* (state-variant <':'>)*

  <media-query> = media-query-min-width |
                  media-query-color-scheme |
                  media-query-reduced-motion
  media-query-min-width = 'sm' | 'md' | 'lg' | 'xl' | '2xl'
  media-query-color-scheme = 'light' | 'dark'
  media-query-reduced-motion = 'motion-safe' | 'motion-reduce'

  state-variant = 'hover' | 'focus' | 'disabled' | 'active' |
                  'group-hover' | 'group-focus' | 'group-disabled' | 'group-active' |
                  'focus-within' | 'focus-visible' |
                  'visited' | 'checked' |
                  'first' | 'last' | 'odd' | 'even'

  signus = '-' | '+'
  direction = 't' | 'r' | 'b' | 'l'
  axis = 'x' | 'y'
  unit = 'px' | 'em' | 'rem' | '%'
  size = float-number
  fraction = int-number <'/'> int-number | 'full'
  <int-number> = #'\\d+'
  <float-number> = #'\\d+([._]\\d+)?'
")

(def components
  [])
