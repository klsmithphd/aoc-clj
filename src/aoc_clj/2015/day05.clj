(ns aoc-clj.2015.day05
  "Solution to https://adventofcode.com/2015/day/5")

(def parse identity)

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
  [input]
  (count (filter nice? input)))

(defn day05-part2-soln
  [input]
  (count (filter new-nice? input)))