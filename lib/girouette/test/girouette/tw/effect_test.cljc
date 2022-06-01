(ns girouette.tw.effect-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [tw-v2-class-name->garden
                                              tw-v3-class-name->garden]]))

(deftest component-v2-test
  (are [class-name expected-garden]
    (= expected-garden (tw-v2-class-name->garden class-name))

    ;; Box shadow v2
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
                    :box-shadow "var(--gi-ring-offset-shadow,0 0 #0000),var(--gi-ring-shadow,0 0 #0000),var(--gi-shadow)"}]))


;; The v3 components which are incompatible with v2.
(deftest component-v3-test
  (are [class-name expected-garden]
    (= expected-garden (tw-v3-class-name->garden class-name))

    ;; Box shadow v3
    "shadow"
    [".shadow" {:--gi-shadow         "0 1px 3px 0 rgba(0,0,0/0.1),0 1px 2px -1px rgba(0,0,0/0.1)"
                :--gi-shadow-colored "0 1px 3px 0 var(--gi-shadow-color),0 1px 2px -1px var(--gi-shadow-color)"
                :box-shadow          "var(--gi-ring-offset-shadow,0 0 #0000),var(--gi-ring-shadow,0 0 #0000),var(--gi-shadow)"}]

    "shadow-none"
    [".shadow-none" {:--gi-shadow         "0 0 #0000",
                     :--gi-shadow-colored "0 0 var(--gi-shadow-color)",
                     :box-shadow          "var(--gi-ring-offset-shadow,0 0 #0000),var(--gi-ring-shadow,0 0 #0000),var(--gi-shadow)"}]

    "shadow-inner"
    [".shadow-inner" {:--gi-shadow "inset 0 2px 4px 0 rgba(0,0,0/0.05)",
                      :--gi-shadow-colored "inset 0 2px 4px 0 var(--gi-shadow-color)"
                      :box-shadow "var(--gi-ring-offset-shadow,0 0 #0000),var(--gi-ring-shadow,0 0 #0000),var(--gi-shadow)"}]

    "shadow-2xl"
    [".shadow-2xl" {:--gi-shadow "0 25px 50px -12px rgba(0,0,0/0.25)",
                    :--gi-shadow-colored "0 25px 50px -12px var(--gi-shadow-color)"
                    :box-shadow "var(--gi-ring-offset-shadow,0 0 #0000),var(--gi-ring-shadow,0 0 #0000),var(--gi-shadow)"}]

    ;; Box shadow color
    "shadow-inherit"
    [".shadow-inherit" {:--gi-shadow-color "inherit"
                        :--gi-shadow "var(--gi-shadow-colored)"}]

    "shadow-current"
    [".shadow-current" {:--gi-shadow-color "currentColor"
                        :--gi-shadow "var(--gi-shadow-colored)"}]

    "shadow-cyan-500-50"
    [".shadow-cyan-500-50" {:--gi-shadow-color "#06b6d47f"
                            :--gi-shadow "var(--gi-shadow-colored)"}]

    "shadow-cyan-500/50"
    [".shadow-cyan-500\\/50" {:--gi-shadow-color "#06b6d47f"
                              :--gi-shadow "var(--gi-shadow-colored)"}]

    ;; Opacity
    "opacity-0"
    [".opacity-0" {:opacity 0}]

    "opacity-20"
    [".opacity-20" {:opacity 0.2}]

    "opacity-100"
    [".opacity-100" {:opacity 1}]

    ;; Mix Blend Color
    "mix-blend-normal"
    [".mix-blend-normal" {:mix-blend-mode "normal"}]

    "mix-blend-multiply"
    [".mix-blend-multiply" {:mix-blend-mode "multiply"}]

    "mix-blend-screen"
    [".mix-blend-screen" {:mix-blend-mode "screen"}]

    "mix-blend-overlay"
    [".mix-blend-overlay" {:mix-blend-mode "overlay"}]

    "mix-blend-darken"
    [".mix-blend-darken" {:mix-blend-mode "darken"}]

    "mix-blend-lighten"
    [".mix-blend-lighten" {:mix-blend-mode "lighten"}]

    "mix-blend-color-dodge"
    [".mix-blend-color-dodge" {:mix-blend-mode "color-dodge"}]

    "mix-blend-color-burn"
    [".mix-blend-color-burn" {:mix-blend-mode "color-burn"}]

    "mix-blend-hard-light"
    [".mix-blend-hard-light" {:mix-blend-mode "hard-light"}]

    "mix-blend-soft-light"
    [".mix-blend-soft-light" {:mix-blend-mode "soft-light"}]

    "mix-blend-difference"
    [".mix-blend-difference" {:mix-blend-mode "difference"}]

    "mix-blend-exclusion"
    [".mix-blend-exclusion" {:mix-blend-mode "exclusion"}]

    "mix-blend-hue"
    [".mix-blend-hue" {:mix-blend-mode "hue"}]

    "mix-blend-saturation"
    [".mix-blend-saturation" {:mix-blend-mode "saturation"}]

    "mix-blend-color"
    [".mix-blend-color" {:mix-blend-mode "color"}]

    "mix-blend-luminosity"
    [".mix-blend-luminosity" {:mix-blend-mode "luminosity"}]

    ;; background blend mode
    "bg-blend-normal"
    [".bg-blend-normal" {:background-blend-mode "normal"}]

    "bg-blend-multiply"
    [".bg-blend-multiply" {:background-blend-mode "multiply"}]

    "bg-blend-screen"
    [".bg-blend-screen" {:background-blend-mode "screen"}]

    "bg-blend-overlay"
    [".bg-blend-overlay" {:background-blend-mode "overlay"}]

    "bg-blend-darken"
    [".bg-blend-darken" {:background-blend-mode "darken"}]

    "bg-blend-lighten"
    [".bg-blend-lighten" {:background-blend-mode "lighten"}]

    "bg-blend-color-dodge"
    [".bg-blend-color-dodge" {:background-blend-mode "color-dodge"}]

    "bg-blend-color-burn"
    [".bg-blend-color-burn" {:background-blend-mode "color-burn"}]

    "bg-blend-hard-light"
    [".bg-blend-hard-light" {:background-blend-mode "hard-light"}]

    "bg-blend-soft-light"
    [".bg-blend-soft-light" {:background-blend-mode "soft-light"}]

    "bg-blend-difference"
    [".bg-blend-difference" {:background-blend-mode "difference"}]

    "bg-blend-exclusion"
    [".bg-blend-exclusion" {:background-blend-mode "exclusion"}]

    "bg-blend-hue"
    [".bg-blend-hue" {:background-blend-mode "hue"}]

    "bg-blend-saturation"
    [".bg-blend-saturation" {:background-blend-mode "saturation"}]

    "bg-blend-color"
    [".bg-blend-color" {:background-blend-mode "color"}]

    "bg-blend-luminosity"
    [".bg-blend-luminosity" {:background-blend-mode "luminosity"}]))
