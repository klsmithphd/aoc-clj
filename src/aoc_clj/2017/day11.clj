(ns aoc-clj.2017.day11
  "Solution to https://adventofcode.com/2017/day/11"
  (:require [clojure.string :as str]
            [aoc-clj.utils.vectors :as v]))

;; Input parsing
(defn parse
  [input]
  (str/split (first input) #","))

;; Puzzle logic
(def cube-coords
  "Mapping between move directions and Cube coordinates.
   
   See https://www.redblobgames.com/grids/hexagons/#coordinates"
  {"n" [0 -1 1]
   "s" [0 1 -1]
   "ne" [1 -1 0]
   "se" [1 0 -1]
   "nw" [-1 0 1]
   "sw" [-1 1 0]})

(defn hex-distance
  "Move distance away from origin
   
   See https://www.redblobgames.com/grids/hexagons/#distances"
  [moves]
  (->> (map cube-coords moves)
       (reduce v/vec-add)
       (map abs)
       (apply max)))

(defn max-hex-distance
  "Finds the maximum distance away from the origin ever reached
   in the sequence of moves"
  [moves]
  (->> (map cube-coords moves)
       (reductions v/vec-add)
       (map #(apply max (map abs %)))
       (apply max)))

;; Puzzle solutions
(defn part1
  "How many steps away is the child process?"
  [input]
  (hex-distance input))

(defn part2
  "How many steps away is the furthest he ever got?"
  [input]
  (max-hex-distance input))