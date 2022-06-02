(ns ^:no-doc girouette.tw.preflight
  (:require [garden.core :refer [css]]
            [garden.selectors :as s]))

(s/defpseudoelement placeholder)

;; Adapted from https://unpkg.com/tailwindcss@2.0.3/dist/base.css
(def preflight-v2_0_3
  [;; tailwindcss v2.0.3 | MIT License | https://tailwindcss.com
   ;; modern-normalize v1.0.0 | MIT License | https://github.com/sindresorhus/modern-normalize
   ;; Document
   ;; ========

   ;; Use a better box model (opinionated).
   [:*
    ((s/selector :*) s/before)
    ((s/selector :*) s/after)
    {:box-sizing "border-box"}]

   ;; Use a more readable tab size (opinionated).
   [:.root {:-moz-tab-size 4
            :tab-size 4}]

   ;; 1. Correct the line height in all browsers.
   ;; 2. Prevent adjustments of font size after orientation changes in iOS.
   [:html {:line-height 1.15
           :-webkit-text-size-adjust "100%"}]

   ;; Sections
   ;; ========

   ;; Remove the margin in all browsers.
   [:body {:margin 0}]

   ;; Improve consistency of default fonts in all browsers. (https://github.com/sindresorhus/modern-normalize/issues/3)
   [:body {:font-family ["system-ui"
                         "apple-system" ;; Firefox supports this but not yet `system-ui`
                         "'Segoe UI'"
                         "Roboto"
                         "Helvetica"
                         "Arial"
                         "sans-serif"
                         "'Apple Color Emoji'"
                         "'Segoe UI Emoji'"]}]

   ;; Grouping content
   ;; ================

   ;; 1. Add the correct height in Firefox.
   ;; 2. Correct the inheritance of border color in Firefox. (https://bugzilla.mozilla.org/show_bug.cgi?id=190655)
   [:hr {:height 0
         :color "inherit"}]

   ;; Text-level semantics
   ;; ====================

   ;; Add the correct text decoration in Chrome, Edge, and Safari.
   [((s/selector :abbr) (s/attr :title)) {:-webkit-text-decoration [["underline" "dotted"]]
                                          :text-decoration [["underline" "dotted"]]}]

   ;; Add the correct font weight in Edge and Safari.
   [:b :strong {:font-weight "bolder"}]

   ;; 1. Improve consistency of default fonts in all browsers. (https://github.com/sindresorhus/modern-normalize/issues/3)
   ;; 2. Correct the odd 'em' font sizing in all browsers.
   [:code :kbd :samp :pre {:font-family ["ui-monospace"
                                         "SFMono-Regular"
                                         "Consolas"
                                         "'Liberation Mono'"
                                         "Menlo"
                                         "monospace"]
                           :font-size "1em"}]

   ;; Add the correct font size in all browsers.
   [:small {:font-size "80%"}]

   ;; Prevent 'sub' and 'sup' elements from affecting the line height in all browsers.
   [:sub :sup {:font-size "75%"
               :line-height 0
               :position "relative"
               :vertical-align "baseline"}]
   [:sub {:bottom "-0.25em"}]
   [:sup {:top "-0.5em"}]

   ;; Tabular data
   ;; ============

   ;; 1. Remove text indentation from table contents in Chrome and Safari. (https://bugs.chromium.org/p/chromium/issues/detail?id=999088, https://bugs.webkit.org/show_bug.cgi?id=201297)
   ;; 2. Correct table border color inheritance in all Chrome and Safari. (https://bugs.chromium.org/p/chromium/issues/detail?id=935729, https://bugs.webkit.org/show_bug.cgi?id=195016)
   [:table {:text-indent 0
            :border-color "inherit"}]

   ;; Forms
   ;; =====

   ;; 1. Change the font styles in all browsers.
   ;; 2. Remove the margin in Firefox and Safari.
   [:button :input :optgroup :select :textarea
    {:font-family "inherit"
     :font-size "100%"
     :line-height 1.15
     :margin 0}]

   ;; Remove the inheritance of text transform in Edge and Firefox.
   ;; 1. Remove the inheritance of text transform in Firefox.
   [:button :select {:text-transform "none"}]

   ;; Correct the inability to style clickable types in iOS and Safari.
   [:button
    (s/attr= :type "button")
    (s/attr= :type "reset")
    (s/attr= :type "submit")
    {:-webkit-appearance "button"}]

   ;; Remove the inner border and padding in Firefox.
   ["::-moz-focus-inner" {:border-style "none"
                          :padding 0}]

   ;; Restore the focus styles unset by the previous rule.
   [":-moz-focusring" {:outline [["1px" "dotted" "ButtonText"]]}]

   ;; Remove the additional ':invalid' styles in Firefox.
   ;; See: https://github.com/mozilla/gecko-dev/blob/2f9eacd9d3d995c937b4251a5557d95d494c9be1/layout/style/res/forms.css#L728-L737
   [":-moz-ui-invalid" {:box-shadow "none"}]

   ;; Remove the padding so developers are not caught out when they zero out 'fieldset' elements in all browsers.
   [:legend {:padding 0}]

   ;; Add the correct vertical alignment in Chrome and Firefox.
   [:progress {:vertical-align "baseline"}]

   ;; Correct the cursor style of increment and decrement buttons in Safari.
   ["::-webkit-inner-spin-button"
    "::-webkit-outer-spin-button"
    {:height "auto"}]

   ;; 1. Correct the odd appearance in Chrome and Safari.
   ;; 2. Correct the outline style in Safari.
   [(s/attr= :type "search") {:-webkit-appearance "textfield"
                              :outline-offset "-2px"}]

   ;; Remove the inner padding in Chrome and Safari on macOS.
   ["::-webkit-search-decoration" {:-webkit-appearance "none"}]

   ;; 1. Correct the inability to style clickable types in iOS and Safari.
   ;; 2. Change font properties to 'inherit' in Safari.
   ["::-webkit-file-upload-button" {:-webkit-appearance "button"
                                    :font "inherit"}]

   ;; Interactive
   ;; ===========

   ;; Add the correct display in Chrome and Safari.
   [:summary {:display "list-item"}]

   ;; Manually forked from SUIT CSS Base: https://github.com/suitcss/base
   ;; A thin layer on top of normalize.css that provides a starting point more
   ;; suitable for web applications.

   ;; Removes the default spacing and border for appropriate elements.
   [:blockquote :dl :dd
    :h1 :h2 :h3 :h4 :h5 :h6
    :hr :figure :p :pre
    {:margin 0}]

   [:button {:background-color "transparent"
             :background-image "none"}]

   ;; Work around a Firefox/IE bug where the transparent `button` background
   ;; results in a loss of the default `button` focus styles.
   [((s/selector :button) s/focus) {:outline [["1px dotted"]]}
                                   {:outline [["5px" "auto" "-webkit-focus-ring-color"]]}]

   [:fieldset {:margin 0
               :padding 0}]

   [:ol :ul {:list-style "none"
             :margin 0
             :padding 0}]

   ;; Tailwind custom reset styles

   ;; 1. Use the user's configured `sans` font-family (with Tailwind's default
   ;;    sans-serif font stack as a fallback) as a sane default.
   ;; 2. Use Tailwind's default "normal" line-height so the user isn't forced
   ;;    to override it to ensure consistency even when using the default theme.
   [:html {:font-family ["ui-sans-serif"
                         "system-ui"
                         "-apple-system"
                         "BlinkMacSystemFont"
                         "'Segoe UI'"
                         "Roboto"
                         "'Helvetica Neue'"
                         "Arial"
                         "'Noto Sans'"
                         "sans-serif"
                         "'Apple Color Emoji'"
                         "'Segoe UI Emoji'"
                         "'Segoe UI Symbol'"
                         "'Noto Color Emoji'"]
           :line-height 1.5}]

   ;; Inherit font-family and line-height from `html` so users can set them as
   ;; a class directly on the `html` element.
   [:html {:font-family "inherit"
           :line-height "inherit"}]

   ;; 1. Prevent padding and border from affecting element width.
   ;;
   ;;    We used to set this in the html element and inherit from
   ;;    the parent element for everything else. This caused issues
   ;;    in shadow-dom-enhanced elements like <details> where the content
   ;;    is wrapped by a div with box-sizing set to `content-box`.
   ;;
   ;;    https://github.com/mozdevs/cssremedy/issues/4
   ;;
   ;;
   ;; 2. Allow adding a border to an element by just adding a border-width.
   ;;
   ;;    By default, the way the browser specifies that an element should have no
   ;;    border is by setting it's border-style to `none` in the user-agent
   ;;    stylesheet.
   ;;
   ;;    In order to easily add borders to elements by just setting the `border-width`
   ;;    property, we change the default border-style for all elements to `solid`, and
   ;;    use border-width to hide them instead. This way our `border` utilities only
   ;;    need to set the `border-width` property instead of the entire `border`
   ;;    shorthand, making our border utilities much more straightforward to compose.
   ;;
   ;;    https://github.com/tailwindcss/tailwindcss/pull/116
   [:* s/before s/after
    {:box-sizing "border-box"
     :border-width 0
     :border-style "solid"
     :border-color "#e5e7eb"}]

   ;; Ensure horizontal rules are visible by default
   [:hr {:border-top-width "1px"}]

   ;; Undo the `border-style: none` reset that Normalize applies to images so that
   ;; our `border-{width}` utilities have the expected effect.
   ;;
   ;; The Normalize reset is unnecessary for us since we default the border-width
   ;; to 0 on all elements.
   ;;
   ;; https://github.com/tailwindcss/tailwindcss/issues/362
   [:img {:border-style "solid"}]

   [:textarea {:resize "vertical"}]

   [((s/selector :input) placeholder)
    ((s/selector :textarea) placeholder)
    {:opacity 1
     :color "#9ca3af"}]

   [:button (s/attr= :role "button") {:cursor "pointer"}]

   [:table {:border-collapse "collapse"}]

   [:h1 :h2 :h3 :h4 :h5 :h6 {:font-size "inherit"
                             :font-weight "inherit"}]

   ;; Reset links to optimize for opt-in styling instead of opt-out.
   [:a {:color "inherit"
        :text-decoration "inherit"}]

   ;; Reset form element properties that are easy to forget to
   ;; style explicitly so you don't inadvertently introduce
   ;; styles that deviate from your design system. These styles
   ;; supplement a partial reset that is already applied by
   ;; normalize.css.
   [:button :input :optgroup :select :textarea {:padding 0
                                                :line-height "inherit"
                                                :color "inherit"}]
   ;; Use the configured 'mono' font family for elements that
   ;; are expected to be rendered with a monospace font, falling
   ;; back to the system monospace stack if there is no configured
   ;; 'mono' font family.
   [:pre :code :kbd :samp {:font-family ["ui-monospace"
                                         "SFMono-Regular"
                                         "Menlo"
                                         "Monaco"
                                         "Consolas"
                                         "'Liberation Mono'"
                                         "'Courier New'"
                                         "monospace"]}]

   ;; Make replaced elements `display: block` by default as that's
   ;; the behavior you want almost all of the time. Inspired by
   ;; CSS Remedy, with `svg` added as well.
   ;;
   ;; https://github.com/mozdevs/cssremedy/issues/14
   [:img :svg :video :canvas :audio :iframe :embed :object {:display "block"
                                                            :vertical-align "middle"}]

   ;; Constrain images and videos to the parent width and preserve
   ;; their intrinsic aspect ratio.
   ;;
   ;; https://github.com/mozdevs/cssremedy/issues/14
   [:img :video {:max-width "100%"
                 :height "auto"}]])

#_ (-> preflight-v2_0_3
       css
       println)



;; Port in the Garden format of Tailwind CSS v3.0.24's version of Preflight
;; Source: https://unpkg.com/tailwindcss@3.0.24/src/css/preflight.css
(def preflight-v3_0_24
  [;; 1. Prevent padding and border from affecting element width. (https://github.com/mozdevs/cssremedy/issues/4)
   ;; 2. Allow adding a border to an element by just adding a border-width. (https://github.com/tailwindcss/tailwindcss/pull/116)]
   [:*
    (s/before)
    (s/after)
    {:box-sizing "border-box" ;; 1
     :border-width 0          ;; 2
     :border-style "solid"    ;; 2
     :border-color "theme('borderColor.DEFAULT', currentColor)"}] ;; 2

   [(s/before)
    (s/after)
    {:--gi-content "''"}]


   ;; 1. Use a consistent sensible line-height in all browsers.
   ;; 2. Prevent adjustments of font size after orientation changes in iOS.
   ;; 3. Use a more readable tab size.
   ;; 4. Use the user's configured `sans` font-family by default.
   [:html {:line-height 1.5                 ;; 1
           :-webkit-text-size-adjust "100%" ;; 2
           :-moz-tab-size 4                 ;; 3
           :tab-size 4                      ;; 3
           :font-family "theme('fontFamily.sans', ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, \"Noto Sans\", sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\")"}] ;; 4


   ;; 1. Remove the margin in all browsers.
   ;; 2. Inherit line-height from `html` so users can set them as a class directly on the `html` element.
   [:body {:margin 0                ;; 1
           :line-height "inherit"}] ;; 2


   ;; 1. Add the correct height in Firefox.
   ;; 2. Correct the inheritance of border color in Firefox. (https://bugzilla.mozilla.org/show_bug.cgi?id=190655)
   ;; 3. Ensure horizontal rules are visible by default.
   [:hr {:height 0                 ;; 1
         :color "inherit"          ;; 2
         :border-top-width "1px"}] ;; 3


   ;; Add the correct text decoration in Chrome, Edge, and Safari.
   ["abbr:where([title])" {:text-decoration [["underline" "dotted"]]}]


   ;; Remove the default font size and weight for headings.
   [:h1 :h2 :h3 :h4 :h5 :h6 {:font-size "inherit"
                             :font-weight "inherit"}];


   ;; Reset links to optimize for opt-in styling instead of opt-out.
   [:a {:color "inherit"
        :text-decoration "inherit"}]


   ;; Add the correct font weight in Edge and Safari.
   [:b :strong {:font-weight "bolder"}]


   ;; 1. Use the user's configured `mono` font family by default.
   ;; 2. Correct the odd `em` font sizing in all browsers.
   [:code
    :kbd
    :samp
    :pre
    {:font-family "theme('fontFamily.mono', ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, \"Liberation Mono\", \"Courier New\", monospace)" ;; 1
     :font-size "1em"}] ;; 2


   ;; Add the correct font size in all browsers.
   [:small {:font-size "80%"}]


   ;; Prevent `sub` and `sup` elements from affecting the line height in all browsers.
   [:sub :sup {:font-size "75%"
               :line-height 0;
               :position "relative";
               :vertical-align "baseline"}]
   [:sub {:bottom "-0.25em"}]
   [:sup {:top "-0.5em"}]


   ;; 1. Remove text indentation from table contents in Chrome and Safari. (https://bugs.chromium.org/p/chromium/issues/detail?id=999088, https://bugs.webkit.org/show_bug.cgi?id=201297)
   ;; 2. Correct table border color inheritance in all Chrome and Safari. (https://bugs.chromium.org/p/chromium/issues/detail?id=935729, https://bugs.webkit.org/show_bug.cgi?id=195016)
   ;; 3. Remove gaps between table borders by default.
   [:table {:text-indent 0                ;; 1
            :border-color "inherit";      ;; 2
            :border-collapse "collapse"}] ;; 3


   ;; 1. Change the font styles in all browsers.
   ;; 2. Remove the margin in Firefox and Safari.
   ;; 3. Remove default padding in all browsers.
   [:button
    :input
    :optgroup
    :select
    :textarea
    {:font-family "inherit" ;; 1
     :font-size "100%"      ;; 1
     :line-height "inherit" ;; 1
     :color "inherit"       ;; 1
     :margin 0              ;; 2
     :padding 0}]           ;; 3


   ;; Remove the inheritance of text transform in Edge and Firefox.
   [:button :select {:text-transform "none"}]


   ;; 1. Correct the inability to style clickable types in iOS and Safari.
   ;; 2. Remove default button styles.
   [:button
    (s/attr= :type "button")
    (s/attr= :type "reset")
    (s/attr= :type "submit")
    {:-webkit-appearance "button"    ;; 1
     :background-color "transparent" ;; 2
     :background-image "none"}]      ;; 2


   ;; Use the modern Firefox focus style for all focusable elements.
   [":-moz-focusring" {:outline "auto"}]


   ;; Remove the additional `:invalid` styles in Firefox. (https://github.com/mozilla/gecko-dev/blob/2f9eacd9d3d995c937b4251a5557d95d494c9be1/layout/style/res/forms.css#L728-L737)
   [":-moz-ui-invalid" {:box-shadow "none"}]


   ;; Add the correct vertical alignment in Chrome and Firefox.
   [:progress {:vertical-align "baseline"}]


   ;; Correct the cursor style of increment and decrement buttons in Safari.
   ["::-webkit-inner-spin-button"
    "::-webkit-outer-spin-button"
    {:height "auto"}]


   ;; 1. Correct the odd appearance in Chrome and Safari.
   ;; 2. Correct the outline style in Safari.
   [(s/attr= :type "search") {:-webkit-appearance "textfield" ;; 1
                              :outline-offset "-2px"}]         ;; 2


   ;; Remove the inner padding in Chrome and Safari on macOS.
   ["::-webkit-search-decoration" {:-webkit-appearance "none"}]


   ;; 1. Correct the inability to style clickable types in iOS and Safari.
   ;; 2. Change font properties to `inherit` in Safari.
   ["::-webkit-file-upload-button" {:-webkit-appearance "button" ;; 1
                                    :font "inherit"}]            ;; 2


   ;; Add the correct display in Chrome and Safari.
   [:summary {:display "list-item"}]


   ;; Removes the default spacing and border for appropriate elements.
   [:blockquote
    :dl
    :dd
    :h1
    :h2
    :h3
    :h4
    :h5
    :h6
    :hr
    :figure
    :p
    :pre
    {:margin 0}]

   [:fieldset {:margin 0
               :padding 0}]

   [:legend {:padding 0}]

   [:ol :ul :menu {:list-style "none"
                   :margin 0
                   :padding 0}]


   ;; Prevent resizing textareas horizontally by default.
   [:textarea {:resize "vertical"}]


   ;; 1. Reset the default placeholder opacity in Firefox. (https://github.com/tailwindlabs/tailwindcss/issues/3300)
   ;; 2. Set the default placeholder color to the user's configured gray 400 color.
   [((s/selector :input) placeholder)
    ((s/selector :textarea) placeholder)
    {:opacity 1                                   ;; 1
     :color "theme('colors.gray.400', #9ca3af)"}] ;; 2


   ;; Set the default cursor for buttons.
   [:button (s/attr= :role "button") {:cursor "pointer"}]


   ;; Make sure disabled buttons don't get the pointer cursor.
   [s/disabled {:cursor :default}]


   ;; 1. Make replaced elements `display: block` by default. (https://github.com/mozdevs/cssremedy/issues/14)
   ;; 2. Add `vertical-align: middle` to align replaced elements more sensibly by default. (https://github.com/jensimmons/cssremedy/issues/14#issuecomment-634934210)
   ;;    This can trigger a poorly considered lint error in some tools but is included by design.
   [:img
    :svg
    :video
    :canvas
    :audio
    :iframe
    :embed
    :object
    {:display "block"           ;; 1
     :vertical-align "middle"}] ;; 2


   ;; Constrain images and videos to the parent width and preserve their intrinsic aspect ratio. (https://github.com/mozdevs/cssremedy/issues/14)
   [:img :video {:max-width "100%"
                 :height "auto"}]


   ;; Ensure the default browser behavior of the `hidden` attribute.
   [(s/attr "hidden") {:display "none"}]

   ,]
  ,)

#_ (-> preflight-v3_0_24
       css
       println)
