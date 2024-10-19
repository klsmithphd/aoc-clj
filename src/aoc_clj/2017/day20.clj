(ns aoc-clj.2017.day20
  "Solution to https://adventofcode.com/2017/day/20")

;; Input parsing
(defn parse-line
  [line]
  (->> (re-seq #"-?\d+" line)
       (map read-string)
       (partition 3)
       (zipmap [:p :v :a])))

(defn parse
  [input]
  (mapv parse-line input))