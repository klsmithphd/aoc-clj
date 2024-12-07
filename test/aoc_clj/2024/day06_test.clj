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

(def day06-input (u/parse-puzzle-input d06/parse 2024 6))

(deftest part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= 4656 (d06/part1 day06-input)))))