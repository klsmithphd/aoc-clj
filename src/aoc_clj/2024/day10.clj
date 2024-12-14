(ns aoc-clj.2024.day10
  "Solution to https://adventofcode.com/2024/day/10"
  (:require [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.vecgrid :as vg]))

;; Input parsing
(defn parse
  [input]
  (vg/ascii->VecGrid2D (comp read-string str) input :down true))

;; Puzzle logic
(defn trailheads
  [grid]
  (->> (grid/pos-seq grid)
       (filter #(= 0 (grid/value grid %)))
       set))