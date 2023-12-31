(ns aoc-clj.2015.day05
  (:require [aoc-clj.utils.core :as u]))

(def day05-input (u/puzzle-input "inputs/2015/day05-input.txt"))

(defn three-vowels?
  [s]
  (>= (count (re-seq #"[aeiou]" s)) 3))

(defn repeated-char?
  [s]
  (some? (re-find #"(\w)\1" s)))

(defn no-invalid-pairs?
  [s]
  (nil? (re-find #"ab|cd|pq|xy" s)))

(def nice?
  (every-pred
   three-vowels?
   repeated-char?
   no-invalid-pairs?))

(defn non-overlapping-pair?
  [s]
  (some? (re-find #"(\w\w).*\1" s)))

(defn repeat-with-letter-between?
  [s]
  (some? (re-find #"(\w)\w\1" s)))

(def new-nice?
  (every-pred
   non-overlapping-pair?
   repeat-with-letter-between?))

(defn day05-part1-soln
  []
  (count (filter nice? day05-input)))

(defn day05-part2-soln
  []
  (count (filter new-nice? day05-input)))