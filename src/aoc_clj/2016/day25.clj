(ns aoc-clj.2016.day25
  "Solution to https://adventofcode.com/2016/day/25"
  (:require [aoc-clj.utils.assembunny :as asmb]))

;; Input parsing
;; We take all but the last value of the program because the final command
;; will result in an infinite loop. We only need to test one loop through.
(def parse (comp asmb/parse butlast))

;; Puzzle logic
(defn is-square-wave?
  "Determines whether the provided finite sequence is a square wave consisting
   of repeating 0, 1, 0, 1, 0, 1, etc."
  [s]
  (let [len (count s)]
    (= s (take len (cycle [0 1])))))

(defn valid-input?
  "Executes the assembunny program against the provided input and returns
   true if the input will produce the correct clock signal"
  [program input]
  (let [{:keys [a out]} (asmb/execute (assoc asmb/init-state :a input) program)]
    (and (zero? a) (is-square-wave? out))))

(defn first-valid-input
  "Returns the first positive integer input to the assembunny program that will
   result in the clock signal"
  [program]
  (->> (range)
       (filter #(valid-input? program %))
       first))

;; Puzzle solutions
(defn part1
  "Lowest positive integer to initialize register a and produce a clock signal"
  [input]
  (first-valid-input input))