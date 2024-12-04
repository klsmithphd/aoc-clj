(ns aoc-clj.2024.day04
  "Solution to https://adventofcode.com/2024/day/4"
  (:require [aoc-clj.utils.grid :refer [value]]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.vectors :as v]))

;; Constants
(def xmas-deltas
  [[[0 0] [1 0] [2 0] [3 0]]
   [[0 0] [0 1] [0 2] [0 3]]
   [[0 0] [0 -1] [0 -2] [0 -3]]
   [[0 0] [-1 0] [-2 0] [-3 0]]
   [[0 0] [1 1] [2 2] [3 3]]
   [[0 0] [1 -1] [2 -2] [3 -3]]
   [[0 0] [-1 1] [-2 2] [-3 3]]
   [[0 0] [-1 -1] [-2 -2] [-3 -3]]])

(def x-mas-deltas
  [[[-1 -1] [0 0] [1 1] [-1 1] [0 0] [1 -1]]
   [[-1 -1] [0 0] [1 1] [1 -1] [0 0] [-1 1]]
   [[1 1] [0 0] [-1 -1] [-1 1] [0 0] [1 -1]]
   [[1 1] [0 0] [-1 -1] [1 -1] [0 0] [-1 1]]])

;; Input parsing
(defn parse
  [input]
  (mg/ascii->MapGrid2D identity input :down true))

;; Puzzle logic
(defn char-positions
  [{:keys [grid]} ch]
  (->> grid
       (filter #(= ch (val %)))
       keys
       set))

(defn x-es
  "Finds the set of coordination location of all the X'es"
  [grid]
  (char-positions grid \X))

(defn a-es
  [grid]
  (char-positions grid \A))

(defn search-coords
  "For a given X pos, returns the collection of coordinates to search
   for X M A S"
  [search-pattern pos]
  (map (fn [ds]
         (map #(v/vec-add pos %) ds)) search-pattern))

(defn is-xmas?
  [grid coords]
  (= [\X \M \A \S]
     (map #(value grid %) coords)))

(defn is-x-mas?
  [grid coords]
  (= [\M \A \S \M \A \S]
     (map #(value grid %) coords)))

(defn xmas-coords
  [input]
  (->> (x-es input)
       (mapcat #(search-coords xmas-deltas %))
       (filter #(is-xmas? input %))))

(defn x-mas-coords
  [input]
  (->> (a-es input)
       (mapcat #(search-coords x-mas-deltas %))
       (filter #(is-x-mas? input %))
       set))


(defn xmas-coords-count
  [input]
  (count (xmas-coords input)))

;; Puzzle solutions
(defn part1
  [input]
  (xmas-coords-count input))

(defn part2
  [input]
  (count (x-mas-coords input)))