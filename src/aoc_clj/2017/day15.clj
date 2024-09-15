(ns aoc-clj.2017.day15
  "Solution to https://adventofcode.com/2017/day/15")

;; Input parsing
(defn parse
  [input]
  (->> (map #(re-find #"\d+" %) input)
       (map read-string)))

;; Puzzle logic
