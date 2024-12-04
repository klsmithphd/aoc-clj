(ns aoc-clj.2024.day04
  "Solution to https://adventofcode.com/2024/day/4"
  (:require [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.vectors :as v]))

;; Constants
(def deltas
  [[[0 0] [1 0] [2 0] [3 0]]
   [[0 0] [0 1] [0 2] [0 3]]
   [[0 0] [0 -1] [0 -2] [0 -3]]
   [[0 0] [-1 0] [-2 0] [-3 0]]
   [[0 0] [1 1] [2 2] [3 3]]
   [[0 0] [1 -1] [2 -2] [3 -3]]
   [[0 0] [-1 1] [-2 2] [-3 3]]
   [[0 0] [-1 -1] [-2 -2] [-3 -3]]])

;; Input parsing
(defn parse
  [input]
  (mg/ascii->MapGrid2D identity input :down true))

;; Puzzle logic

