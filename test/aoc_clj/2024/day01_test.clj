(ns aoc-clj.2024.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2024.day01 :as d01]))

(def d01-s00-raw
  ["3   4"
   "4   3"
   "2   5"
   "1   3"
   "3   9"
   "3   3"])

(def d01-s00
  [[3 4 2 1 3 3]
   [4 3 5 3 9 3]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d01-s00 (d01/parse d01-s00-raw)))))