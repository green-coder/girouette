(ns ^:no-doc girouette.tw.background
  (:require [clojure.string :as str]
            [girouette.tw.common :refer [value-unit->css div-100 div-4 mul-100]]
            [girouette.tw.color :refer [as-transparent color->css]]))

(def components
  [{:id :background-attachment
    :since-version [:tw 2]
    :rules "
    background-attachment = <'bg-'> ('fixed' | 'local' | 'scroll')
    "
    :garden (fn [{[attachment-type] :component-data}]
              {:background-attachment attachment-type})}


   {:id :background-clip
    :since-version [:tw 2]
    :rules "
    background-clip = <'bg-clip-'> ('border' | 'padding' | 'content' | 'text')
    "
    :garden (fn [{[clip-type] :component-data}]
              {:background-clip ({"border" "border-box"
                                  "padding" "padding-box"
                                  "content" "content-box"
                                  "text" "text"} clip-type)})}


   {:id :background-color
    :since-version [:tw 2]
    :rules "
    background-color = <'bg-'> color
    "
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              (let [color (read-color color)]
                (if (string? color)
                  {:background-color color}
                  (let [[r g b a] color]
                    (if (some? a)
                      {:background-color (color->css color)}
                      {:--gi-bg-opacity 1
                       :background-color (color->css [r g b "var(--gi-bg-opacity)"])})))))
    :before-rules #{:background-opacity}}


   {:id :background-opacity
    :since-version [:tw 2]
    :rules "
    background-opacity = <'bg-opacity-'> number
    "
    :garden (fn [{[value] :component-data}]
              {:--gi-bg-opacity (value-unit->css value {:value-fn div-100})})}


   {:id :background-origin
    :since-version [:tw 3]
    :rules "
    background-origin = <'bg-origin-'> ('border' | 'padding' | 'content')
    "
    :garden (fn [{[value] :component-data}]
              {:background-origin (str value "-box")})}


   {:id :background-position
    :since-version [:tw 2]
    :rules "
    background-position = <'bg-'> ('top' | 'center' | 'bottom' |
                                   'left-top' | 'left' | 'left-bottom' |
                                   'right-top' | 'right' | 'right-bottom')
    "
    :garden (fn [{[position] :component-data}]
              {:background-position (str/escape position {\- \space})})}


   {:id :background-repeat
    :since-version [:tw 2]
    :rules "
    background-repeat = <'bg-'> ('repeat' | 'no-repeat' | 'repeat-x' | 'repeat-y' |
                                 <'repeat-'> 'round' | <'repeat-'> 'space')
    "
    :garden (fn [{[repeat-type] :component-data}]
              {:background-repeat repeat-type})}


   {:id :background-size
    :since-version [:tw 2]
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
    :since-version [:tw 2]
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
    :since-version [:tw 2]
    :rules "
    gradient-color-from = <'from-'> color
    "
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              (let [color (read-color color)
                    transp-color (as-transparent color)]
                {:--gi-gradient-from (color->css color)
                 :--gi-gradient-stops (str "var(--gi-gradient-from),"
                                           "var(--gi-gradient-to," (color->css transp-color) ")")}))
    :before-rules #{:gradient-color-via}}


   {:id :gradient-color-to
    :since-version [:tw 2]
    :rules "
    gradient-color-to = <'to-'> color
    "
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              {:--gi-gradient-to (color->css (read-color color))})}


   {:id :gradient-color-via
    :since-version [:tw 2]
    :rules "
    gradient-color-via = <'via-'> color
    "
    :garden (fn [{[color] :component-data
                  read-color :read-color}]
              (let [color (read-color color)
                    transp-color (as-transparent color)]
                {:--gi-gradient-stops (str "var(--gi-gradient-from),"
                                           (color->css color) ","
                                           "var(--gi-gradient-to," (color->css transp-color) ")")}))}])
