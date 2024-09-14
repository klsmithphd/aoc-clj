(ns aoc-clj.2017.day13
  "Solution to https://adventofcode.com/2017/day/13")

;; Input parsing
(defn parse-line
  [line]
  (mapv read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (into {} (map parse-line input)))
