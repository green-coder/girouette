(ns girouette.tw.flexbox-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "flex-grow"
    [".flex-grow" {:flex-grow 1}]

    "flex-grow-3"
    [".flex-grow-3" {:flex-grow 3}]

    "flex-grow-3/2"
    [".flex-grow-3\\/2" {:flex-grow 1.5}]

    "flex-shrink"
    [".flex-shrink" {:flex-shrink 1}]

    "flex-shrink-3"
    [".flex-shrink-3" {:flex-shrink 3}]

    "flex-basis"
    [".flex-basis" {:flex-basis 1}]

    "flex-basis-3"
    [".flex-basis-3" {:flex-basis "0.75rem"}]

    "flex-basis-px"
    [".flex-basis-px" {:flex-basis "1px"}]

    "flex-basis-auto"
    [".flex-basis-auto" {:flex-basis "auto"}]

    "flex-0"
    [".flex-0" {:flex "0 0 0%"}]

    "flex-1"
    [".flex-1" {:flex "1 1 0%"}]

    "flex-2"
    [".flex-2" {:flex "2 2 0%"}]

    "flex-4/5"
    [".flex-4\\/5" {:flex "0.8 0.8 0%"}]

    "flex-auto"
    [".flex-auto" {:flex "1 1 auto"}]

    "flex-initial"
    [".flex-initial" {:flex "0 1 auto"}]

    "flex-2-3"
    [".flex-2-3" {:flex "2 3"}]

    "flex-2-3%"
    [".flex-2-3\\%" {:flex "2 3%"}]

    "flex-2-px"
    [".flex-2-px" {:flex "2 1px"}]

    "flex-2-3-4"
    [".flex-2-3-4" {:flex "2 3 1rem"}]

    "flex-none"
    [".flex-none" {:flex "none"}]

    "flex-col-reverse"
    [".flex-col-reverse" {:flex-direction "column-reverse"}]

    "flex-nowrap"
    [".flex-nowrap" {:flex-wrap "nowrap"}]

    "order-none"
    [".order-none" {:order 0}]

    "-order-1"
    [".-order-1" {:order -1}]

    "order-1"
    [".order-1" {:order 1}]))
