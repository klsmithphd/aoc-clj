(ns aoc-clj.2015.day11
  "Solution to https://adventofcode.com/2015/day/11"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Constants
(def a-val 97)
(def rightmost-index
  "The passwords are all 8 chars long, so the rightmost index is 7"
  7)
(def x 23)
(def y 24)
(def z 25)
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

(defn nums-fn
  "Converts the `password` string to the numeric representation,
   applies `f` to the numeric representation, and converts back to the string
   representation"
  [f password]
  (-> password str->nums f nums->str))

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

(defn has-a-pair?
  "Is there a matching pair among the first 3 characters?"
  [[d0 d1 d2]]
  (or (= d0 d1) (= d1 d2)))

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

(defn next-wo-disallowed-chars
  "Returns the next password number sequence that doesn't contain
   any disallowed characters."
  [nums]
  (if-let [index (u/index-of disallowed nums)]
    (into [] (concat (take index nums)
                     [(inc (nth nums index))]
                     (repeat (- 7 index) 0)))
    nums))

(defn next-aabcc
  "Returns the next eight-digit sequence that has an `aabcc` pattern in 
   the lowest digits, i.e. a pattern that consists of two pairs sandwiching
   a 3-digit increasing straight"
  [[d0 d1 d2 d3 d4 d5 d6 d7 :as nums]]
  ;; Check whether the last five chars are past xxyzz
  (case (compare [d3 d4 d5 d6 d7] [x x y z z])
    ;; If past xxyzz, the next option is aabcc, with the char before inc'd
    1  [d0 d1 (inc d2) 0 0 1 2 2]
    ;; If exactly xxyzz, then we hava a valid password
    0  nums
    ;; If less than xxyzz, then we can jump to the next aabcc option to try
    -1 (let [a (max d3 d4)
             b (inc a)
             c (inc b)]
         (if (= 1 (compare [d5 d6 d7] [b c c]))
           ;; If the last three digits are already past bcc, we need to bump a
           ;; and try the next option
           (next-aabcc [d0 d1 d2 b b 0 0 0])
           ;; Else, we can return the aabcc pattern
           [d0 d1 d2 a a b c c]))))

(defn increment-fast
  "Exploit the fact that if there's no triplet or pair in the first three chars,
   then we can jump past a lot of options to find the sequence aabcc, as this
   is sequence is required to meet the two distinct pairs and increasing
   straight requirement
   
   Other optimizations are possible, but this is the minimum one needed
   to make the solution not take forever."
  [nums]
  (let [new-nums (next-wo-disallowed-chars (increment nums))
        first-three (subvec new-nums 0 3)]
    (if (and (not (increasing-triplet? first-three))
             (not (has-a-pair? first-three)))
      (next-aabcc new-nums)
      new-nums)))

(defn next-password
  "Increment the password by exactly one character"
  [password]
  (nums-fn increment password))

(defn next-valid-password
  "Scans through password values until finding one that satisfies the criteria"
  [password]
  (->> (str->nums password)
       (iterate increment-fast)
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