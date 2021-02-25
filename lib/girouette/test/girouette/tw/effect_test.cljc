(ns girouette.tw.effect-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "shadow"
    [".shadow" {:--gi-shadow "0 1px 3px 0 rgba(0,0,0,0.1),0 1px 2px 0 rgba(0,0,0,0.06)"
                :box-shadow "var(--gi-ring-offset-shadow,0 0 #0000),var(--gi-ring-shadow,0 0 #0000),var(--gi-shadow)"}]

    "shadow-none"
    [".shadow-none" {:--gi-shadow "0 0 #0000"
                     :box-shadow "var(--gi-ring-offset-shadow,0 0 #0000),var(--gi-ring-shadow,0 0 #0000),var(--gi-shadow)"}]

    "shadow-inner"
    [".shadow-inner" {:--gi-shadow "inset 0 2px 4px 0 rgba(0,0,0,0.06)"
                      :box-shadow "var(--gi-ring-offset-shadow,0 0 #0000),var(--gi-ring-shadow,0 0 #0000),var(--gi-shadow)"}]

    "shadow-2xl"
    [".shadow-2xl" {:--gi-shadow "0 25px 50px -12px rgba(0,0,0,0.25)"
                    :box-shadow "var(--gi-ring-offset-shadow,0 0 #0000),var(--gi-ring-shadow,0 0 #0000),var(--gi-shadow)"}]

    "opacity-0"
    [".opacity-0" {:opacity 0}]

    "opacity-20"
    [".opacity-20" {:opacity 0.2}]

    "opacity-100"
    [".opacity-100" {:opacity 1}]))
