(ns girouette.tw.typography-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.core :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "text-black"
    [".text-black" {:--gi-text-opacity 1
                    :color "#000000ff"}]

    "text-current"
    [".text-current" {:color "currentColor"}]

    "text-green-300"
    [".text-green-300" {:--gi-text-opacity 1
                        :color "#86efacff"}]

    "text-rgb-733"
    [".text-rgb-733" {:--gi-text-opacity 1
                      :color "#703030ff"}]

    "text-rgba-7338"
    [".text-rgba-7338" {:--gi-text-opacity 0.5019607843137255
                        :color "#70303080"}]

    "text-rgba-703030ff"
    [".text-rgba-703030ff" {:--gi-text-opacity 1.0, :color "#703030ff"}]

    "text-opacity-0"
    [".text-opacity-0" {:--gi-text-opacity 0}]

    "text-opacity-5"
    [".text-opacity-5" {:--gi-text-opacity 0.05}]

    "text-opacity-100"
    [".text-opacity-100" {:--gi-text-opacity 1}]))
