(ns aoc-clj.2016.day12
  "Solution to https://adventofcode.com/2016/day/12"
  (:require [aoc-clj.utils.assembunny :as asmb]))

;; Constants
(def init-state {:a 0 :b 0 :c 0 :d 0 :inst 0})

;; Input parsing
(def parse asmb/parse)

;; Puzzle solutions
(defn part1
  "What value is in register a after executing the code?"
  [input]
  (:a (asmb/execute init-state input)))

(defn part2
  "What value is in register a after executing the code, with reg c initialized to 1?"
  [input]
  (:a (asmb/execute (assoc init-state :c 1) input)))

