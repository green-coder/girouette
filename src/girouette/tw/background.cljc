(ns girouette.tw.background
  (:require [clojure.string :as str]
            [girouette.tw.common :refer [value-unit->css div-100 div-4 mul-100]]
            [girouette.tw.color :refer [read-color as-transparent color->css]]))

(def components
  [{:id :background-attachment
    :rules "
    background-attachment = <'bg-'> ('fixed' | 'local' | 'scroll')
    "
    :garden (fn [{[attachment-type] :component-data}]
              {:background-attachment attachment-type})}


   {:id :background-clip
    :rules "
    background-clip = <'bg-clip-'> ('border' | 'padding' | 'content' | 'text')
    "
    :garden (fn [{[clip-type] :component-data}]
              {:background-clip ({"border" "border-box"
                                  "padding" "padding-box"
                                  "content" "content-box"
                                  "text" "text"} clip-type)})}


   {:id :background-color
    :rules "
    background-color = <'bg-'> color
    "
    :garden (fn [{[[_ color]] :component-data}]
              (let [color (read-color color)]
                (if (string? color)
                  {:background-color color}
                  (let [[r g b a] color]
                    (if (some? a)
                      {:background-color (color->css color)}
                      {:--gi-bg-opacity 1
                       :background-color (color->css [r g b "var(--gi-bg-opacity)"])})))))}


   {:id :background-opacity
    :rules "
    background-opacity = <'bg-opacity-'> integer
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-bg-opacity (value-unit->css value {:value-fn div-100})})}


   {:id :background-position
    :rules "
    background-position = <'bg-'> ('top' | 'center' | 'bottom' |
                                   'left-top' | 'left' | 'left-bottom' |
                                   'right-top' | 'right' | 'right-bottom')
    "
    :garden (fn [{[position] :component-data}]
              {:background-position (str/escape position {\- \space})})}


   {:id :background-repeat
    :rules "
    background-repeat = <'bg-'> ('repeat' | 'no-repeat' | 'repeat-x' | 'repeat-y' |
                                 <'repeat-'> 'round' | <'repeat-'> 'space')
    "
    :garden (fn [{[repeat-type] :component-data}]
              {:background-repeat repeat-type})}


   {:id :background-size
    :rules "
    background-size = <'bg-'> ('auto' | 'cover' | 'contain' |
                               <'size-'> background-size-length <'-'> background-size-length)
    <background-size-length> = auto | number | length | length-unit | fraction | percentage
    "
    :garden (fn [{data :component-data}]
              (if (= (count data) 1)
                {:background-size (first data)}
                (let [[x y] data
                      options {:zero-unit nil
                               :number {:unit "rem"
                                        :value-fn div-4}
                               :fraction {:unit "%"
                                          :value-fn mul-100}}]
                  {:background-size [[(value-unit->css x options)
                                      (value-unit->css y options)]]})))}


   {:id :background-image
    :rules "
    background-image = <'bg-'> 'none' |
                       <'bg-gradient-to-'> ('tr' | 't' | 'tl' | 'l' | 'r' | 'bl' | 'b' | 'br')
    "
    :garden (fn [{[data] :component-data}]
              {:background-image
               (if (= data "none")
                 "none"
                 (let [direction (->> data
                                      (map {\t "top"
                                            \b "bottom"
                                            \l "left"
                                            \r "right"})
                                      (str/join " "))]
                   (str "linear-gradient(to " direction ","
                        "var(--gi-gradient-stops))")))})}


   {:id :gradient-color-from
    :rules "
    gradient-color-from = <'from-'> color
    "
    :garden (fn [{[[_ color]] :component-data}]
              (let [color (read-color color)
                    transp-color (as-transparent color)]
                {:--gi-gradient-from (color->css color)
                 :--gi-gradient-stops (str "var(--gi-gradient-from),"
                                           "var(--gi-gradient-to," (color->css transp-color) ")")}))}


   {:id :gradient-color-to
    :rules "
    gradient-color-to = <'to-'> color
    "
    :garden (fn [{[[_ color]] :component-data}]
              {:--gi-gradient-to (color->css (read-color color))})}


   {:id :gradient-color-via
    :rules "
    gradient-color-via = <'via-'> color
    "
    :garden (fn [{[[_ color]] :component-data}]
              (let [color (read-color color)
                    transp-color (as-transparent color)]
                {:--gi-gradient-stops (str "var(--gi-gradient-from),"
                                           (color->css color) ","
                                           "var(--gi-gradient-to," (color->css transp-color) ")")}))}])
