(ns acme.frontend.my-css)

;; This CSS rules is appended to preflight by the Girouette processor.
(def my-base-css-rules
  [;; Make the font bigger
   [:html {:font-size "20px"}]

   ;; More CSS rules can be added here
   ,])


;; This symbol is referenced in `deps.edn`
(def composed-classes
  {"btn" ["p-2" "rounded"]
   "larger-btn" ["p-6" "rounded-xl"]})
