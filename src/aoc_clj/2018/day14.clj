(ns aoc-clj.2018.day14
  "Solution to https://adventofcode.com/2018/day/14")

;; Input parsing
(defn parse
  [input]
  (map (comp read-string str) (first input)))