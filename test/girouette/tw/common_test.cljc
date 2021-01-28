(ns girouette.tw.common-test
  (:require
    [clojure.test :refer [deftest testing is are]]
    [girouette.tw.common :refer [value-unit->css]]
    [girouette.tw.default-api :refer [class-name->garden]]))


(deftest value-unit->css-test
  (are [signus data options expected-output]
    (= expected-output (value-unit->css signus data options))

    nil [:auto] {} "auto"
    "-" [:auto] {} "auto"
    nil [:full] {} "full"
    "-" [:full] {} "full"

    nil [:full-100%] {} "100%"
    nil [:screen-100vw] {} "100vw"
    nil [:screen-100vh] {} "100vh"
    "-" [:screen-100vh] {} "-100vh"

    nil [:integer "1"] {} "1"
    nil [:number "1"] {} "1"
    nil [:number "1_5"] {} "1.5"
    nil [:number "1.5"] {} "1.5"
    "-" [:number "1.5"] {} "-1.5"
    nil [:integer "1"] {:number-unit "foo"} "1foo"
    nil [:number "1_5"] {:number-unit "foo"} "1.5foo"
    nil [:number "1.5"] {:number-unit "foo"} "1.5foo"
    "-" [:number "1.5"] {:number-unit "foo"} "-1.5foo"

    nil [:length [:number "0"] "cm"] {} "0"
    "-" [:length [:number "0"] "cm"] {} "0"
    nil [:length [:number "0"] "cm"] {:zero-unit "banana"} "0banana"
    "-" [:length [:number "0"] "cm"] {:zero-unit "banana"} "0banana"
    nil [:length [:number "1_5"] "cm"] {} "1.5cm"
    nil [:length-unit "cm"] {} "1cm"
    "-" [:length-unit "cm"] {} "-1cm"

    nil [:percentage [:number "1_5"]] {} "1.5%"
    "-" [:percentage [:number "1_5"]] {} "-1.5%"

    nil [:fraction [:number "5"] [:number "2_5"]] {} "2"
    nil [:fraction [:number "5"] [:number "2_5"]] {:fraction-unit "px"} "2px"
    nil [:fraction [:number "5"] [:number "2_5"]] {:fraction-unit :quarter-rem} "0.5rem"
    "-" [:fraction [:number "5"] [:number "2_5"]] {:fraction-unit :quarter-rem} "-0.5rem"
    nil [:fraction [:number "5"] [:number "2_5"]] {:fraction-unit "%"} "200%"
    nil [:fraction [:number "5"] [:number "2_5"]] {:fraction-unit "apple"
                                                   :zero-unit "banana"} "2apple"

    nil [:fraction [:number "0"] [:number "2_5"]] {:fraction-unit "apple"
                                                   :zero-unit "banana"} "0banana"
    "-" [:fraction [:number "0"] [:number "2_5"]] {:fraction-unit "apple"
                                                   :zero-unit "banana"} "0banana"))

(deftest prefixes-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "group-hover:container"
    [".group:hover" [".group-hover\\:container" {:width "100%"}]]

    "dark:container"
    #garden.types.CSSAtRule{:identifier :media,
                            :value {:media-queries {:prefers-color-scheme "dark"},
                                    :rules ([".dark\\:container" {:width "100%"}])}}

    "light:container"
    #garden.types.CSSAtRule{:identifier :media,
                            :value {:media-queries {:prefers-color-scheme "light"},
                                    :rules ([".light\\:container" {:width "100%"}])}}

    "motion-safe:container"
    #garden.types.CSSAtRule{:identifier :media,
                            :value {:media-queries {:prefers-reduced-motion "no-preference"},
                                    :rules ([".motion-safe\\:container" {:width "100%"}])}}

    "motion-reduce:container"
    #garden.types.CSSAtRule{:identifier :media,
                            :value {:media-queries {:prefers-reduced-motion "reduced"},
                                    :rules ([".motion-reduce\\:container" {:width "100%"}])}}

    "focus:container"
    [".focus\\:container" [:&:focus {:width "100%"}]]

    "sm:focus:container"
    #garden.types.CSSAtRule{:identifier :media,
                            :value {:media-queries {:min-width "640px"},
                                    :rules ([".sm\\:focus\\:container" [:&:focus {:max-width "640px"}]])}}

    "sm:first:focus:container"
    #garden.types.CSSAtRule{:identifier :media,
                            :value {:media-queries {:min-width "640px"},
                                    :rules ([".sm\\:first\\:focus\\:container" [:&:first [:&:focus {:max-width "640px"}]]])}}))
