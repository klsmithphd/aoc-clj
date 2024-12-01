(ns aoc-clj.2024.day01
  "Solution to https://adventofcode.com/2024/day/1")

;; Input parsing
(defn parse-line
  [line]
  (map read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (let [pairs (map parse-line input)]
    [(map first pairs)
     (map second pairs)]))