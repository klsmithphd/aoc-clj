(ns aoc-clj.2018.day05
  "Solution to https://adventofcode.com/2018/day/5"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Constants
(def lowers
  "All lowercase characters"
  (map char (range 97 123)))

(def uppers
  "All uppercase characters"
  (map char (range 65 91)))

(def pair-patterns
  "A collection of regex patterns that will match all mixed case instances of
   A's, B's, C's, etc."
  (map #(re-pattern (str %1 "|" %2)) lowers uppers))

(def any-pair-pattern
  "A regex pattern that will match any adjacent mixed-case letter pairs"
  (->> (map #(str %1 %2 \| %2 %1) lowers uppers)
       (str/join "|")
       re-pattern))

;; Input parsing
(def parse first)

;; Puzzle logic
(defn collapsed
  "Returns a new string with any adjacent lowercase-uppercase letter pairs
   removed"
  [s]
  (str/replace s any-pair-pattern ""))

(defn fully-collapsed
  "Returns a new string after iteratively removing adjacent mixed-case 
   letter pairs until nothing changes"
  [s]
  (last (u/converge collapsed s)))

(defn fully-collapsed-count
  "Returns the length of the string after fully collapsing it"
  [s]
  (count (fully-collapsed s)))

(defn polymers-without-units
  "Returns a seq of new starting polymers with each of the possible unit
   types removed"
  [s]
  (map #(str/replace s % "") pair-patterns))

(defn shortest-polymer
  "Returns the length of the shortest possible polymer after trying
   to remove any problematic unit types"
  [s]
  (->> (polymers-without-units s)
       (map fully-collapsed-count)
       (apply min)))

;; Puzzle solutions
(defn part1
  "How many units remain after fully reacting the polymer you scanned?"
  [input]
  (fully-collapsed-count input))

(defn part2
  "What is the length of the shortest polymer you can produce by removing 
   all units of exactly one type and fully reacting the result?"
  [input]
  (shortest-polymer input))