(ns aoc-clj.2022.day03
  "Solution to https://adventofcode.com/2022/day/3"
  (:require [clojure.set :as set]
            [aoc-clj.utils.core :as u]))

;;;; Input parsing
(def parse identity)
(def day03-input (u/parse-puzzle-input parse 2022 3))

;;;; Puzzle logic

(defn split-halfway
  "Divide a sequence `s` into a vector of two sequences, cut at the midpoint"
  [s]
  (split-at (/ (count s) 2) s))

(defn split
  "Divide a collection `coll` into sub-partitions based on `type`.
     ::thirds will group together every three items
     ::halfway will split every item into two at its midpoint"
  [type coll]
  (case type
    ::thirds  (partition 3 coll)
    ::halfway (map split-halfway coll)))

(defn overlap
  "For a collection of seqs `coll`, find the value that all seqs have 
   in common"
  [coll]
  (first (apply set/intersection (map set coll))))

(defn overlaps
  "For the given `split-type` (either `::halfway` or `::thirds`),
   find the overlapping value within the partitions"
  [split-type input]
  (map overlap (split split-type input)))

(defn priority
  "Lowercase item types `a` through `z` have priorities 1 through 26.
   Uppercase item types `A` through `Z` have priorities 27 through 52."
  [c]
  (if (Character/isUpperCase c)
    (+ 27 (- (int c) (int \A)))
    (+  1 (- (int c) (int \a)))))

(defn overlap-priority-sum
  "Using the item to priority value mapping, compute the sum of the
   priorities for the given `split-type` (either `::halfway` or `::thirds`)
   for the given `input`"
  [split-type input]
  (->> (overlaps split-type input)
       (map priority)
       (reduce +)))

;;;; Puzzle solutions

(defn day03-part1-soln
  "Find the item type that appears in both compartments of each rucksack. 
   What is the sum of the priorities of those item types?"
  []
  (overlap-priority-sum ::halfway day03-input))

(defn day03-part2-soln
  "Find the item type that corresponds to the badges of each three-Elf group. 
   What is the sum of the priorities of those item types?"
  []
  (overlap-priority-sum ::thirds day03-input))
