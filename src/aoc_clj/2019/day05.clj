(ns aoc-clj.2019.day05
  "Solution to https://adventofcode.com/2019/day/5"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

(def parse u/firstv)

(defn day05-part1-soln
  [input]
  (intcode/last-out (intcode/intcode-exec input [1])))

(defn day05-part2-soln
  [input]
  (intcode/last-out (intcode/intcode-exec input [5])))