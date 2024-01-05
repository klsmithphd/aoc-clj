(ns aoc-clj.2019.day09
  "Solution to https://adventofcode.com/2019/day/9"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

(def parse u/firstv)

(defn part1
  [input]
  (intcode/last-out (intcode/intcode-exec input [1])))

(defn part2
  [input]
  (intcode/last-out (intcode/intcode-exec input [2])))