(ns aoc-clj.2022.day08
  "Solution to https://adventofcode.com/2022/day/8"
  (:require [aoc-clj.utils.grid.vecgrid :as vecgrid
             :refer [->VecGrid2D width height value slice]]
            [aoc-clj.utils.core :as u]))

(def d08-s01
  ["30373"
   "25512"
   "65332"
   "33549"
   "35390"])

(->VecGrid2D d08-s01)

(defn something
  [grid]
  (let [indices (for [j (range 1 (dec (height grid)))
                      i (range 1 (dec (width grid)))])]))

;; (def day08-input (u/puzzle-input "2022/day08-input.txt"))