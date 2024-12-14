(ns aoc-clj.2024.day14
  "Solution to https://adventofcode.com/2024/day/14")

;; Input parsing
(defn parse-line
  [line]
  (->> (re-seq #"-?\d+" line)
       (map read-string)
       (partition 2)
       (map vec)))

(defn parse
  [input]
  (map parse-line input))