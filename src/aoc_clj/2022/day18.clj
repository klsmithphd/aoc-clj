(ns aoc-clj.2022.day18
  "Solution to https://adventofcode.com/2022/day/18"
  (:require [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (map u/str->vec input))

(def d18-s01
  [[1 1 1]
   [2 1 1]])

(def d18-s02
  [[2 2 2]
   [1 2 2]
   [3 2 2]
   [2 1 2]
   [2 3 2]
   [2 2 1]
   [2 2 3]
   [2 2 4]
   [2 2 6]
   [1 2 5]
   [3 2 5]
   [2 1 5]
   [2 3 5]])

(def day18-input (parse (u/puzzle-input "2022/day18-input.txt")))

(defn shared-face-count
  [cubes]
  (count (filter (set (grid/adj-coords-3d (first cubes))) (rest cubes))))

(defn surface-area
  "Count the number of sides of each cube that are not immediately 
   connected to another cube."
  [cubes]
  (let [faces (->> (u/rotations cubes)
                   (map shared-face-count)
                   (reduce +))]
    (- (* 6 (count cubes)) faces)))

(defn day18-part1-soln
  []
  (surface-area day18-input))