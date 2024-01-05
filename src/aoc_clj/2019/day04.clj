(ns aoc-clj.2019.day04
  "Solution to https://adventofcode.com/2019/day/4")

(defn parse
  [input]
  (map read-string (re-seq #"\d+" (first input))))

(def my-any? (complement not-any?))

(defn digits
  "Converts an integer into a seq of its digits in order"
  [num]
  (map (comp read-string str) (str num)))

(defn not-decreasing-digits?
  [digits]
  (every? (complement neg?) (map - (rest digits) digits)))

(defn one-matching-pair?
  [digits]
  (my-any? #(>= % 2) (vals (frequencies digits))))

(def all-conds-part1?
  (every-pred not-decreasing-digits? one-matching-pair?))

(defn satisfactory-numbers
  [numbers condition]
  (->> numbers
       (map digits)
       (filter condition)))

(defn part1
  [input]
  (count (satisfactory-numbers (apply range input) all-conds-part1?)))

(defn pair-not-in-larger-group?
  [digits]
  (my-any? #(= % 2) (vals (frequencies digits))))

(def all-conds-part2?
  (every-pred not-decreasing-digits? pair-not-in-larger-group?))

(defn part2
  [input]
  (count (satisfactory-numbers (apply range input) all-conds-part2?)))