(ns aoc-clj.2017.day24
  "Solution to https://adventofcode.com/2017/day/23")


(defn parse-line
  [line]
  (mapv read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))