(ns aoc-clj.2015.day01
  (:require [aoc-clj.utils.core :as u]))

(def day01-input (first (u/puzzle-input "2015/day01-input.txt")))

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
       (u/index-of -1)))

(defn day01-part1-soln
  []
  (final-floor day01-input))

(defn day01-part2-soln
  []
  (first-pos-in-basement day01-input))

