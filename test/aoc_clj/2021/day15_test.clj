(ns aoc-clj.2021.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day15 :as t]))

(def day15-sample
  (t/parse
   ["1163751742"
    "1381373672"
    "2136511328"
    "3694931569"
    "7463417111"
    "1319128137"
    "1359912421"
    "3125421639"
    "1293138521"
    "2311944581"]))

(deftest find-path-vals
  (testing "Finds the risk values along the safest path in sample data"
    (is (= '(1 1 2 1 3 6 5 1 1 1 5 1 1 3 2 3 2 1 1)
           (t/find-path-vals day15-sample)))))

(deftest find-path-risk
  (testing "Finds the total risk along the safest path in sample data"
    (is (= 40 (t/path-risk day15-sample)))))

(deftest find-path-risk-tiled
  (testing "Finds the risk when the values have been tiled per part2 rules"
    (is (= 315 (t/path-risk (t/tile day15-sample 5))))))

(def day15-input (u/parse-puzzle-input t/parse 2021 15))

(deftest day15-part1-soln
  (testing "Reproduces the answer for day15, part1"
    (is (= 508 (t/day15-part1-soln day15-input)))))

(deftest day15-part2-soln
  (testing "Reproduces the answer for day15, part2"
    (is (= 2872 (t/day15-part2-soln day15-input)))))
