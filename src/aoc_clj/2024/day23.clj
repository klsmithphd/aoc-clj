(ns aoc-clj.2024.day23
  "Solution to https://adventofcode.com/2024/day/23")

;; Input parsing
(defn parse-line
  [line]
  {(subs line 0 2) #{(subs line 3)}})

(defn parse
  [input]
  (->> (map parse-line input)
       (apply merge-with into)))