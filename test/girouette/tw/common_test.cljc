(ns girouette.tw.common-test
  (:require
    [clojure.test :refer [deftest testing is are]]
    [girouette.tw.common :refer [value-unit->css]]
    [girouette.tw.default-api :refer [class-name->garden]]))


(deftest value-unit->css-test
  (are [data options expected-output]
    (= expected-output (value-unit->css data options))

    [:auto] {} "auto"
    [:auto] {:signus "-"} "auto"
    [:full] {} "full"
    [:full] {:signus "-"} "full"

    [:full-100%] {} "100%"
    [:screen-100vw] {} "100vw"
    [:screen-100vh] {} "100vh"
    [:screen-100vh] {:signus "-"} "-100vh"

    [:integer "1"] {} 1
    [:number "1"] {} 1
    [:integer "1"] {:number-unit "foo"} "1foo"
    [:integer "100"] {:value-fn #(/ % 100)} 1
    [:number "1_5"] {} 1.5
    [:number "1.5"] {} 1.5
    [:number "1.5"] {:signus "-"} -1.5
    [:number "100_5"] {:value-fn #(/ % 100)} 1.005
    [:number "1_5"] {:number-unit "foo"} "1.5foo"
    [:number "1.5"] {:number-unit "foo"} "1.5foo"
    [:number "1.5"] {:signus "-"
                     :number-unit "foo"} "-1.5foo"

    [:length [:number "0"] "cm"] {} "0cm"
    [:length [:number "0"] "cm"] {:zero-unit nil} 0
    [:length [:number "0"] "cm"] {:signus "-"} "0cm"
    [:length [:number "0"] "cm"] {:signus "-"
                                  :zero-unit nil} 0
    [:length [:number "0"] "cm"] {:zero-unit "banana"} "0banana"
    [:length [:number "0"] "cm"] {:signus "-"
                                  :zero-unit "banana"} "0banana"
    [:length [:number "1_5"] "cm"] {} "1.5cm"
    [:length-unit "cm"] {} "1cm"
    [:length-unit "cm"] {:signus "-"} "-1cm"

    [:percentage [:number "1_5"]] {} "1.5%"
    [:percentage [:number "1_5"]] {:signus "-"} "-1.5%"

    [:fraction [:number "5"] [:number "2_5"]] {} 2
    [:fraction [:number "5"] [:number "2_5"]] {:fraction-unit "px"} "2px"
    [:fraction [:number "5"] [:number "2_5"]] {:fraction-unit :quarter-rem} "0.5rem"
    [:fraction [:number "5"] [:number "2_5"]] {:signus "-"
                                               :fraction-unit :quarter-rem} "-0.5rem"
    [:fraction [:number "5"] [:number "2_5"]] {:fraction-unit "%"} "200%"
    [:fraction [:number "5"] [:number "2_5"]] {:fraction-unit "apple"
                                               :zero-unit "banana"} "2apple"

    [:fraction [:number "0"] [:number "2_5"]] {:fraction-unit "apple"
                                               :zero-unit "banana"} "0banana"
    [:fraction [:number "0"] [:number "-2_5"]] {:signus "-"
                                                :fraction-unit "apple"
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
