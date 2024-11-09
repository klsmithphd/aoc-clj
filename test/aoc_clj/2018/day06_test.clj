(ns aoc-clj.2018.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day06 :as d06]))

(def d06-s00-raw
  ["1, 1"
   "1, 6"
   "8, 3"
   "3, 4"
   "5, 5"
   "8, 9"])

(def d06-s00
  [[1 1]
   [1 6]
   [8 3]
   [3 4]
   [5 5]
   [8 9]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d06-s00 (d06/parse d06-s00-raw)))))