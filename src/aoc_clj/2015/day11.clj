(ns aoc-clj.2015.day11
  "Solution to https://adventofcode.com/2015/day/11"
  (:require [clojure.string :as str]))

;; Constants
(def a-val 97)
(def rightmost-index
  "The passwords are all 8 chars long, so the rightmost index is 7"
  7)
(def alphabet-length 26)

;; Input parsing
(def parse first)

;; Puzzle logic
(defn char->int
  "Converts a character to its ASCII integer value"
  [c]
  (- (int c) a-val))

(defn int->char
  "Convers an ASCII integer value to its character equivalent"
  [i]
  (char (+ i a-val)))

(defn str->nums
  "Converts a string to a vec of integer values"
  [s]
  (mapv char->int s))

(defn nums->str
  "Converts a string of integer values into a string"
  [nums]
  (str/join (map int->char nums)))

(defn increasing-triplet?
  "Whether the three values are consecutively increasing"
  [[a b c]]
  (and (= (- b a) 1)
       (= (- c b) 1)))

(defn increasing-straight?
  "Whether the numbers contain any consecutively increasing triplets"
  [nums]
  (let [triplets (partition 3 1 nums)]
    (some increasing-triplet? triplets)))

(defn matching-pair?
  "Whether the pair matches"
  [[a b]]
  (= a b))

(defn two-distinct-pairs?
  "Are there at least two distinct matching pairs in the set"
  [nums]
  (let [pairs (partition 2 1 nums)
        matches (filter matching-pair? pairs)]
    (>= (count (distinct matches)) 2)))

(def disallowed (set (str->nums "iol")))
(defn no-disallowed?
  "Whether any disallowed numbers are included"
  [nums]
  (not (some disallowed nums)))

(def valid-password?
  "A valid password must satisfy each of the three predicates"
  (every-pred
   increasing-straight?
   no-disallowed?
   two-distinct-pairs?))

(defn increment
  "Given the list of numbers representing the password, return the next
   value"
  [password]
  (loop [index rightmost-index nums password]
    (if (< (inc (nth nums index)) alphabet-length)
      ;; If incrementing the current index stays under 26 (z),
      ;; just increase it and return it
      (update nums index inc)
      ;; Otherwise, we need to record a zero in the current place
      ;; and proceed with increment the next lowest index
      (recur (dec index)
             (assoc nums index 0)))))

(defn next-password
  "Increment the password by exactly one character"
  [password]
  (-> password str->nums increment nums->str))

(defn next-valid-password
  "Scans through password values until finding one that satisfies the criteria"
  [password]
  (->> (str->nums password)
       (iterate increment)
       (filter valid-password?)
       first
       nums->str))

;; Puzzle solutions
(defn part1
  "Finds the next valid password after the one provided as input"
  [input]
  (next-valid-password input))

(defn part2
  "Finds the second next valid password after the one provided as input"
  [input]
  (-> (part1 input)
      next-password
      next-valid-password))