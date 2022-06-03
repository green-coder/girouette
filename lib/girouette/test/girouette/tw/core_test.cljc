(ns girouette.tw.core-test
  (:require
    [clojure.test :refer [deftest testing is are]]
    [girouette.tw.layout :as layout]
    [girouette.tw.core :as gc]))

(deftest assoc-ordering-level-test
  (let [id->component {1 {:before-rules #{2 3}}
                       2 {}
                       3 {}
                       4 {:after-rules #{3}}
                       5 {}}]
    (testing "The :before-rules and :after-rules should become symmetric."
      (is (= (-> id->component
                 (#'gc/complement-before-rules-after-rules))
             {1 {:before-rules #{2 3}}
              2 {:after-rules #{1}}
              3 {:before-rules #{4}
                 :after-rules #{1}}
              4 {:after-rules #{3}}
              5 {}})))

    (testing "The ordering levels should be correct."
      (is (= (-> id->component
                 (#'gc/complement-before-rules-after-rules)
                 (#'gc/assoc-ordering-level))
             {1 {:ordering-level 0
                 :before-rules #{2 3}}
              2 {:ordering-level 1
                 :after-rules #{1}}
              3 {:ordering-level 1
                 :after-rules #{1}
                 :before-rules #{4}}
              4 {:ordering-level 2
                 :after-rules #{3}}
              5 {:ordering-level 0}})))))

(deftest make-api-test
  (testing "The API still work without any color and font family."
    (let [{:keys [class-name->garden]} (gc/make-api layout/components {})]
      (is (= [".flex" {:display "flex"}]
             (class-name->garden "flex"))))))
