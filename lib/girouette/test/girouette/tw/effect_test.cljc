(ns girouette.tw.effect-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.tw.default-api :refer [class-name->garden]]))

(deftest component-test
  (are [class-name expected-garden]
    (= expected-garden (class-name->garden class-name))

    "blur-none"
    [".blur-none" {:--gi-blur "blur(0)"
                   :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "blur"
    [".blur" {:--gi-blur "blur(8px)"
              :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "blur-lg"
    [".blur-lg" {:--gi-blur "blur(16px)"
                 :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "blur-1rem"
    [".blur-1rem" {:--gi-blur "blur(1rem)"
                   :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "brightness-0"
    [".brightness-0" {:--gi-brightness "brightness(0)"
                      :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "brightness-75"
    [".brightness-75" {:--gi-brightness "brightness(0.75)"
                       :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "brightness-150"
    [".brightness-150" {:--gi-brightness "brightness(1.5)"
                        :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "contrast-0"
    [".contrast-0" {:--gi-contrast "contrast(0)"
                    :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "contrast-75"
    [".contrast-75" {:--gi-contrast "contrast(0.75)"
                     :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "contrast-150"
    [".contrast-150" {:--gi-contrast "contrast(1.5)"
                      :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "drop-shadow"
    [".drop-shadow"
     {:--gi-drop-shadow "drop-shadow(0 1px 2px rgb(0 0 0 / 0.1)) drop-shadow(0 1px 1px rgb(0 0 0 / 0.06))"
      :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "drop-shadow-lg"
    [".drop-shadow-lg"
     {:--gi-drop-shadow "drop-shadow(0 10px 8px rgb(0 0 0 / 0.04)) drop-shadow(0 4px 3px rgb(0 0 0 / 0.1))"
      :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "grayscale"
    [".grayscale" {:--gi-grayscale "grayscale(100%)"
                   :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "grayscale-0"
    [".grayscale-0" {:--gi-grayscale "grayscale(0)"
                     :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "grayscale-1/2"
    [".grayscale-1\\/2" {:--gi-grayscale "grayscale(50%)"
                         :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

    "hue-rotate-30"
    [".hue-rotate-30" {:--gi-hue-rotate "hue-rotate(30deg)"
                       :filter "var(--gi-blur, ) var(--gi-brightness, ) var(--gi-contrast, ) var(--gi-grayscale, ) var(--gi-hue-rotate, ) var(--gi-invert, ) var(--gi-saturate, ) var(--gi-sepia, ) var(--gi-drop-shadow, )"}]

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
    [".bg-blend-luminosity" {:background-blend-mode "luminosity"}]

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
