(ns aoc-clj.2024.day07
  "Solution to https://adventofcode.com/2024/day/7")

;; Input parsing
(defn parse-line
  [line]
  (map read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn possible-values
  "Returns the possible result values that could be generated when
   evaluating arguments left-to-right and inserting either * or +
   between consecutive args"
  [[a b & others]]
  (if (empty? others)
    [(* a b) (+ a b)]
    (mapcat possible-values [(cons (* a b) others) (cons (+ a b) others)])))

(defn possibly-true-eqn?
  "Determines whether the equation could possibly be true when
   inserting operators between arguments"
  [[result & args]]
  (some #(= result %) (possible-values args)))

(defn possibly-true-eqns
  "Returns a filtered coll of only the possibly true equations"
  [eqns]
  (filter possibly-true-eqn? eqns))

(defn possibly-true-test-sums
  "Returns the sum of the test values of all the equations that could
   possibly be true"
  [eqns]
  (->> eqns
       possibly-true-eqns
       (map first)
       (reduce +)))

;; Puzzle solutions
(defn part1
  "Determine which equations could possibly be true. 
   What is their total calibration result?"
  [input]
  (possibly-true-test-sums input))