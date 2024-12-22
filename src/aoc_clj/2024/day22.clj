(ns aoc-clj.2024.day22
  "Solution to https://adventofcode.com/2024/day/22")

;; Input parsing
(defn parse
  [input]
  (map read-string input))