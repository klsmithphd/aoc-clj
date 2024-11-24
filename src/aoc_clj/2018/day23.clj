(ns aoc-clj.2018.day23
  "Solution to https://adventofcode.com/2018/day/23")

;; Input parsing
(defn parse-line
  [line]
  (let [[x y z r] (map read-string (re-seq #"\d+" line))]
    [[x y z] r]))

(defn parse
  [input]
  (map parse-line input))