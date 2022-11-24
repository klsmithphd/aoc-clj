(ns aoc-clj.2019.day05
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

(def day05-input (u/firstv (u/puzzle-input "2019/day05-input.txt")))

(defn day05-part1-soln
  []
  (intcode/last-out (intcode/intcode-exec day05-input [1] [])))

(defn day05-part2-soln
  []
  (intcode/last-out (intcode/intcode-exec day05-input [5] [])))