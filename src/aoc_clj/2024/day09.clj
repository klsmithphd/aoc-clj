(ns aoc-clj.2024.day09
  "Solution to https://adventofcode.com/2024/day/9")

;; Input parsing
(defn parse
  [input]
  (mapv (comp read-string str) (first input)))