(ns aoc-clj.2018.day01
  "Solution to https://adventofcode.com/2018/day/1"
  (:require
   [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse
  [input]
  (map read-string input))

;; Puzzle logic
(defn net-freq-change
  "Sums all the changes in frequency, starting with zero"
  [deltas]
  (reduce + deltas))

(defn find-first-repeated-freq
  "Find the first net frequency that repeats"
  [deltas]
  (u/first-duplicate (reductions + 0 (cycle deltas))))

;; Puzzle solutions
(defn part1
  "What is the resulting frequency after all the changes in frequency have
   been applied?"
  [input]
  (net-freq-change input))

(defn part2
  "What is the first frequency your device reaches twice?"
  [input]
  (find-first-repeated-freq input))