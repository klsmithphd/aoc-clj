(ns aoc-clj.2024.day04
  "Solution to https://adventofcode.com/2024/day/4"
  (:require [aoc-clj.utils.grid :refer [value]]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.vectors :as v]))

;; Constants
(def xmas-deltas
  [[[0 0] [1 0] [2 0] [3 0]]       ;; right
   [[0 0] [0 1] [0 2] [0 3]]       ;; up
   [[0 0] [0 -1] [0 -2] [0 -3]]    ;; down
   [[0 0] [-1 0] [-2 0] [-3 0]]    ;; left
   [[0 0] [1 1] [2 2] [3 3]]       ;; up-right
   [[0 0] [1 -1] [2 -2] [3 -3]]    ;; down-right
   [[0 0] [-1 1] [-2 2] [-3 3]]    ;; up-left
   [[0 0] [-1 -1] [-2 -2] [-3 -3]] ;; down-left
   ])

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
  "Returns the set of coordinate positions in the grid that match
   the provided character value"
  [ch {:keys [grid]}]
  (->> grid
       (filter #(= ch (val %)))
       keys
       set))

(def x-es (partial char-positions \X))
(def a-es (partial char-positions \A))

(defn search-coords
  "For a given starting position `pos`, return a collection of coordinate seqs
   that follow a given `search-pattern`"
  [search-pattern pos]
  (map (fn [ds]
         (map #(v/vec-add pos %) ds)) search-pattern))

(defn matches?
  "Returns true if the values of `coords` in `grid` match `pattern`"
  [pattern grid coords]
  (= pattern (map #(value grid %) coords)))

(def is-xmas? (partial matches? [\X \M \A \S]))
(def is-x-mas? (partial matches? [\M \A \S \M \A \S]))

(defn pattern-coords
  "Given:
    a collection of starting points `start-pts`, 
    a collection of `deltas` that represent a search pattern of adjacent coordinates
    a predication `matcher` that assesses whether the coordinate match the desired pattern,
    and the `input` grid,
   Returns a set of coordinate sequences that match the pattern."
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