(ns aoc-clj.2024.day03
  "Solution to https://adventofcode.com/2024/day/3")

;; Input parsing
(def parse first)

;; Puzzle logic
(defn real-mul-insts
  "Finds the *real* mul instructions in a given string"
  [s]
  (re-seq #"mul\(\d{1,3},\d{1,3}\)" s))