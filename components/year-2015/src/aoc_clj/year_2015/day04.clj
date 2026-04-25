(ns aoc-clj.year-2015.day04
  "Solution to https://adventofcode.com/2015/day/4"
  (:require [aoc-clj.digest.interface :as d]))

;; Input parsing
(def parse first)

;; Puzzle logic
(defn first-integer
  "The first integer that satifies the supplied `pred` given the `secret`"
  [pred secret]
  (d/find-first-int #(pred (d/md5-digest (str secret %)))))

(def first-five-zero-int (partial first-integer d/five-zero-start?))
(def first-six-zero-int (partial first-integer d/six-zero-start?))

;; Puzzle solutions
(defn part1
  "The first integer whose MD5 hash in hex starts with five zeroes"
  [input]
  (first-five-zero-int input))

(defn part2
  "The first integer whose MD5 hash in hex starts with six zeros"
  [input]
  (first-six-zero-int input))