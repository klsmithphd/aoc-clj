(ns aoc-clj.2024.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
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

(deftest list-diffs-test
  (testing "Computes the individual differences between two sorted lists"
    (is (= [2 1 0 1 2 5] (d01/list-diffs d01-s00)))))

(deftest total-distance-test
  (testing "Computes the total distance between the two lists"
    (is (= 11 (d01/total-distance d01-s00)))))

(deftest similarity-score-test
  (testing "Computes the similarity score for the lists"
    (is (= 31 (d01/similarity-score d01-s00)))))

(def day01-input (u/parse-puzzle-input d01/parse 2024 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= 1222801 (d01/part1 day01-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day01, part2"
    (is (= 22545250 (d01/part2 day01-input)))))