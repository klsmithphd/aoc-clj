(ns aoc-clj.2024.day06
  "Solution to https://adventofcode.com/2024/day/6"
  (:require [aoc-clj.utils.grid.mapgrid :as mg]))

;; Input parsing
(def charmap {\. nil \# :wall \^ :guard})

(defn parse
  [input]
  (mg/ascii->MapGrid2D charmap input :down true))