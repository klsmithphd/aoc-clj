(ns aoc-clj.2024.day08
  "Solution to https://adventofcode.com/2024/day/8"
  (:require [aoc-clj.utils.grid.mapgrid :as mg]))

;; Input parsing
(defn parse
  [input]
  (mg/ascii->MapGrid2D identity input))