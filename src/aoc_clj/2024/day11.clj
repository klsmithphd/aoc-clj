(ns aoc-clj.2024.day11
  "Solution to https://adventofcode.com/2024/day/11"
  (:require [clojure.string :as str]
            [clojure.math :as math]))

;; Constants
(def part1-blinks 25)
(def part2-blinks 75)

;; Input parsing
(defn parse
  [input]
  (map read-string (str/split (first input) #" ")))

;; Puzzle logic
(defn stone-update
  "Updates a single stone, returning a collection of either 1 or 2 stones"
  [stone]
  (if (zero? stone)
    [1]
    (let [digits (count (str stone))
          div    (long (math/pow 10 (/ digits 2)))]
      (if (even? digits)
        [(quot stone div) (mod stone div)]
        [(* 2024 stone)]))))

(defn blink
  "Returns the new collection of stones after updating all of them"
  [stones]
  (mapcat stone-update stones))

(defn stones-after-n-blinks
  "Returns the number of stones present after n blinks"
  [n stones]
  (->> stones
       (iterate blink)
       (drop n)
       first
       count))

;; Puzzle solutions
(defn part1
  "How many stones will you have after blinking 25 times?"
  [input]
  (stones-after-n-blinks part1-blinks input))

(defn part2
  "How many stones would you have after blinking a total of 75 times?"
  [input]
  (stones-after-n-blinks part2-blinks input))


;; Some thoughts...
;; Every value has a completely deterministic collection of values that will 
;; be spawned from it. Many values will recur. So, if we start building up
;; a collection of how many values will be spawned from a given number up to a certain
;; depth, we should be able to build up dynamic-programming style the total count
;; 0 -> 1 -> 2024 -> 20 24 -> 2 0 2 4 -> 4048 1 4048 9096 -> 40 48 2024 40 48 90 96 -> 4 0 4 8 20 24 4 0 4 8 9 0 9 6