(ns aoc-clj.2022.day04
  "Solution to https://adventofcode.com/2022/day/4"
  (:require [clojure.string :as str]
            [aoc-clj.utils.intervals :as ivs]))

;;;; Input parsing

(defn parse-range
  "Split range at the `-` character"
  [range]
  (mapv read-string (str/split range #"\-")))

(defn parse-line
  "Split pair at the `,` character"
  [line]
  (mapv parse-range (str/split line #",")))

(defn parse
  "Parse the input format across all lines"
  [input]
  (mapv parse-line input))

;;;; Puzzle solutions

(defn part1
  "In how many assignment pairs does one range fully contain the other?"
  [input]
  (count (filter #(apply ivs/fully-contained? %) input)))

(defn part2
  "In how many assignment pairs do the ranges overlap?"
  [input]
  (count (filter #(apply ivs/overlap? %) input)))