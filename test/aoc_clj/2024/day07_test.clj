(ns aoc-clj.2024.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day07 :as d07]))

(def d07-s00-raw
  ["190: 10 19"
   "3267: 81 40 27"
   "83: 17 5"
   "156: 15 6"
   "7290: 6 8 6 15"
   "161011: 16 10 13"
   "192: 17 8 14"
   "21037: 9 7 18 13"
   "292: 11 6 16 20"])

(def d07-s00
  [[190 10 19]
   [3267 81 40 27]
   [83 17 5]
   [156 15 6]
   [7290 6 8 6 15]
   [161011 16 10 13]
   [192 17 8 14]
   [21037 9 7 18 13]
   [292 11 6 16 20]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d07-s00 (d07/parse d07-s00-raw)))))

(deftest possible-values-test
  (testing "Determines the possible values that could result from inserting +
            and * operators"
    (is (= [190 29]              (d07/possible-values-p1 (next (nth d07-s00 0)))))
    (is (= [87480 3267 3267 148] (d07/possible-values-p1 (next (nth d07-s00 1)))))))

(deftest possibly-true-eqns-test
  (testing "Filters to just the equations that could possibly be true with
            the correct operators inserted"
    (is (= [[190 10 19]
            [3267 81 40 27]
            [292 11 6 16 20]]
           (d07/possibly-true-eqns :part1 d07-s00)))))

(deftest possibly-true-test-sums-test
  (testing "Computes the sum of the test values from eqns that could possibly
            be true"
    (is (= 3749 (d07/possibly-true-test-sums :part1 d07-s00)))))

(def day07-input (u/parse-puzzle-input d07/parse 2024 7))

(deftest part1-test
  (testing "Reproduces the answer for day07, part1"
    (is (= 1289579105366 (d07/part1 day07-input)))))