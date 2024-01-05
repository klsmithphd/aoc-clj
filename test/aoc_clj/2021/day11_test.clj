(ns aoc-clj.2021.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day11 :as t]))

(def day11-sample
  (t/parse
   ["5483143223"
    "2745854711"
    "5264556173"
    "6141336146"
    "6357385478"
    "4167524645"
    "2176841721"
    "6882881134"
    "4846848554"
    "5283751526"]))

(deftest flashes-after-100-steps
  (testing "Counts total number of flashes after 100 steps in sample data"
    (is (= 1656 (t/flashes-after-100-steps day11-sample)))))

(deftest steps-until-sync
  (testing "Counts number of steps before all flashes are synchronized"
    (is (= 195 (t/steps-until-sync day11-sample)))))

(def day11-input (u/parse-puzzle-input t/parse 2021 11))

(deftest part1-test
  (testing "Reproduces the answer for day11, part1"
    (is (= 1634 (t/part1 day11-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day11, part2"
    (is (= 210 (t/part2 day11-input)))))