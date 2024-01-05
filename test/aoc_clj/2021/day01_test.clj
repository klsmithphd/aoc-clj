(ns aoc-clj.2021.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day01 :as t]))

(def d01-s00
  [199
   200
   208
   210
   200
   207
   240
   269
   260
   263])

(deftest number-of-increases
  (testing "Finds the number of times the depth increases relative to the previous value"
    (is (= 7 (t/increases d01-s00)))))

(deftest sliding-window-sums
  (testing "Correctly adds up each triplet of 3 values in a sliding window"
    (is [607
         618
         618
         617
         647
         716
         769
         792]
        (t/sliding-window-sum d01-s00))))

(def day01-input (u/parse-puzzle-input t/parse 2021 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= 1292 (t/part1 day01-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day01, part2"
    (is (= 1262 (t/part2 day01-input)))))