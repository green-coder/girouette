(ns girouette.tw.background-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "bg-green-300"
    [".bg-green-300" {:--gi-bg-opacity 1
                      :background-color "rgba(134, 239, 172, var(--gi-bg-opacity))"}]

    "bg-repeat-round"
    [".bg-repeat-round" {:background-repeat "round"}]

    "bg-auto"
    [".bg-auto" {:background-size "auto"}]

    "bg-size-auto-auto"
    [".bg-size-auto-auto" {:background-size [["auto" "auto"]]}]

    "bg-size-10px-20px"
    [".bg-size-10px-20px" {:background-size [["10px" "20px"]]}]

    "bg-size-px-20%"
    [".bg-size-px-20\\%" {:background-size [["1px" "20%"]]}]

    "bg-none"
    [".bg-none" {:background-image "none"}]

    "bg-gradient-to-r"
    [".bg-gradient-to-r" {:background-image "linear-gradient(to right,var(--gi-gradient-stops))"}]

    "bg-gradient-to-tr"
    [".bg-gradient-to-tr" {:background-image "linear-gradient(to top right,var(--gi-gradient-stops))"}]

    "from-blue-500"
    [".from-blue-500" {:--gi-gradient-from "#3b82f6"
                       :--gi-gradient-stops "var(--gi-gradient-from),var(--gi-gradient-to,#3b82f600)"}]

    "to-red-500"
    [".to-red-500" {:--gi-gradient-to "#ef4444"}]

    "via-white"
    [".via-white" {:--gi-gradient-stops "var(--gi-gradient-from),#f0f0f0,var(--gi-gradient-to,#f0f0f000)"}]))
