(ns aoc-clj.2022.day01
  "Solution to https://adventofcode.com/2022/day/1"
  (:require [aoc-clj.utils.core :as u]))

;;;; Input parsing

(defn parse-segment
  "Parse each string in the collection as a number"
  [segment]
  (map read-string segment))

(defn parse
  "Parse the day01 input"
  [input]
  (->> input u/split-at-blankline (map parse-segment)))

(def day01-input (u/parse-puzzle-input parse 2022 1))

;;;; Puzzle logic

(defn sorted-totals
  "Return a collection of all the `calorie` totals, sorted in descending order"
  [calories]
  (sort > (map #(reduce + %) calories)))

(defn top-n-capacity-sum
  "Return the sum of the top `n` totals"
  [n input]
  (reduce + (take n (sorted-totals input))))

;;;; Puzzle solutions

(defn day01-part1-soln
  "Find the Elf carrying the most Calories. 
   How many total Calories is that Elf carrying?"
  []
  (top-n-capacity-sum 1 day01-input))

(defn day01-part2-soln
  "Find the top three Elves carrying the most Calories. 
   How many Calories are those Elves carrying in total?"
  []
  (top-n-capacity-sum 3 day01-input))
