(ns aoc-clj.2016.day20
  "Solution to https://adventofcode.com/2016/day/20")

;; Input parsing
(defn parse-line
  [line]
  (mapv read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))