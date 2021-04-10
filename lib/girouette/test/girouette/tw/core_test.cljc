(ns girouette.tw.core-test
  (:require
    [clojure.test :refer [deftest testing is are]]
    [girouette.tw.layout :as layout]
    [girouette.tw.core :refer [make-api]]))

(deftest make-api-test
  (testing "The API still work without any color and font family."
    (let [{:keys [class-name->garden]} (make-api layout/components {})]
      (is (= [".flex" {:display "flex"}]
             (class-name->garden "flex"))))))
