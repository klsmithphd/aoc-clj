(ns aoc-clj.2024.day18
  "Solution to https://adventofcode.com/2024/day/18")

;; Input parsing
(defn parse-line
  [line]
  (map read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))