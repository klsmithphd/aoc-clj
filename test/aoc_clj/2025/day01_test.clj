(ns aoc-clj.2025.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day01 :as d01]))

(def d01-s00-raw
  ["L68"
   "L30"
   "R48"
   "L5"
   "R60"
   "L55"
   "L1"
   "L99"
   "R14"
   "L82"])

(def d01-s00
  [-68 -30 48 -5 60 -55 -1 -99 14 -82])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d01-s00 (d01/parse d01-s00-raw)))))

(def day01-input (u/parse-puzzle-input d01/parse 2025 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= :not-implemented (d01/part1 day01-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day01, part2"
    (is (= :not-implemented (d01/part2 day01-input)))))