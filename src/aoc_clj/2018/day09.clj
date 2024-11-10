(ns aoc-clj.2018.day09
  "Solution to https://adventofcode.com/2018/day/9")

;; Input parsing
(defn parse
  [input]
  (mapv read-string (re-seq #"\d+" (first input))))