(ns girouette.grammar.hiccup-tag-test
  (:require [clojure.test :refer [deftest testing is are]]
            [girouette.grammar.hiccup-tag :refer [hiccup-tag-parser]]))

(deftest parser-test
  (are [kw expected-parsed-data]
    (= (hiccup-tag-parser (name kw))
       expected-parsed-data)

    :div
    [:hiccup-tag [:html-tag "div"]]

    :div#app.foo
    [:hiccup-tag [:html-tag "div"] [:id "app"] [:class "foo"]]

    :div.foo#here
    [:hiccup-tag [:html-tag "div"] [:class "foo"] [:id "here"]]

    :div#app.foo#here.bar
    [:hiccup-tag [:html-tag "div"] [:id "app"] [:class "foo"] [:id "here"] [:class "bar"]]))
