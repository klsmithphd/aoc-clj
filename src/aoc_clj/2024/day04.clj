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

(def x-mas-down-right [[-1 1] [0 0] [1 -1]])
(def x-mas-up-right   [[-1 -1] [0 0] [1 1]])
(def x-mas-down-left  [[1 1] [0 0] [-1 -1]])
(def x-mas-up-left    [[1 -1] [0 0] [-1 1]])
(def x-mas-deltas
  [(concat x-mas-up-right x-mas-down-right)
   (concat x-mas-up-right x-mas-up-left)
   (concat x-mas-down-left x-mas-down-right)
   (concat x-mas-down-left x-mas-up-left)])

;; Input parsing
(defn parse
  [input]
  (mg/ascii->MapGrid2D identity input :down true))

;; Puzzle logic
(defn char-positions
  [ch {:keys [grid]}]
  (->> grid
       (filter #(= ch (val %)))
       keys
       set))

(def x-es (partial char-positions \X))
(def a-es (partial char-positions \A))

(defn search-coords
  "For a given X pos, returns the collection of coordinates to search
   for X M A S"
  [search-pattern pos]
  (map (fn [ds]
         (map #(v/vec-add pos %) ds)) search-pattern))

(defn matches?
  [pattern grid coords]
  (= pattern (map #(value grid %) coords)))

(def is-xmas? (partial matches? [\X \M \A \S]))
(def is-x-mas? (partial matches? [\M \A \S \M \A \S]))

(defn pattern-coords
  [start-pts deltas matcher input]
  (->> (start-pts input)
       (mapcat #(search-coords deltas %))
       (filter #(matcher input %))
       set))

(def xmas-coords (partial pattern-coords x-es xmas-deltas is-xmas?))
(def x-mas-coords (partial pattern-coords a-es x-mas-deltas is-x-mas?))

;; Puzzle solutions
(defn part1
  "How many times does XMAS appear?"
  [input]
  (count (xmas-coords input)))

(defn part2
  "How many times does an X-MAS appear?"
  [input]
  (count (x-mas-coords input)))