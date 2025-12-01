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

(deftest dial-positions-test
  (testing "Returns a seq of all the dial positions"
    (is (= [50 82 52 0 95 55 0 99 0 14 32]
           (d01/dial-positions d01-s00)))))

(deftest zero-count-test
  (testing "Returns the number of times the dial was at position zero"
    (is (= 3 (d01/zero-count d01-s00)))))

(def day01-input (u/parse-puzzle-input d01/parse 2025 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= 1172 (d01/part1 day01-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day01, part2"
    (is (= :not-implemented (d01/part2 day01-input)))))