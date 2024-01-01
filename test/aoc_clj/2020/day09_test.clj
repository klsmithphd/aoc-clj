(ns aoc-clj.2020.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day09 :as t]))

(def d09-s00
  (t/parse
   ["35"
    "20"
    "15"
    "25"
    "47"
    "40"
    "62"
    "55"
    "65"
    "95"
    "102"
    "117"
    "150"
    "182"
    "127"
    "219"
    "299"
    "277"
    "309"
    "576"]))

(deftest part1-sample
  (testing "Can find the first value not a sum of previous values in window"
    (is (= 127 (t/first-non-sum d09-s00 5)))))

(deftest part2-sample
  (testing "Can find the limits of the continuous range that sums to invalid number"
    (is (= [15 47] (t/contiguous-range-to-sum d09-s00 127)))))

(def day09-input (u/parse-puzzle-input t/parse 2020 9))

(deftest day09-part1-soln
  (testing "Reproduces the answer for day09, part1"
    (is (= 22406676 (t/day09-part1-soln day09-input)))))

(deftest day09-part2-soln
  (testing "Reproduces the answer for day09, part2"
    (is (= 2942387 (t/day09-part2-soln day09-input)))))