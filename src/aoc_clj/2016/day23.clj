(ns aoc-clj.2016.day23
  "Solution to https://adventofcode.com/2016/day/23"
  (:require [aoc-clj.utils.assembunny :as asmb]))

;; Constants
(def part1-value 7)
(def part2-value 12)

;; Input parsing
(def parse asmb/parse)

;; Puzzle solutions
(defn part1
  "What value is in register a after executing the code?"
  [input]
  (:a (asmb/execute (assoc asmb/init-state :a part1-value) input)))

(defn part2
  "What value is in register a after executing the code?"
  [input]
  (:a (asmb/execute (assoc asmb/init-state :a part2-value) input)))


