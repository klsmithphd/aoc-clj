(ns aoc-clj.2019.day09
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

(def day09-input (u/firstv (u/puzzle-input "inputs/2019/day09-input.txt")))

(defn day09-part1-soln
  []
  (intcode/last-out (intcode/intcode-exec day09-input [1])))

(defn day09-part2-soln
  []
  (intcode/last-out (intcode/intcode-exec day09-input [2])))