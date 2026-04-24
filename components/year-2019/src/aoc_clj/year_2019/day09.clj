(ns aoc-clj.year-2019.day09
  "Solution to https://adventofcode.com/2019/day/9"
  (:require [aoc-clj.util.interface :as u]
            [aoc-clj.intcode.interface :as intcode]))

(def parse u/firstv)

(defn part1
  [input]
  (intcode/last-out (intcode/intcode-exec input [1])))

(defn part2
  [input]
  (intcode/last-out (intcode/intcode-exec input [2])))