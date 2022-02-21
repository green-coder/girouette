(ns girouette.tw.color
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [girouette.tw.common :refer [matches-nothing value-unit->css div-100 mul-255 clamp-0-255]]
            [garden.color :as gc]))

;; Those color values and names are from Tailwind CSS v2.
;; They are imported here for the purpose of compatibility.
(def ^:no-doc default-color-map
  {"white" "ffffff"
   "black" "000000"

   "rose-50" "fff1f2"
   "rose-100" "ffe4e6"
   "rose-200" "fecdd3"
   "rose-300" "fda4af"
   "rose-400" "fb7185"
   "rose-500" "f43f5e"
   "rose-600" "e11d48"
   "rose-700" "be123c"
   "rose-800" "9f1239"
   "rose-900" "881337"

   "pink-50" "fdf2f8"
   "pink-100" "fce7f3"
   "pink-200" "fbcfe8"
   "pink-300" "f9a8d4"
   "pink-400" "f472b6"
   "pink-500" "ec4899"
   "pink-600" "db2777"
   "pink-700" "be185d"
   "pink-800" "9d174d"
   "pink-900" "831843"

   "fuchsia-50" "fdf4ff"
   "fuchsia-100" "fae8ff"
   "fuchsia-200" "f5d0fe"
   "fuchsia-300" "f0abfc"
   "fuchsia-400" "e879f9"
   "fuchsia-500" "d946ef"
   "fuchsia-600" "c026d3"
   "fuchsia-700" "a21caf"
   "fuchsia-800" "86198f"
   "fuchsia-900" "701a75"

   "purple-50" "faf5ff"
   "purple-100" "f3e8ff"
   "purple-200" "e9d5ff"
   "purple-300" "d8b4fe"
   "purple-400" "c084fc"
   "purple-500" "a855f7"
   "purple-600" "9333ea"
   "purple-700" "7e22ce"
   "purple-800" "6b21a8"
   "purple-900" "581c87"

   "violet-50" "f5f3ff"
   "violet-100" "ede9fe"
   "violet-200" "ddd6fe"
   "violet-300" "c4b5fd"
   "violet-400" "a78bfa"
   "violet-500" "8b5cf6"
   "violet-600" "7c3aed"
   "violet-700" "6d28d9"
   "violet-800" "5b21b6"
   "violet-900" "4c1d95"

   "indigo-50" "eef2ff"
   "indigo-100" "e0e7ff"
   "indigo-200" "c7d2fe"
   "indigo-300" "a5b4fc"
   "indigo-400" "818cf8"
   "indigo-500" "6366f1"
   "indigo-600" "4f46e5"
   "indigo-700" "4338ca"
   "indigo-800" "3730a3"
   "indigo-900" "312e81"

   "blue-50" "eff6ff"
   "blue-100" "dbeafe"
   "blue-200" "bfdbfe"
   "blue-300" "93c5fd"
   "blue-400" "60a5fa"
   "blue-500" "3b82f6"
   "blue-600" "2563eb"
   "blue-700" "1d4ed8"
   "blue-800" "1e40af"
   "blue-900" "1e3a8a"

   "lightBlue-50" "f0f9ff"
   "lightBlue-100" "e0f2fe"
   "lightBlue-200" "bae6fd"
   "lightBlue-300" "7dd3fc"
   "lightBlue-400" "38bdf8"
   "lightBlue-500" "0ea5e9"
   "lightBlue-600" "0284c7"
   "lightBlue-700" "0369a1"
   "lightBlue-800" "075985"
   "lightBlue-900" "0c4a6e"

   "sky-50" "f0f9ff"
   "sky-100" "e0f2fe"
   "sky-200" "bae6fd"
   "sky-300" "7dd3fc"
   "sky-400" "38bdf8"
   "sky-500" "0ea5e9"
   "sky-600" "0284c7"
   "sky-700" "0369a1"
   "sky-800" "075985"
   "sky-900" "0c4a6e"

   "cyan-50" "ecfeff"
   "cyan-100" "cffafe"
   "cyan-200" "a5f3fc"
   "cyan-300" "67e8f9"
   "cyan-400" "22d3ee"
   "cyan-500" "06b6d4"
   "cyan-600" "0891b2"
   "cyan-700" "0e7490"
   "cyan-800" "155e75"
   "cyan-900" "164e63"

   "teal-50" "f0fdfa"
   "teal-100" "ccfbf1"
   "teal-200" "99f6e4"
   "teal-300" "5eead4"
   "teal-400" "2dd4bf"
   "teal-500" "14b8a6"
   "teal-600" "0d9488"
   "teal-700" "0f766e"
   "teal-800" "115e59"
   "teal-900" "134e4a"

   "emerald-50" "ecfdf5"
   "emerald-100" "d1fae5"
   "emerald-200" "a7f3d0"
   "emerald-300" "6ee7b7"
   "emerald-400" "34d399"
   "emerald-500" "10b981"
   "emerald-600" "059669"
   "emerald-700" "047857"
   "emerald-800" "065f46"
   "emerald-900" "064e3b"

   "green-50" "f0fdf4"
   "green-100" "dcfce7"
   "green-200" "bbf7d0"
   "green-300" "86efac"
   "green-400" "4ade80"
   "green-500" "22c55e"
   "green-600" "16a34a"
   "green-700" "15803d"
   "green-800" "166534"
   "green-900" "14532d"

   "lime-50" "f7fee7"
   "lime-100" "ecfccb"
   "lime-200" "d9f99d"
   "lime-300" "bef264"
   "lime-400" "a3e635"
   "lime-500" "84cc16"
   "lime-600" "65a30d"
   "lime-700" "4d7c0f"
   "lime-800" "3f6212"
   "lime-900" "365314"

   "yellow-50" "fefce8"
   "yellow-100" "fef9c3"
   "yellow-200" "fef08a"
   "yellow-300" "fde047"
   "yellow-400" "facc15"
   "yellow-500" "eab308"
   "yellow-600" "ca8a04"
   "yellow-700" "a16207"
   "yellow-800" "854d0e"
   "yellow-900" "713f12"

   "amber-50" "fffbeb"
   "amber-100" "fef3c7"
   "amber-200" "fde68a"
   "amber-300" "fcd34d"
   "amber-400" "fbbf24"
   "amber-500" "f59e0b"
   "amber-600" "d97706"
   "amber-700" "b45309"
   "amber-800" "92400e"
   "amber-900" "78350f"

   "orange-50" "fff7ed"
   "orange-100" "ffedd5"
   "orange-200" "fed7aa"
   "orange-300" "fdba74"
   "orange-400" "fb923c"
   "orange-500" "f97316"
   "orange-600" "ea580c"
   "orange-700" "c2410c"
   "orange-800" "9a3412"
   "orange-900" "7c2d12"

   "red-50" "fef2f2"
   "red-100" "fee2e2"
   "red-200" "fecaca"
   "red-300" "fca5a5"
   "red-400" "f87171"
   "red-500" "ef4444"
   "red-600" "dc2626"
   "red-700" "b91c1c"
   "red-800" "991b1b"
   "red-900" "7f1d1d"

   "warmGray-50" "fafaf9"
   "warmGray-100" "f5f5f4"
   "warmGray-200" "e7e5e4"
   "warmGray-300" "d6d3d1"
   "warmGray-400" "a8a29e"
   "warmGray-500" "78716c"
   "warmGray-600" "57534e"
   "warmGray-700" "44403c"
   "warmGray-800" "292524"
   "warmGray-900" "1c1917"

   "trueGray-50" "fafafa"
   "trueGray-100" "f5f5f5"
   "trueGray-200" "e5e5e5"
   "trueGray-300" "d4d4d4"
   "trueGray-400" "a3a3a3"
   "trueGray-500" "737373"
   "trueGray-600" "525252"
   "trueGray-700" "404040"
   "trueGray-800" "262626"
   "trueGray-900" "171717"

   "gray-50" "fafafa"
   "gray-100" "f4f4f5"
   "gray-200" "e4e4e7"
   "gray-300" "d4d4d8"
   "gray-400" "a1a1aa"
   "gray-500" "71717a"
   "gray-600" "52525b"
   "gray-700" "3f3f46"
   "gray-800" "27272a"
   "gray-900" "18181b"

   "coolGray-50" "f9fafb"
   "coolGray-100" "f3f4f6"
   "coolGray-200" "e5e7eb"
   "coolGray-300" "d1d5db"
   "coolGray-400" "9ca3af"
   "coolGray-500" "6b7280"
   "coolGray-600" "4b5563"
   "coolGray-700" "374151"
   "coolGray-800" "1f2937"
   "coolGray-900" "111827"

   "blueGray-50" "f8fafc"
   "blueGray-100" "f1f5f9"
   "blueGray-200" "e2e8f0"
   "blueGray-300" "cbd5e1"
   "blueGray-400" "94a3b8"
   "blueGray-500" "64748b"
   "blueGray-600" "475569"
   "blueGray-700" "334155"
   "blueGray-800" "1e293b"
   "blueGray-900" "0f172a"})


(defn color-rules [color-map]
  (let [color-names (if (seq color-map)
                      (->> (keys color-map)
                           (map (fn [color] (str "'" color "'")))
                           (str/join " | "))
                      matches-nothing)]
    (str "
  color = color-rgb | color-rgba |
          color-hsl | color-hsla |
          special-color |
          predefined-color-opacity

  color-rgb = (<'#'> | <'rgb-'>) (#'[0-9a-f]{3}' | #'[0-9a-f]{6}')
  color-rgba = (<'#'> | <'rgba-'>) (#'[0-9a-f]{4}' | #'[0-9a-f]{8}')

  color-hsl = <'hsl-'> color-hue <'-'> color-saturation <'-'> color-lightness
  color-hsla = <'hsla-'> color-hue <'-'> color-saturation <'-'> color-lightness <'-'> color-opacity
  <color-hue> = number
  <color-saturation> = number
  <color-lightness> = number
  <color-opacity> = number

  special-color = 'transparent' | 'current'
  predefined-color-opacity = predefined-color (<'-' | '/'> color-opacity)?

  <predefined-color> = " color-names "
")))

(def ^:private zero-to-f "0123456789abcdef")

(defn- read-hex-value [hex-value]
  (edn/read-string (str "0x" hex-value)))

(defn- byte->hex [byte]
  (when byte
    (str (nth zero-to-f (bit-and (bit-shift-right byte 4) 0xf))
         (nth zero-to-f (bit-and byte 0xf)))))

(defn read-color
  "Returns a 4-elements vector containing the color components, or a string.
   The nil value is returned if a particular component is not specified in the input.

   `color-map` is a function which takes a string and returns a string in the hexadecimal RRGGBB format.
   `color-map` is typically a hashmap.

   `color` is the data structure from the parser which matched a color."
  [color-map color]
  (let [[_ [type param1 param2 param3 param4]] color]
    (case type
      :color-rgb (case (count param1)
                   3 [(read-hex-value (str (nth param1 0) (nth param1 0)))
                      (read-hex-value (str (nth param1 1) (nth param1 1)))
                      (read-hex-value (str (nth param1 2) (nth param1 2)))
                      nil]
                   6 [(read-hex-value (subs param1 0 2))
                      (read-hex-value (subs param1 2 4))
                      (read-hex-value (subs param1 4 6))
                      nil])

      :color-rgba (case (count param1)
                    4 [(read-hex-value (str (nth param1 0) (nth param1 0)))
                       (read-hex-value (str (nth param1 1) (nth param1 1)))
                       (read-hex-value (str (nth param1 2) (nth param1 2)))
                       (read-hex-value (str (nth param1 3) (nth param1 3)))]
                    8 [(read-hex-value (subs param1 0 2))
                       (read-hex-value (subs param1 2 4))
                       (read-hex-value (subs param1 4 6))
                       (read-hex-value (subs param1 6 8))])

      (:color-hsl :color-hsla) (let [{:keys [red green blue]} (gc/hsl->rgb {:hue (value-unit->css param1)
                                                                            :saturation (value-unit->css param2)
                                                                            :lightness (value-unit->css param3)})
                                     alpha (when (some? param4)
                                             (value-unit->css param4 {:value-fn (comp clamp-0-255 int div-100 mul-255)}))]
                                 [red green blue alpha])

      :special-color ({"transparent" "transparent"
                       "current" "currentColor"} param1)

      :predefined-color-opacity
      (let [color-code (color-map param1) ;; color-name -> color-code
            r (read-hex-value (subs color-code 0 2))
            b (read-hex-value (subs color-code 2 4))
            g (read-hex-value (subs color-code 4 6))
            a (when (some? param2)
                (value-unit->css param2 {:value-fn (comp clamp-0-255 int div-100 mul-255)}))]
          [r b g a]))))

(defn as-transparent
  "Transforms a color into a transparent one when possible."
  [color]
  (if (string? color)
    color
    (let [[r g b _] color]
      [r g b 0])))

(defn color->css
  "Transform a color structure into a css string."
  [color]
  (if (string? color)
    color
    (let [[r g b a] color]
      (if (string? a)
        (str "rgba("
              r ","
              g ","
              b ","
              a ")")
        (str "#"
             (byte->hex r)
             (byte->hex g)
             (byte->hex b)
             (byte->hex a))))))
