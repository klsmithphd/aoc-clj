(ns aoc-clj.2015.day05
  "Solution to https://adventofcode.com/2015/day/5")

;; Input parsing
(def parse identity)

;; Puzzle logic
(defn three-vowels?
  "Does the string contain at least three vowels (of the set `aeiou`)?"
  [s]
  (>= (count (re-seq #"[aeiou]" s)) 3))

(defn repeated-char?
  "Does the string contain at least one repeated letter?"
  [s]
  (some? (re-find #"(\w)\1" s)))

(defn no-invalid-pairs?
  "Does the string not contain the strings `ab` `cd` `pq` or `xy`"
  [s]
  (nil? (re-find #"ab|cd|pq|xy" s)))

(def part1-nice?
  "A string is nice if it satifies the first three conditions"
  (every-pred
   three-vowels?
   repeated-char?
   no-invalid-pairs?))

(defn non-overlapping-pair?
  "Does the string contain a pair of any two letters that appears
   at least twice without overlapping"
  [s]
  (some? (re-find #"(\w\w).*\1" s)))

(defn repeat-with-letter-between?
  "Does the string contain one letter that repeats with exactly one other
   letter in between"
  [s]
  (some? (re-find #"(\w)\w\1" s)))

(def part2-nice?
  "A string is nice in part2 if it satisfies these two new rules"
  (every-pred
   non-overlapping-pair?
   repeat-with-letter-between?))

(defn nice-count
  [pred strings]
  (count (filter pred strings)))

;; Puzzle solutions
(defn part1
  "The number of input strings that are nice using the rules in part1"
  [input]
  (nice-count part1-nice? input))

(defn part2
  "The number of input strings that are nice using the rules in part2"
  [input]
  (nice-count part2-nice? input))