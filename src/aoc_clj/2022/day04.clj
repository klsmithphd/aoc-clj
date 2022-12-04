(ns aoc-clj.2022.day04
  "Solution to https://adventofcode.com/2022/day/4"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

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

(def day04-input (parse (u/puzzle-input "2022/day04-input.txt")))

(defn fully-contained?
  "Return true if one of the ranges (determined by inclusive left/right bounds)
   is fully contained within the other"
  [[[l1 r1] [l2 r2]]]
  (or
   ;;     l1-r1
   ;; l2----------r2
   (<= l2 l1 r1 r2)
   ;; l1----------r1
   ;;     l2--r2   
   (<= l1 l2 r2 r1)))

(defn overlap?
  "Return true if one of the ranges (determined by inclusive left/right bounds)
   overlaps with the other"
  [[[l1 r1] [l2 r2]]]
  (or
   ;; l1-----r1
   ;;     l2------r2
   (<= l1 l2 r1 r2)
   ;;       l1-----r1
   ;; l2------r2
   (<= l2 l1 r2 r1)
   ;;     l1-r1
   ;; l2----------r2
   (<= l2 l1 r1 r2)
   ;; l1----------r1
   ;;     l2--r2   
   (<= l1 l2 r2 r1)))

(defn day04-part1-soln
  "In how many assignment pairs does one range fully contain the other?"
  []
  (count (filter fully-contained? day04-input)))

(defn day04-part2-soln
  "In how many assignment pairs do the ranges overlap?"
  []
  (count (filter overlap? day04-input)))