(ns ^:no-doc girouette.grammar.hiccup-tag
  (:require [instaparse.core :as insta]))

(def hiccup-tag-grammar "
  hiccup-tag = html-tag (<'#'> id | (<'.'> class-name))*
  html-tag = segment
  id = segment
  class-name = segment
  <segment> = #'[^\\.#]+'
  ")

(def hiccup-tag-parser
  (insta/parser hiccup-tag-grammar))
