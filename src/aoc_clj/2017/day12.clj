(ns aoc-clj.2017.day12
  "Solution to https://adventofcode.com/2017/day/12")

;; Input parsing
(defn parse-line
  [line]
  (let [[id & others] (map read-string (re-seq #"\d+" line))]
    [id others]))

(defn parse
  [input]
  (into {} (map parse-line input)))