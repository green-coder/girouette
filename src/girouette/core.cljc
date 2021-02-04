(ns girouette.core)

(defmacro css
  "This macro should be used as a way to tag the expressions where
   all the strings and keywords should be interpreted as css class names."
  [expr]
  expr)

#_ (defmacro not-css
     "This macro should be used as a way to tag the expressions where
   none of the strings and keywords should be interpreted as css class names.

   It may be used inside an expression annotated by the `css` macro above."
     [expr]
     expr)

(defmacro hiccup
  "This macro should be used as a way to tag the expressions which are
  in the Hiccup format, hinting code processors at how to find css class names."
  [expr]
  expr)
