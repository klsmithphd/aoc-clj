(ns aoc-clj.2024.day02
  "Solution to https://adventofcode.com/2024/day/2")

;; Input parsing
(defn parse-line
  [line]
  (map read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))