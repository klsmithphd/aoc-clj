(ns aoc-clj.2017.day20
  "Solution to https://adventofcode.com/2017/day/20"
  (:require [aoc-clj.utils.vectors :as vec]))

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

;; Puzzle logic
(defn closest-to-origin
  [particles]
  (->> particles
       (map-indexed (fn [idx p] [idx (vec/l2-norm (:a p))]))
       (apply min-key second)
       first))

(defn part1
  [input]
  (closest-to-origin input))