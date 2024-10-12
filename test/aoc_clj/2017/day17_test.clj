(ns aoc-clj.2017.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day17 :as d17]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= 307 (d17/parse ["307"])))))