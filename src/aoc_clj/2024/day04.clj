(ns aoc-clj.2024.day04
  "Solution to https://adventofcode.com/2024/day/4"
  (:require [aoc-clj.utils.grid :refer [value]]
            [aoc-clj.utils.grid.mapgrid :as mg]
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
(defn x-es
  "Finds the set of coordination location of all the X'es"
  [{:keys [grid]}]
  (->> grid
       (filter #(= \X (val %)))
       keys
       set))

(defn search-coords
  "For a given X pos, returns the collection of coordinates to search
   for X M A S"
  [pos]
  (map (fn [ds]
         (map #(v/vec-add pos %) ds)) deltas))

(defn is-xmas?
  [grid coords]
  (= [\X \M \A \S]
     (map #(value grid %) coords)))

(defn xmas-coords
  [input]
  (->> (x-es input)
       (mapcat search-coords)
       (filter #(is-xmas? input %))))

(defn xmas-coords-count
  [input]
  (count (xmas-coords input)))

;; Puzzle solutions
(defn part1
  [input]
  (xmas-coords-count input))