(ns aoc-clj.2017.day19
  "Solution to https://adventofcode.com/2017/day/19"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.vecgrid :as vg]))

;; Input parsing
(defn charmap
  [ch]
  (case ch
    \  :space
    \+ :corner
    \| :vert
    \- :horiz
    (str ch)))

(defn parse
  [input]
  (vg/ascii->VecGrid2D charmap input :down true))

;; Puzzle logic
(defn start
  [grid]
  [(u/index-of (u/equals? :vert) (first (:v grid))) 0])


;; Puzzle solutions