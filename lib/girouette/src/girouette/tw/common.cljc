(ns ^:no-doc girouette.tw.common
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [garden.selectors]
            [garden.stylesheet :as gs]))

;; This Instaparse grammar matches nothing.
;; It literally means "look ahead to see 'nop', then see 'no-way'".
(def matches-nothing "&'nop' 'no-way'")

(defn- state-variant->str [state-variant]
  (cond
    (string? state-variant)
    (str ":"
         ({"first" "first-child"
           "last"  "last-child"
           "odd"   "nth-child(odd)"
           "even"  "nth-child(even)"} state-variant state-variant))

    (and (coll? state-variant)
         (= :attribute-state-variant (first state-variant)))
    (str "[" (second state-variant) "]")))


(defn- target-variant->str [target-variant]
  (str "::" ({"file" "file-selector-button"} target-variant target-variant)))


(defn- outer-state-variants
  [variant]
  (and (coll? variant)
       (#{:group-state-variant :peer-state-variant} (first variant))))

(defn dot [class-name]
  (str "." (str/replace class-name #"[^-a-zA-Z0-9_]"
                        (fn [c] (str "\\" c)))))


(def breakpoint->pixels
  {"sm"  "640px"
   "md"  "768px"
   "lg"  "1024px"
   "xl"  "1280px"
   "2xl" "1536px"})


(defn div-4   [x] (/ x 4))
(defn div-100 [x] (/ x 100))
(defn mul-100 [x] (* x 100))
(defn mul-255 [x] (* x 255))
(defn clamp-0-255 [x] (-> x (max 0) (min 255)))
(defn ratio-str [[x y]] (str x " / " y))


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
  "Convert numeric value to a double, or an int if the value can be converted without a loss.
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
     :fit-content "fit-content"
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
                          :ratio [[(read-number arg1) (read-number arg2)] nil]
                          :full-100% [100 "%"]
                          :screen-100vw [100 "vw"]
                          :screen-100vh [100 "vh"])
           value-fn (get-in options [data-type :value-fn] (:value-fn options identity))
           value (value-fn value)
           unit (get-in options [data-type :unit] (:unit options unit))
           zero-unit (get-in options [data-type :zero-unit] (:zero-unit options unit))
           [value unit] (if (and (number? value)
                                 (zero? value))
                          [0 zero-unit]
                          [value unit])
           value (cond-> value (= signus "-") (* -1))]
       (cond-> value
         (number? value) number->double-or-int
         (some? unit) (str unit))))))


(defn inner-state-variants-transform [rule props]
  (reduce (fn [rule [variant-type variant-value]]
            [(keyword (str "&"
                           (case variant-type
                             :target-variant
                             (target-variant->str variant-value)

                             (:plain-state-variant :attribute-state-variant)
                             (state-variant->str variant-value))))
             rule])
          rule
          (->> props :prefixes :state-variants reverse
               (remove outer-state-variants))))


(defn class-name-transform [rule props]
  [(dot (:class-name props)) rule])


(def between-children-selector
  "Selects every direct child of an element except the last.
   Commonly used to visually style 'between' a list of elements.

  For example:
  .space-y-2 uses this selector to add space between elements"
  #garden.selectors.CSSSelector{:selector "&>:not([hidden])~:not([hidden])"})


(defn outer-state-variants-transform [rule props]
  (reduce (fn [rule state-variant]
            (case (first state-variant)
              :group-state-variant
              [(str ".group" (state-variant->str (second state-variant)))
               rule]

              :peer-state-variant
              (into [(:selector
                      (garden.selectors/-
                        (str ".peer" (state-variant->str (second state-variant)))
                        (first rule)))]
                    (rest rule))))
          rule
          (->> props :prefixes :state-variants reverse
               (filter outer-state-variants))))


(defn media-queries-transform [rule props]
  (let [prefixes (:prefixes props)
        min-width (-> prefixes :media-query-min-width breakpoint->pixels)
        color-scheme (-> prefixes :media-query-color-scheme)
        reduced-motion (-> prefixes :media-query-reduced-motion {"motion-safe" "no-preference"
                                                                 "motion-reduce" "reduced"})
        orientation (-> prefixes :media-query-orientation)
        media-query (cond-> {}
                      min-width (assoc :min-width min-width)
                      color-scheme (assoc :prefers-color-scheme color-scheme)
                      reduced-motion (assoc :prefers-reduced-motion reduced-motion)
                      orientation (assoc :orientation orientation))]
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
                  media-query-reduced-motion |
                  media-query-orientation
  media-query-min-width = 'sm' | 'md' | 'lg' | 'xl' | '2xl'
  media-query-color-scheme = 'light' | 'dark'
  media-query-reduced-motion = 'motion-safe' | 'motion-reduce'
  media-query-orientation = 'landscape' | 'portrait'

  attribute-state-variant = 'open'
  <state-variant-value> = 'hover' | 'focus' | 'disabled' | 'active' |
                  'focus-within' | 'focus-visible' |
                  'any-link' | 'link' | 'visited' | 'target' |
                  'blank' | 'required' | 'optional' | 'valid' | 'invalid' | 'placeholder-shown' | 'checked' |
                  'read-only' | 'read-write' |
                  'first' | 'last' | 'odd' | 'even' | 'first-of-type' | 'last-of-type' |
                  'root' | 'empty' |
                  attribute-state-variant
  group-state-variant = <'group-'> state-variant-value
  peer-state-variant = <'peer-'> state-variant-value
  plain-state-variant = state-variant-value
  target-variant = 'file' | 'before' | 'after' | 'placeholder'
  state-variant = group-state-variant | peer-state-variant | plain-state-variant | target-variant

  signus = '-' | '+'
  direction = 't' | 'r' | 'b' | 'l'
  axis = 'x' | 'y'

  fraction = integer <'/'> integer
  ratio = integer <'/'> integer

  full-100% = 'full'
  screen-100vw = 'screen'
  screen-100vh = 'screen'
  min-content = 'min'
  max-content = 'max'
  fit-content = 'fit'
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
  [{:id :arbitrary-property
    :since-version [:tw 3]
    :rules "
    arbitrary-property = <'['> #'[^\\] ]*' <']'>
    "
    :garden (fn [{[value] :component-data}]
              (let [[prop val] (-> value
                                   ;; negative-lookbehind isn't supported in javascript
                                   (str/replace #"(^|[^\\])_" "$1 ")
                                   (str/replace #"\\_" "_")
                                   (str/split #":" 2))]
                {(keyword prop) val}))}])
