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

(defn part1
  "Find the Elf carrying the most Calories. 
   How many total Calories is that Elf carrying?"
  [input]
  (top-n-capacity-sum 1 input))

(defn part2
  "Find the top three Elves carrying the most Calories. 
   How many Calories are those Elves carrying in total?"
  [input]
  (top-n-capacity-sum 3 input))
