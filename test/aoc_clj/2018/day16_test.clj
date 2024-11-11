(ns aoc-clj.2018.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day16 :as d16]))

(def d16-s00-raw
  ["Before: [3, 2, 1, 1]"
   "9 2 1 2"
   "After:  [3, 2, 2, 1]"
   ""
   "Before: [0, 1, 2, 1]"
   "12 3 2 2"
   "After:  [0, 1, 1, 1]"])

(def d16-s00
  [[[3 2 1 1] [9 2 1 2] [3 2 2 1]]
   [[0 1 2 1] [12 3 2 2] [0 1 1 1]]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d16-s00 (d16/parse d16-s00-raw)))))