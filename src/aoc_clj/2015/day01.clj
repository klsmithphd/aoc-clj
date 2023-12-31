(ns aoc-clj.2015.day01
  "Solution to https://adventofcode.com/2015/day/1"
  (:require [aoc-clj.utils.core :as u]))

(def parse first)

(defn instructions
  [input]
  (map {\( 1 \) -1} input))

(defn final-floor
  [input]
  (->> (instructions input)
       (reduce + 0)))

(defn first-pos-in-basement
  [input]
  (->> (instructions input)
       (reductions + 0)
       (u/index-of (u/equals? -1))))

(defn day01-part1-soln
  [input]
  (final-floor input))

(defn day01-part2-soln
  [input]
  (first-pos-in-basement input))

