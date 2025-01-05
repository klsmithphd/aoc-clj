(ns aoc-clj.2018.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day11 :as d11]))

(def sample-grid
  [[3 1 4 1 5 9]
   [2 6 5 3 5 8]
   [9 7 9 3 2 3]
   [8 4 6 2 6 4]
   [3 3 8 3 2 7]
   [9 5 0 2 8 8]])

(def summed-area-table-sample
  [[3   4  8   9  14 23]
   [5  12 21  25  35 52]
   [14 28 46  53  65 85]
   [22 40 64  73  91 115]
   [25 46 78  90 110 141]
   [34 60 92 106 134 173]])

(deftest hundreds-test
  (testing "Extracts the hundreds digit from any number"
    (is (= 0 (d11/hundreds 99)))
    (is (= 1 (d11/hundreds 112)))
    (is (= 3 (d11/hundreds 4312)))))

(deftest power-level-test
  (testing "Computes the power level of any cell"
    (is (= 4 (d11/cell-power-level 8 [3 5])))
    (is (= -5 (d11/cell-power-level 57 [122 79])))
    (is (= 0 (d11/cell-power-level 39 [217 196])))
    (is (= 4 (d11/cell-power-level 71 [101 153])))))

(deftest summed-area-table-test
  (testing "Constructs a summed-area table for the input values"
    (is (= summed-area-table-sample (d11/summed-area-table sample-grid)))))

(deftest area-sum-test
  (testing "Computes the sum of the values included in an area
            using a summed area table"
    (is (= 27 (d11/area-sum summed-area-table-sample [2 3] [4 4])))))

(deftest square-area-sum-test
  (testing "Computes the enclosed sum for square regions"
    (is (= 46 (d11/square-area-sum summed-area-table-sample [0 0 3])))
    (is (= 39 (d11/square-area-sum summed-area-table-sample [1 0 3])))
    (is (= 37 (d11/square-area-sum summed-area-table-sample [2 0 3])))
    (is (= 39 (d11/square-area-sum summed-area-table-sample [3 0 3])))

    (is (= 45 (d11/square-area-sum summed-area-table-sample [1 1 3])))))

(deftest upper-coords-test
  (testing "Returns the upper-left square definitions for all
            squares of a given size that can fit in a 300x300 grid"
    (is (= [[0 0 300]] (d11/upper-coords 300)))
    (is (= [[0 0 299] [1 0 299] [0 1 299] [1 1 299]]
           (d11/upper-coords 299)))))

(deftest highest-power-3x3-square-test
  (testing "Computes the 3x3 square with highest power level"
    (is (= "33,45" (d11/highest-power-3x3-square 18)))
    (is (= "21,61" (d11/highest-power-3x3-square 42)))))

(deftest highest-power-any-size-square-test
  (testing "Computes which NxX square has the highest power level"
    (is (= "90,269,16"  (d11/highest-power-any-size-square 18)))
    (is (= "232,251,12" (d11/highest-power-any-size-square 42)))))

(def day11-input (u/parse-puzzle-input d11/parse 2018 11))

(deftest part1-test
  (testing "Reproduces the answer for day11, part1"
    (is (= "20,32" (d11/part1 day11-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day11, part2"
    (is (= "235,287,13" (d11/part2 day11-input)))))