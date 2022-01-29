(ns girouette.tw.interactivity-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "accent-current"
    [".accent-current" {:accent-color "currentColor"}]

    "accent-#abcdef11"
    [".accent-\\#abcdef11" {:accent-color "#abcdef11"}]

    "appearance-none"
    [".appearance-none" {:appearance "none"}]

    "cursor-help"
    [".cursor-help" {:cursor "help"}]

    "cursor-wait"
    [".cursor-wait" {:cursor "wait"}]

    "outline-none"
    [".outline-none" {:outline "2px solid transparent"
                      :outline-offset "2px"}]

    "pointer-events-none"
    [".pointer-events-none" {:pointer-events "none"}]

    "resize"
    [".resize" {:resize "both"}]

    "resize-x"
    [".resize-x" {:resize "horizontal"}]

    "select-all"
    [".select-all" {:user-select "all"}]

    "scroll-auto"
    [".scroll-auto" {:scroll-behavior "auto"}]

    "scroll-smooth"
    [".scroll-smooth" {:scroll-behavior "smooth"}]

    "will-change-auto"
    [".will-change-auto" {:will-change "auto"}]

    "will-change-scroll"
    [".will-change-scroll" {:will-change "scroll-position"}]))
