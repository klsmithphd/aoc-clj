(ns aoc-clj.2022.day06
  "Solution to https://adventofcode.com/2022/day/6"
  (:require [aoc-clj.utils.core :as u]))

(def day06-input (first (u/puzzle-input "2022/day06-input.txt")))

(defn chars-to-distinct-run
  "Returns the number of characters that need to be examined to
   find a run of distinct characters of length `len` in a string `s`
   Returns nil if no such run is found"
  [len s]
  (->> (partition len 1 s)
       (map #(apply distinct? %))
       (u/index-of true)
       (+ len)))

(defn day06-part1-soln
  "How many characters need to be processed before the 
   first start-of-packet marker is detected?"
  []
  (chars-to-distinct-run 4 day06-input))

(defn day06-part2-soln
  "How many characters need to be processed before the 
   first start-of-message marker is detected?"
  []
  (chars-to-distinct-run 14 day06-input))

