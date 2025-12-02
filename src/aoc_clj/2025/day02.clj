(ns aoc-clj.2025.day02
  "Solution to https://adventofcode.com/2025/day/2"
  (:require [clojure.string :as str]))

;; Input parsing
(defn parse-range
  [range]
  (->> (str/split range #"-")
       (mapv read-string)))

(defn parse
  [input]
  (->> (str/split (first input) #",")
       (map parse-range)))

;; Puzzle logic
(defn candidates
  "Returns all the values in a range (ends inclusive)"
  [[s e]]
  (range s (inc e)))

(defn part1-invalid?
  "Returns true if the number represents an invalid id, where invalid id consists
   only of some sequence of digits repeated twice"
  [num]
  (some? (re-matches #"^(\d+)(\1)$" (str num))))

(defn part2-invalid?
  "Returns true if the number is made only of some sequence of digits repeated
   at least twice"
  [num]
  (some? (re-matches #"^(\d+)(\1)+$" (str num))))

(defn all-invalid-ids
  "Returns all of the invalid IDs across the ranges given"
  [invalid? ranges]
  (->> (mapcat candidates ranges)
       (filter invalid?)))

(defn all-invalid-ids-sum
  "Computes the sum of all the invalid IDs in the ranges given"
  [invalid? ranges]
  (->> (all-invalid-ids invalid? ranges)
       (reduce +)))

;; Puzzle solutions
(defn part1
  "What do you get if you add up all of the invalid IDs?"
  [input]
  (all-invalid-ids-sum part1-invalid? input))

(defn part2
  "What do you get if you add up all of the invalid IDs using these new rules?"
  [input]
  (all-invalid-ids-sum part2-invalid? input))