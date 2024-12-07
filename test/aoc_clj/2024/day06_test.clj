(ns aoc-clj.2024.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day06 :as d06]))

(def d06-s00
  (d06/parse
   ["....#....."
    ".........#"
    ".........."
    "..#......."
    ".......#.."
    ".........."
    ".#..^....."
    "........#."
    "#........."
    "......#..."]))

(deftest guard-start-test
  (testing "Finds the initial state of the guard"
    (is (= {:heading :n :pos [4 3] :visited #{}}
           (d06/guard-start d06-s00)))))

(deftest distinct-guard-positions-test
  (testing "Counts the number of unique positions visited by the guard"
    (is (= 41 (d06/distinct-guard-positions d06-s00)))))

(deftest looping-guard-paths-test
  (testing "Counts how many unique places an obstruction can be placed
            to cause the guard to enter a loop"
    (is (= 6 (d06/looping-guard-paths d06-s00)))))

(def day06-input (u/parse-puzzle-input d06/parse 2024 6))

(deftest part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= 4656 (d06/part1 day06-input)))))

(deftest ^:slow part2-test
  (testing "Reproduces the answer for day06, part2"
    (is (= 1575 (d06/part2 day06-input)))))