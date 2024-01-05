(ns aoc-clj.2021.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day03 :as t]))

(def d03-s00
  ["00100"
   "11110"
   "10110"
   "10111"
   "10101"
   "01111"
   "00111"
   "11100"
   "10000"
   "11001"
   "00010"
   "01010"])

(deftest power-consumption
  (testing "Computes gamma and epsilon values from sample data"
    (is (= [22 9] (t/power-consumption d03-s00)))))

(deftest gamma-and-epsilon
  (testing "Computes oxygen and co2 values from sample data"
    (is (= [23 10] (t/life-support d03-s00)))))

(def day03-input (u/parse-puzzle-input t/parse 2021 3))

(deftest part1-test
  (testing "Reproduces the answer for day03, part1"
    (is (= 3959450 (t/part1 day03-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day03, part2"
    (is (= 7440311 (t/part2 day03-input)))))