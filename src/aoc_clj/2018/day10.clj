(ns aoc-clj.2018.day10
  "Solution to https://adventofcode.com/2018/day/10")

;; Input parsing
(defn parse-line
  [line]
  (->> (re-seq #"-?\d+" line)
       (map read-string)
       (partition 2)))

(defn parse
  [input]
  (map parse-line input))