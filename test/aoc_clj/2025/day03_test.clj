(ns aoc-clj.2025.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day03 :as d03]))

(def d03-s00-raw
  ["987654321111111"
   "811111111111119"
   "234234234234278"
   "818181911112111"])

(def d03-s00
  [[9 8 7 6 5 4 3 2 1 1 1 1 1 1 1]
   [8 1 1 1 1 1 1 1 1 1 1 1 1 1 9]
   [2 3 4 2 3 4 2 3 4 2 3 4 2 7 8]
   [8 1 8 1 8 1 9 1 1 1 1 2 1 1 1]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d03-s00 (d03/parse d03-s00-raw)))))

(def day03-input (u/parse-puzzle-input d03/parse 2025 3))

(deftest part1-test
  (testing "Reproduces the answer for day03, part1"
    (is (= :not-implemented (d03/part1 day03-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day03, part2"
    (is (= :not-implemented (d03/part2 day03-input)))))