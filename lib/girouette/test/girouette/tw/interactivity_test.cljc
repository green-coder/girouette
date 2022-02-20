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

    "accent-auto"
    [".accent-auto" {:accent-color "auto"}]

    "appearance-none"
    [".appearance-none" {:appearance "none"}]

    "cursor-help"
    [".cursor-help" {:cursor "help"}]

    "cursor-wait"
    [".cursor-wait" {:cursor "wait"}]

    "pointer-events-none"
    [".pointer-events-none" {:pointer-events "none"}]

    "resize"
    [".resize" {:resize "both"}]

    "resize-x"
    [".resize-x" {:resize "horizontal"}]

    "scroll-auto"
    [".scroll-auto" {:scroll-behavior "auto"}]

    "scroll-smooth"
    [".scroll-smooth" {:scroll-behavior "smooth"}]

    ;; Scroll margin
    "scroll-m-8"
    [".scroll-m-8" {:scroll-margin "2rem"}]

    "scroll-mt-8"
    [".scroll-mt-8" {:scroll-margin-top "2rem"}]

    "scroll-ml-0"
    [".scroll-ml-0" {:scroll-margin-left "0px"}]

    "scroll-my-11vh"
    [".scroll-my-11vh" {:scroll-margin-top "11vh"
                        :scroll-margin-bottom "11vh"}]

    "scroll-mx-8"
    [".scroll-mx-8" {:scroll-margin-left "2rem"
                     :scroll-margin-right "2rem"}]

    ;; Scroll padding
    "scroll-p-8"
    [".scroll-p-8" {:scroll-padding "2rem"}]

    "scroll-p-10%"
    [".scroll-p-10\\%" {:scroll-padding "10%"}]

    "scroll-p-1/2"
    [".scroll-p-1\\/2" {:scroll-padding "50%"}]

    "scroll-pt-8"
    [".scroll-pt-8" {:scroll-padding-top "2rem"}]

    "scroll-px-8"
    [".scroll-px-8" {:scroll-padding-left "2rem"
                     :scroll-padding-right "2rem"}]

    ;; Scroll snap align
    "snap-start"
    [".snap-start" {:scroll-snap-align "start"}]

    "snap-align-none"
    [".snap-align-none" {:scroll-snap-align "none"}]

    ;; Scroll snap stop
    "snap-normal"
    [".snap-normal" {:scroll-snap-stop "normal"}]

    ;; Scroll snap type
    "snap-x"
    [".snap-x" {:scroll-snap-type "x var(--gi-scroll-snap-strictness)"}]

    "snap-mandatory"
    [".snap-mandatory" {:--gi-scroll-snap-strictness "mandatory"}]

    ;; Touch action
    "touch-auto"
    [".touch-auto" {:touch-action "auto"}]

    "touch-manipulation"
    [".touch-manipulation" {:touch-action "manipulation"}]

    ;; User select
    "select-all"
    [".select-all" {:user-select "all"}]

    ;; Will change
    "will-change-auto"
    [".will-change-auto" {:will-change "auto"}]

    "will-change-scroll"
    [".will-change-scroll" {:will-change "scroll-position"}]))
