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
  [stone]
  (if (zero? stone)
    [1]
    (let [digits (count (str stone))
          div    (long (math/pow 10 (/ digits 2)))]
      (if (even? digits)
        [(quot stone div) (mod stone div)]
        [(* 2024 stone)]))))

(defn blink
  [stones]
  (mapcat stone-update stones))

(defn stones-after-n-blinks
  [n stones]
  (->> stones
       (iterate blink)
       (drop n)
       first
       count))

;; Puzzle solutions
(defn part1
  [input]
  (stones-after-n-blinks part1-blinks input))

(defn part2
  [input]
  (stones-after-n-blinks part2-blinks input))