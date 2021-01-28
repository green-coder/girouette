(ns girouette.tw.common
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


(defn read-number [s]
  (-> s
      (str/escape {\_ \.})
      edn/read-string))


(defn value->css [value]
  (if (= (double (int value))
         (double value))
    (int value)
    (double value)))


(defn value-unit->css [[data-type & data] {:as options
                                           :keys [signus]}]
  (case data-type
    :auto "auto"
    :none "none"
    :full "full"
    :min-content "min-content"
    :max-content "max-content"
    (let [[value unit] (case data-type
                         (:integer :number) [(read-number (first data)) (:number-unit options)]
                         :length [(read-number (second (first data))) (second data)]
                         :length-unit [1 (first data)]
                         :percentage [(read-number (second (first data))) "%"]
                         :fraction (let [[[_ numerator] [_ denominator]] data
                                         ratio (/ (read-number numerator) (read-number denominator))
                                         unit (:fraction-unit options)]
                                     [(cond-> ratio (= unit "%") (* 100)) unit])
                         :full-100% [100 "%"]
                         :screen-100vw [100 "vw"]
                         :screen-100vh [100 "vh"])
          [value unit] (cond
                         (zero? value) [0 (:zero-unit options)]
                         (= unit :quarter-rem) [(/ value 4) "rem"]
                         :else [value unit])
          value (cond-> value (= signus "-") (* -1))]
     (str (value->css value) unit))))


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

  <percentage-full> = percentage | full-100%

  (* source: https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units *)
  integer = #'\\d+'
  number = #'\\d+([._]\\d+)?'
  percentage = number <'%'>

  <dimension> = length | angle | time | resolution

  length = number (absolute-length-unit | relative-length-unit)
  length-unit = absolute-length-unit | relative-length-unit
  <absolute-length-unit> = 'cm' | 'mm' | 'in' | 'pc' | 'pt' | 'px'
  <relative-length-unit> = 'em' | 'ex' | 'ch' | 'rem' | 'lh' | 'vw' | 'vh' | 'vmin' | 'vmax'
  <length-percentage> = length | percentage

  angle = number angle-unit
  <angle-unit> = 'deg' | 'grad' | 'rad' | 'turn'

  time = number time-unit
  <time-unit> = 's' | 'ms'
  <time-percentage> = time | percentage

  resolution = number resolution-unit
  <resolution-unit> = 'dpi' | 'dpcm' | 'dppx' | 'x'

")

(def components
  [])
