(ns girouette.tw.common-test
  (:require
    [clojure.test :refer [deftest testing is are]]
    [girouette.tw.core :refer [class-name->garden]]))

(deftest prefixes-test
  (are [class-name expected-garden]
    (= (class-name->garden class-name) expected-garden)

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
