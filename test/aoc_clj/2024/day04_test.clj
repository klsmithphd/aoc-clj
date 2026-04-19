(ns aoc-clj.2024.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day04 :as d04]))

(def d04-s00
  (d04/parse
   ["MMMSXXMASM"
    "MSAMXMSMSA"
    "AMXSXMAAMM"
    "MSAMASMSMX"
    "XMASAMXAMM"
    "XXAMMXXAMA"
    "SMSMSASXSS"
    "SAXAMASAAA"
    "MAMMMXMMMM"
    "MXMXAXMASX"]))

(deftest x-es-test
  (testing "Finds the coordinate location of all X'es"
    (is (= #{[6 7]
             [2 2]
             [9 3]
             [5 0]
             [2 4]
             [9 1]
             [9 9]
             [3 9]
             [5 6]
             [1 4]
             [5 1]
             [4 6]
             [8 5]
             [5 5]
             [7 2]
             [9 5]
             [4 0]
             [0 5]
             [0 4]}
           (d04/x-es d04-s00)))))

(deftest xmas-coords-test
  (testing "Returns the coordinates of the XMAS strings"
    (is (= #{[[9 3] [8 4] [7 5] [6 6]]
             [[9 3] [8 2] [7 1] [6 0]]
             [[5 0] [4 1] [3 2] [2 3]]
             [[9 1] [8 2] [7 3] [6 4]]
             [[9 9] [8 9] [7 9] [6 9]]
             [[9 9] [8 8] [7 7] [6 6]]
             [[3 9] [4 9] [5 9] [6 9]]
             [[3 9] [4 8] [5 7] [6 6]]
             [[5 6] [4 5] [3 4] [2 3]]
             [[1 4] [1 3] [1 2] [1 1]]
             [[4 6] [3 6] [2 6] [1 6]]
             [[4 6] [4 5] [4 4] [4 3]]
             [[9 5] [9 6] [9 7] [9 8]]
             [[9 5] [8 6] [7 7] [6 8]]
             [[9 5] [8 4] [7 3] [6 2]]
             [[4 0] [4 1] [4 2] [4 3]]
             [[0 5] [0 6] [0 7] [0 8]]
             [[0 4] [1 5] [2 6] [3 7]]}
           (d04/xmas-coords d04-s00)))))

(def day04-input (u/parse-puzzle-input d04/parse 2024 4))

(deftest part1-test
  (testing "Reproduces the answer for day04, part1"
    (is (= 2545 (d04/part1 day04-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day04, part2"
    (is (= 1886 (d04/part2 day04-input)))))