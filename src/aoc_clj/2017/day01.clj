(ns aoc-clj.2017.day01
  "Solution to https://adventofcode.com/2016/day/1")

;; Input parsing
(defn parse
  [input]
  (map (comp read-string str) (first input)))

;; Puzzle logic
(defn match-value
  [[a b]]
  (if (= a b)
    a
    0))

(defn wrap-around
  [digits]
  (conj digits (first digits)))

(defn sum-of-matching-digits
  [digits]
  (->> (wrap-around digits)
       (partition 2 1)
       (map match-value)
       (reduce +)))

;; Puzzle solutions
(defn part1
  [input]
  (sum-of-matching-digits input))