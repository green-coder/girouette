(ns girouette.version-test
  (:require
    [clojure.test :refer [deftest testing is are]]
    [girouette.version :as gv]))

(deftest version-<-test
  (is (false? (#'gv/version-< [:tw 2] [:tw 1])))
  (is (false? (#'gv/version-< [:tw 2] [:tw 1 9])))
  (is (false? (#'gv/version-< [:tw 2] [:tw 2])))
  (is (false? (#'gv/version-< [:tw 2] [:tw 2 0])))
  (is (true?  (#'gv/version-< [:tw 2] [:tw 2 0 1])))
  (is (true?  (#'gv/version-< [:tw 2] [:tw 3]))))

(deftest version-<=-test
  (is (false? (#'gv/version-<= [:tw 2] [:tw 1])))
  (is (false? (#'gv/version-<= [:tw 2] [:tw 1 9])))
  (is (true?  (#'gv/version-<= [:tw 2] [:tw 2])))
  (is (true?  (#'gv/version-<= [:tw 2] [:tw 2 0])))
  (is (true?  (#'gv/version-<= [:tw 2] [:tw 2 0 1])))
  (is (true?  (#'gv/version-<= [:tw 2] [:tw 3]))))
