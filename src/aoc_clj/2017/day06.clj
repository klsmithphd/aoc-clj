(ns aoc-clj.2017.day06
  "Solution to https://adventofcode.com/2017/day/6")

;; Input parsing
(defn parse
  [input]
  (->> (first input)
       (re-seq #"\d+")
       (map read-string)))