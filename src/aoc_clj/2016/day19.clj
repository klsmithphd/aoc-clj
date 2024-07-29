(ns aoc-clj.2016.day19
  "Solution to https://adventofcode.com/2016/day/18" 
  (:require [clojure.math :as math]))

;; Input parsing
(defn parse
  [input]
  (read-string (first input)))

;; Puzzle logic
(defn part1-winning-elf
  "Determines which elf ends up winning (with all the presents) using
   the stealing logic from part1"
  [n-elves]
  ;; Working out the sequence by hand, it becomes apparent that 
  ;; whenever the number of elves is equal to a power of two, it's
  ;; the elf in the first position (1) that wins. For every number
  ;; of elves greater than a power of two (but less than the next power),
  ;; the winning elf is always the next odd number counting up from 1.
  ;;
  ;; In other words, if n_elves = 2^i + x (where x < 2^i),
  ;; the winner is (2*i + 1)
  (let [power-of-two  (math/floor (/ (math/log n-elves) (math/log 2)))
        nearest-power (int (math/pow 2 power-of-two))
        rem           (- n-elves nearest-power)]
    (+ (* 2 rem) 1)))

;; Puzzle solutions
(defn part1
  "Which elf gets all the presents?"
  [input]
  (part1-winning-elf input))