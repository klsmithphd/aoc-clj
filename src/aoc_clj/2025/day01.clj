(ns aoc-clj.2025.day01
  "Solution to https://adventofcode.com/2025/day/1"
  (:require [clojure.string :as str]
            [aoc-clj.utils.math :as math]))

;; Constants
(def init-pos 50)
(def dial-size 100)

;; Input parsing
(defn parse-line
  [line]
  (let [num (read-string (subs line 1))]
    (if (str/starts-with? line "L")
      (- num)
      num)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn dial-positions
  "A collection of all of the dial positions after applying each turn"
  [turns]
  (reductions (partial math/mod-add dial-size) init-pos turns))

(defn zero-count
  "A count of all of the times that the dial was at position 0"
  [turns]
  (count (filter zero? (dial-positions turns))))

(defn turn->clicks
  "Converts a turn into a sequence of the individual click movements, i.e.,
   a sequence of 1 or -1, repeated `turn` times"
  [turn]
  (if (neg? turn)
    (repeat (- turn) -1)
    (repeat turn 1)))

(defn clicks
  "Converts all the provided turns into a sequence of individual click movements
   (i.e. turns with distance 1)"
  [turns]
  (mapcat turn->clicks turns))

(defn zero-click-count
  "A count of all the times the dial ever passed the zero position"
  [turns]
  (zero-count (clicks turns)))

;; Puzzle solutions
(defn part1
  "What's the actual password to open the door?"
  [input]
  (zero-count input))

(defn part2
  "Using password method 0x434C49434B, what is the password to open the door?"
  [input]
  (zero-click-count input))