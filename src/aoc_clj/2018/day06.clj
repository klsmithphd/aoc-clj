(ns aoc-clj.2018.day06
  "Solution to https://adventofcode.com/2018/day/6")

;; Input parsing
(defn parse-line
  [line]
  (mapv read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))