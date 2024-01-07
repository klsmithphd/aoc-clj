(ns aoc-clj.2015.day10
  "Solution to https://adventofcode.com/2015/day/10"
  (:require [clojure.string :as str]))

;; Input parsing
(def parse first)

;; Puzzle logic
(defn look-and-say
  "Implements the Look-and-say sequence
   https://en.wikipedia.org/wiki/Look-and-say_sequence"
  [s]
  (let [chunks (partition-by identity s)
        counts (map #(vector (str (count %)) (str (first %))) chunks)]
    (str/join (flatten counts))))

(defn nth-length
  "Starting from `seed`, run the look_and_say fn `n` times"
  [n seed]
  (count (nth (iterate look-and-say seed) n)))

;; Puzzle solutions
(defn part1
  "Length of string after 40 iterations given the puzzle input seed"
  [input]
  (nth-length 40 input))

(defn part2
  "Length of the string after 50 iterations given the puzzle input seed"
  [input]
  (nth-length 50 input))