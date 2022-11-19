(ns aoc-clj.core-test
  (:require [clojure.test :refer [deftest testing is]]))

(deftest useless-test
  (testing "Does nothing at the moment"
    (is (= 1 1))))
