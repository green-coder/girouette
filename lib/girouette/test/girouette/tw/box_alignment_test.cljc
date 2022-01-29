(ns girouette.tw.box-alignment-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "justify-start"
    [".justify-start" {:justify-content "flex-start"}]

    "justify-items-stretch"
    [".justify-items-stretch" {:justify-items "stretch"}]

    "justify-self-auto"
    [".justify-self-auto" {:justify-self "auto"}]

    "content-evenly"
    [".content-evenly" {:align-content "space-evenly"}]

    "items-baseline"
    [".items-baseline" {:align-items "baseline"}]

    "self-baseline"
    [".self-baseline" {:align-self "baseline"}]

    "self-center"
    [".self-center" {:align-self "center"}]

    "place-content-between"
    [".place-content-between" {:place-content "space-between"}]

    "place-items-auto"
    [".place-items-auto" {:place-items "auto"}]

    "place-self-center"
    [".place-self-center" {:place-self "center"}]))
