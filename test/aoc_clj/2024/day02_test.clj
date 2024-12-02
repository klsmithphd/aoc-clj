(ns aoc-clj.2024.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day02 :as d02]))

(def d02-s00-raw
  ["7 6 4 2 1"
   "1 2 7 8 9"
   "9 7 6 2 1"
   "1 3 2 4 5"
   "8 6 4 4 1"
   "1 3 6 7 9"])

(def d02-s00
  [[7 6 4 2 1]
   [1 2 7 8 9]
   [9 7 6 2 1]
   [1 3 2 4 5]
   [8 6 4 4 1]
   [1 3 6 7 9]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d02-s00 (d02/parse d02-s00-raw)))))

(deftest all-increasing?-test
  (testing "Checks that the values all increase"
    (is (= [false true false false false true]
           (map d02/all-increasing? d02-s00)))))

(deftest all-drecreasing?-test
  (testing "Checks that the values all decrease"
    (is (= [true false true false false false]
           (map d02/all-decreasing? d02-s00)))))

(deftest safe-diff-size?-test
  (testing "Checks that all values change by at least 1 and at most 3"
    (is (= [true false false true false true]
           (map d02/safe-diff-size? d02-s00)))))

(deftest safe?-test
  (testing "Finds the sequences that are deemed safe"
    (is (= [true false false false false true]
           (map d02/safe? d02-s00)))))

(deftest safe-count-test
  (testing "Counts the number of safe combinations"
    (is (= 2 (d02/safe-count d02-s00)))))

(deftest safe-without-a-level-test
  (testing "Considers a sequence safe with removing at most one level"
    (is (= [true false false true true true]
           (map d02/safe-without-a-level d02-s00)))))

(deftest weaker-safe-count-test
  (testing "Counts the number of combos that pass the weaker safe check"
    (is (= 4 (d02/weaker-safe-count d02-s00)))))

(def day02-input (u/parse-puzzle-input d02/parse 2024 2))

(deftest part1-test
  (testing "Reproduces the answer for day02, part1"
    (is (= 549 (d02/part1 day02-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day02, part2"
    (is (= 589 (d02/part2 day02-input)))))
