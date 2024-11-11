(ns aoc-clj.2018.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day11 :as d11]))

(deftest hundreds-test
  (testing "Extracts the hundreds digit from any number"
    (is (= 0 (d11/hundreds 99)))
    (is (= 1 (d11/hundreds 112)))
    (is (= 3 (d11/hundreds 4312)))))

(deftest power-level-test
  (testing "Computes the power level of any cell"
    (is (= 4 (d11/power-level 8 [3 5])))
    (is (= -5 (d11/power-level 57 [122 79])))
    (is (= 0 (d11/power-level 39 [217 196])))
    (is (= 4 (d11/power-level 71 [101 153])))))

(deftest highest-power-3x3-square-test
  (testing "Computes the 3x3 square with highest power level"
    (is (= "33,45" (d11/highest-power-3x3-square 18)))
    (is (= "21,61" (d11/highest-power-3x3-square 42)))))

(deftest ^:slow highest-power-any-size-square-test
  (testing "Computes which NxX square has the highest power level"
    (is (= [[90 269]  16] (d11/highest-power-any-size-square 18 16 16)))
    (is (= [[232 251] 12] (d11/highest-power-any-size-square 42 12 12)))))

(def day11-input (u/parse-puzzle-input d11/parse 2018 11))

(deftest part1-test
  (testing "Reproduces the answer for day11, part1"
    (is (= "20,32" (d11/part1 day11-input)))))

(deftest ^:slow part2-test
  (testing "Reproduces the answer for day11, part2"
    (is (= 1 (d11/part2 day11-input)))))