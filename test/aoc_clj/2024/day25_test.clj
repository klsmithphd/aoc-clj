(ns aoc-clj.2024.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day25 :as d25]))

(def d25-s00-raw
  ["#####"
   ".####"
   ".####"
   ".####"
   ".#.#."
   ".#..."
   "....."
   ""
   "#####"
   "##.##"
   ".#.##"
   "...##"
   "...#."
   "...#."
   "....."
   ""
   "....."
   "#...."
   "#...."
   "#...#"
   "#.#.#"
   "#.###"
   "#####"
   ""
   "....."
   "....."
   "#.#.."
   "###.."
   "###.#"
   "###.#"
   "#####"
   ""
   "....."
   "....."
   "....."
   "#...."
   "#.#.."
   "#.#.#"
   "#####"])

(def d25-s00
  {:locks [[0 5 3 4 3]
           [1 2 0 5 3]]
   :keys  [[5 0 2 1 3]
           [4 3 4 0 2]
           [3 0 2 0 1]]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d25-s00 (d25/parse d25-s00-raw)))))

(deftest lock-key-fits-test
  (testing "Finds the lock-key combinations that fit"
    (is (= [[[0 5 3 4 3] [3 0 2 0 1]]
            [[1 2 0 5 3] [4 3 4 0 2]]
            [[1 2 0 5 3] [3 0 2 0 1]]]
           (d25/lock-key-fits d25-s00)))))

(deftest lock-key-fits-count-test
  (testing "Counts the number of lock-key pairs that fit"
    (is (= 3 (d25/lock-key-fits-count d25-s00)))))

(def day25-input (u/parse-puzzle-input d25/parse 2024 25))

(deftest part1-test
  (testing "Reproduces the answer for day25, part1"
    (is (= 3223 (d25/part1 day25-input)))))