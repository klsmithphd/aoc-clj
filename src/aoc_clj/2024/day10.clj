(ns aoc-clj.2024.day10
  "Solution to https://adventofcode.com/2024/day/10"
  (:require [aoc-clj.utils.graph :as graph :refer [Graph]]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.vecgrid :as vg]))

;; Records
(defrecord GridGraph [grid]
  Graph
  (vertices
    [_]
    (grid/pos-seq grid))

  (edges
    [_ v]
    (->> (grid/adj-coords-2d v)
         (filter #(number? (grid/value grid %)))
         (filter #(= 1 (- (grid/value grid %) (grid/value grid v))))))

  (distance
    [_ _ _]
    1))

;; Input parsing
(defn parse
  [input]
  (vg/ascii->VecGrid2D (comp read-string str) input :down true))

;; Puzzle logic
(defn trailheads
  "Returns a set of all the possible trailheads"
  [grid]
  (->> (grid/pos-seq grid)
       (filter #(= 0 (grid/value grid %)))
       set))

(defn finish?
  "Returns true when reaching the finish state (a height of 9)"
  [grid pos]
  (= 9 (grid/value grid pos)))

(defn score
  "A trailhead's score is the number of unique summits that can be reached
   via a hiking trail "
  [grid trailhead]
  (->> (graph/all-paths-dfs (->GridGraph grid) trailhead (partial finish? grid))
       (map last)
       set
       count))

(defn trailhead-score-sum
  "Returns the sum of the scores from all the trailheads contained in the grid"
  [grid]
  (->> (trailheads grid)
       (map #(score grid %))
       (reduce +)))

;; Puzzle solutions
(defn part1
  "What is the sum of the scores of all trailheads on your topographic map?"
  [input]
  (trailhead-score-sum input))