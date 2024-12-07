(ns aoc-clj.2024.day07
  "Solution to https://adventofcode.com/2024/day/7")

;; Input parsing
(defn parse-line
  [line]
  (map read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))