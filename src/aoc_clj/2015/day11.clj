(ns aoc-clj.2015.day11
  "Solution to https://adventofcode.com/2015/day/11"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

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

(defn nums-fn
  [f password]
  (-> password str->nums f nums->str))

;; New approach
;; Don't exhaustively search naively through single increments. Jump to the
;; next solution based on the rules.

;; First, increment the starting point by 1 just in case the input already
;; satisfies the rules (you're looking for the *next* valid password)

;; Then, check to see if any of the letters are disallowed. Increment the
;; leftmost disallowed number by 1, and set ever letter to the right of it
;; to 'a' (0). 

;; Now, does the leftmost 3 characters satisfy the triplet condition?
;; If so, we just need to find the next value that has two distinct pairs
;; in the last 4 character slots. If char[4] < char[5], we need to increment
;; to char[5] char[5] 0 0.  If char [4] >= char[5], we need to increment to
;; char[4] char[4] 0 0.

;; If the leftmost 3 chars don't contain a triplet, does it contain a pair?
;; If so, figure out the next possible sequence of aabc up to xxyz that is
;; reachable in the last four chars, such that aa or xx don't match the
;; already available pair.

;; Finally, if the leftmost 3 chars don't contain a triplet or a pair, figure
;; out to get to a sequence like aabcc in the last 5 places. This should
;; be determined by wheter char[3] < char[4] or char[3] >= char[4]

(defn next-wo-disallowed-chars
  "Returns the next password number sequence that doesn't contain
   any disallowed characters."
  [nums]
  (if-let [index (u/index-of disallowed nums)]
    (into [] (concat (take index nums)
                     [(inc (nth nums index))]
                     (repeat (- 7 index) 0)))
    nums))


(def next-aabcc identity)

(defn next-pairs
  "Returns the next sequence that has two distinct, non-overlapping pairs
   in the last four digits"
  [[d0 d1 d2 d3 d4 d5 d6 d7 :as nums]]
  (if (= d4 d5 d6 d7)
    (next-pairs (increment nums))
    (cond
      (< d4 d5) [d0 d1 d2 d3 d5 d5 0 0]
      (> d4 d5) [d0 d1 d2 d3 d4 d4 0 0]
      :else (if (<= d7 d6)
              [d0 d1 d2 d3 d4 d4 d6 d6]
              [d0 d1 d2 d3 d4 d4 d7 d7]))))

(defn has-a-pair?
  [[d0 d1 d2]]
  (or (= d0 d1) (= d1 d2)))

(defn next-trip
  [nums]
  nums)

(defn next-trip-pair
  "Returns the next sequence that has a triplet-pair sequence, either
   aabc or abcc"
  [[d0 d1 d2 d3 d4 d5 d6 d7 :as nums]]
  (case (compare [d4 d5 d6 d7] [23 23 24 25])
    1  [d0 d1 d2 (inc d3) 0 0 1 2]
    0  nums
    -1 (let [pair (if (= d0 d1) d0 d1)
             base (max d4 d5)]
         (if (= pair base)
           (next-trip-pair [d0 d1 d2 d3 (inc base) (inc base) 0 0])
           (if (and (<= d6 (+ base 1)) (<= d7 (+ base 2)))
             [d0 d1 d2 d3 base base (+ base 1) (+ base 2)]
             [d0 d1 d2 d3 (+ base 1) (+ base 1) (+ base 2) (+ base 3)])))))

(defn next-password-fast
  [nums]
  (let [pw (next-wo-disallowed-chars (increment nums))]
    (cond
      ;; If the first three digits are a triplet, we only need to find
      ;; the next distinct pairs
      (increasing-triplet? (subvec pw 0 3)) (next-pairs pw)
      ;; If the first four digits contain two pairs, only need to find the
      ;; next increasing triplet
      (two-distinct-pairs? (subvec pw 0 4)) (next-trip pw)
      ;; The first three digits contain a pair, we only need to find the
      ;; next pair-triplet sequence (aabc to xxyz)
      (has-a-pair? (subvec pw 0 3))         (next-trip-pair pw)
      ;; Finally, if all else fails, we need to find the next pair-triplet-pair
      ;; sequence (aabcc to xxyzz)
      :else                                 (next-aabcc pw))))

(compare [7 7 9 0] [7 7 9 0])

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