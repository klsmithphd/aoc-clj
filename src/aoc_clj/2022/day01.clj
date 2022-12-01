(ns aoc-clj.2022.day01
  (:require [aoc-clj.utils.core :as u]))

(defn parse-segment
  [segment]
  (map read-string segment))

(defn parse
  [input]
  (->> input u/split-at-blankline (map parse-segment)))

(def day01-input (parse (u/puzzle-input "2022/day01-input.txt")))

(defn sorted-sums
  [calorie-counts]
  (sort > (map #(reduce + %) calorie-counts)))

(defn max-capacity
  [input]
  (first (sorted-sums input)))

(defn day01-part1-soln
  "Find the Elf carrying the most Calories. 
   How many total Calories is that Elf carrying?"
  []
  (max-capacity day01-input))

(defn top-three-max-capacity
  [input]
  (reduce + (take 3 (sorted-sums input))))

(defn day01-part2-soln
  "Find the top three Elves carrying the most Calories. 
   How many Calories are those Elves carrying in total?"
  []
  (top-three-max-capacity day01-input))
