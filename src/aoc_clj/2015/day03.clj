(ns aoc-clj.2015.day03
  "Solution to https://adventofcode.com/2015/day/3"
  (:require [aoc-clj.utils.vectors :as v]))

;; Constants
(def dir-map {\^ [0 1] \v [0 -1] \> [1 0] \< [-1 0]})

;; Input parsing
(def parse first)

;; Puzzle logic
(defn visits
  "Positions visited by following `dirs`"
  [dirs]
  (reductions v/vec-add [0 0] (map dir-map dirs)))

(defn split-visits
  "Positions visited by santa and his robot clone when splitting `dirs`"
  [dirs]
  (concat (visits (take-nth 2 dirs))
          (visits (take-nth 2 (rest dirs)))))

(defn distinct-visits
  "Count of distinct positions visited"
  [input visits]
  (count (set (visits input))))

;; Puzzle solutions
(defn part1
  "Count of distinct houses visited by santa following the directions"
  [input]
  (distinct-visits input visits))

(defn part2
  "Count of distinct houses visited by santa and his robot splitting the dirs"
  [input]
  (distinct-visits input split-visits))