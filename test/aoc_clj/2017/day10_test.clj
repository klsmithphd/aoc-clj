(ns aoc-clj.2017.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day10 :as d10]))

(def d10-s00-raw ["3, 4, 1, 5"])
(def d10-s00 [3 4 1 5])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d10-s00 (d10/parse d10-s00-raw)))))