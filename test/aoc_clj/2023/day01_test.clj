(ns aoc-clj.2023.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day01 :as t]))

(def d01-s01
  ["1abc2"
   "pqr3stu8vwx"
   "a1b2c3d4e5f"
   "treb7uchet"])

(def d01-s02
  ["two1nine"
   "eightwothree"
   "abcone2threexyz"
   "xtwone3four"
   "4nineeightseven2"
   "zoneight234"
   "7pqrstsixteen"])

(def hard-cases
  ["eighthree"
   "sevenine"])

(deftest digits-test
  (testing "Finds all of the digits in the string"
    (is (= [1 2]       (t/digits (nth d01-s01 0))))
    (is (= [3 8]       (t/digits (nth d01-s01 1))))
    (is (= [1 2 3 4 5] (t/digits (nth d01-s01 2))))
    (is (= [7]         (t/digits (nth d01-s01 3))))

    (is (= [2 1 9]     (t/digits (nth d01-s02 0) true)))
    (is (= [8 2 3]     (t/digits (nth d01-s02 1) true)))
    (is (= [1 2 3]     (t/digits (nth d01-s02 2) true)))
    (is (= [2 1 3 4]   (t/digits (nth d01-s02 3) true)))
    (is (= [4 9 8 7 2] (t/digits (nth d01-s02 4) true)))
    (is (= [1 8 2 3 4] (t/digits (nth d01-s02 5) true)))
    (is (= [7 6]       (t/digits (nth d01-s02 6) true)))))


(deftest calibration-value-test
  (testing "Computes a calibration value (first and last digit)"
    (is (= [12 38 15 77]
           (mapv t/calibration-value d01-s01)))
    (is (= [29, 83, 13, 24, 42, 14, 76]
           (mapv #(t/calibration-value % true) d01-s02)))
    (is (= [83 79]
           (mapv #(t/calibration-value % true) hard-cases)))))

(deftest calibration-value-sum-test
  (testing "Computes the calibration value sum"
    (is (= 142 (t/calibration-value-sum d01-s01)))
    (is (= 281 (t/calibration-value-sum d01-s02 true)))))

(def day01-input (u/parse-puzzle-input t/parse 2023 1))

(deftest day01-part1-soln
  (testing "Reproduces the answer for day01, part1"
    (is (= 55172 (t/day01-part1-soln day01-input)))))

(deftest day01-part2-soln
  (testing "Reproduces the answer for day01, part2"
    (is (= 54925 (t/day01-part2-soln day01-input)))))