(ns girouette.tw.grid-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "grid-cols-none"
    [".grid-cols-none" {:grid-template-columns "none"}]
    "grid-cols-0"
    [".grid-cols-0" {:grid-template-columns "none"}]
    "grid-cols-10"
    [".grid-cols-10" {:grid-template-columns "repeat(10, minmax(0, 1fr))"}]

    "col-auto"
    [".col-auto" {:grid-column "auto"}]

    "col-span-5"
    [".col-span-5" {:grid-column "span 5 / span 5"}]
    "col-span-full"
    [".col-span-full" {:grid-column "-1 / 1"}]

    "col-start-1"
    [".col-start-1" {:grid-column-start 1}]
    "col-start-auto"
    [".col-start-auto" {:grid-column-start "auto"}]

    "col-end-1"
    [".col-end-1" {:grid-column-end 1}]
    "col-end-auto"
    [".col-end-auto" {:grid-column-end "auto"}]

    "grid-rows-none"
    [".grid-rows-none" {:grid-template-rows "none"}]
    "grid-rows-0"
    [".grid-rows-0" {:grid-template-rows "none"}]
    "grid-rows-10"
    [".grid-rows-10" {:grid-template-rows "repeat(10, minmax(0, 1fr))"}]

    "row-auto"
    [".row-auto" {:grid-row "auto"}]

    "row-span-5"
    [".row-span-5" {:grid-row "span 5 / span 5"}]
    "row-span-full"
    [".row-span-full" {:grid-row "-1 / 1"}]

    "row-start-1"
    [".row-start-1" {:grid-row-start 1}]
    "row-start-auto"
    [".row-start-auto" {:grid-row-start "auto"}]

    "row-end-1"
    [".row-end-1" {:grid-row-end 1}]
    "row-end-auto"
    [".row-end-auto" {:grid-row-end "auto"}]

    "grid-flow-row"
    [".grid-flow-row" {:grid-auto-flow "row"}]
    "grid-flow-col-dense"
    [".grid-flow-col-dense" {:grid-auto-flow "column dense"}]

    "auto-cols-auto"
    [".auto-cols-auto" {:grid-auto-columns "auto"}]
    "auto-cols-min"
    [".auto-cols-min" {:grid-auto-columns "min-content"}]

    "auto-rows-auto"
    [".auto-rows-auto" {:grid-auto-rows "auto"}]
    "auto-rows-min"
    [".auto-rows-min" {:grid-auto-rows "min-content"}]

    "gap-0"
    [".gap-0" {:gap 0}]
    "gap-x-0"
    [".gap-x-0" {:column-gap 0}]

    "gap-1"
    [".gap-1" {:gap "0.25rem"}]
    "gap-y-1"
    [".gap-y-1" {:row-gap "0.25rem"}]

    "gap-px"
    [".gap-px" {:gap "1px"}]
    "gap-y-px"
    [".gap-y-px" {:row-gap "1px"}]

    "gap-10%"
    [".gap-10\\%" {:gap "10%"}]
    "gap-y-10%"
    [".gap-y-10\\%" {:row-gap "10%"}]))
