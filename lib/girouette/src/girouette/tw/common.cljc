(ns ^:no-doc girouette.tw.common
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [garden.stylesheet :as gs]))


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


(defn div-4   [x] (/ x 4))
(defn div-100 [x] (/ x 100))
(defn mul-100 [x] (* x 100))


(defn read-number
  "Converts the input into a number.
   Accepts the formats [:integer \"1\"], [:number \"1\"] or \"1\".
   This function might become private at some point, do not use if possible."
  [data]
  (let [number-str (cond-> data (vector? data) second)]
    (-> number-str
        (str/escape {\_ \.})
        edn/read-string)))


(defn number->double-or-int
  "Convert numeric value to a double, or a int if the value can be converted without a loss.
   Useful for getting rid of ratio numbers like 5/2."
  [value]
  (if (= (double (int value))
         (double value))
    (int value)
    (double value)))


(defn value-unit->css
  ([data]
   (value-unit->css data {}))
  ;; The options also contain :unit, :zero-unit and :value-fn, at the root and
  ;; can also contain an override per data-type, e.g. {:fraction {:unit ...}}
  ([data {:keys [signus] :as options}]
   (case (first data)
     :auto "auto"
     :none "none"
     :full "full"
     :min-content "min-content"
     :max-content "max-content"
     (let [[data-type arg1 arg2] data
           [value unit] (case data-type
                          :integer [(read-number arg1) nil]
                          :number [(read-number arg1) nil]
                          :length [(read-number arg1) arg2]
                          :length-unit [1 arg1]
                          :angle [(read-number arg1) arg2]
                          :time [(read-number arg1) arg2]
                          :percentage [(read-number arg1) "%"]
                          :fraction [(/ (read-number arg1) (read-number arg2)) nil]
                          :full-100% [100 "%"]
                          :screen-100vw [100 "vw"]
                          :screen-100vh [100 "vh"])
           value-fn (get-in options [data-type :value-fn] (:value-fn options identity))
           value (value-fn value)
           unit (get-in options [data-type :unit] (:unit options unit))
           zero-unit (get-in options [data-type :zero-unit] (:zero-unit options unit))
           [value unit] (if (zero? value)
                          [0 zero-unit]
                          [value unit])
           value (cond-> value (= signus "-") (* -1))]
       (cond-> (number->double-or-int value)
         (some? unit) (str unit))))))


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
        reduced-motion (-> prefixes :media-query-reduced-motion {"motion-safe" "no-preference"
                                                                 "motion-reduce" "reduced"})
        media-query (cond-> {}
                      min-width (assoc :min-width min-width)
                      color-scheme (assoc :prefers-color-scheme color-scheme)
                      reduced-motion (assoc :prefers-reduced-motion reduced-motion))]
    (if (seq media-query)
      (gs/at-media media-query rule)
      rule)))


(def default-pipeline
  {:media-queries [media-queries-transform]
   :outer-state-variants [outer-state-variants-transform]
   :class-name [class-name-transform]
   :inner-state-variants [inner-state-variants-transform]})


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

  fraction = integer <'/'> integer

  full-100% = 'full'
  screen-100vw = 'screen'
  screen-100vh = 'screen'
  min-content = 'min'
  max-content = 'max'
  auto = 'auto'
  none = 'none'
  full = 'full'

  (* source: https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units *)
  integer = #'\\d+'
  number = #'\\d+([._]\\d+)?'
  percentage = number <'%'>

  length = number (absolute-length-unit | relative-length-unit)
  length-unit = absolute-length-unit | relative-length-unit
  <absolute-length-unit> = 'cm' | 'mm' | 'in' | 'pc' | 'pt' | 'px'
  <relative-length-unit> = 'em' | 'ex' | 'ch' | 'rem' | 'lh' | 'vw' | 'vh' | 'vmin' | 'vmax'

  angle = number angle-unit
  <angle-unit> = 'deg' | 'grad' | 'rad' | 'turn'

  time = number time-unit
  <time-unit> = 's' | 'ms'

  resolution = number resolution-unit
  <resolution-unit> = 'dpi' | 'dpcm' | 'dppx' | 'x'

")

(def components
  [])
