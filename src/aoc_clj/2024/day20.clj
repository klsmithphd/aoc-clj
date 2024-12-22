(ns aoc-clj.2024.day20
  "Solution to https://adventofcode.com/2024/day/20"
  (:require [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mg]))

;; Input parsing
(def charmap
  {\# :wall
   \. :space
   \S :start
   \E :end})

(defn parse
  [input]
  (mg/ascii->MapGrid2D charmap input))