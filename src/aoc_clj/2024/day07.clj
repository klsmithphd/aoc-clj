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
(defn concatenate
  [a b]
  (read-string (str a b)))

(defn possible-values-p1
  "Returns the possible result values that could be generated when
   evaluating arguments left-to-right and inserting either * or +
   between consecutive args"
  [[a b & others]]
  (if (empty? others)
    [(* a b) (+ a b)]
    (mapcat possible-values-p1 [(cons (* a b) others)
                                (cons (+ a b) others)])))

(defn possible-values-p2
  "Returns the possible result values that could be generated when
   evaluating arguments left-to-right and inserting one of *, +, or ||
   between consecutive args"
  [[a b & others]]
  (if (empty? others)
    [(* a b) (+ a b) (concatenate a b)]
    (mapcat possible-values-p2 [(cons (* a b) others)
                                (cons (+ a b) others)
                                (cons (concatenate a b) others)])))

(defn possibly-true-eqn?
  "Determines whether the equation could possibly be true when
   inserting operators between arguments"
  [part [result & args]]
  (let [poss-values (case part
                      :part1 possible-values-p1
                      :part2 possible-values-p2)]
    (some #(= result %) (poss-values args))))

(defn possibly-true-eqns
  "Returns a filtered coll of only the possibly true equations"
  [part eqns]
  (filter #(possibly-true-eqn? part %) eqns))

(defn possibly-true-test-sums
  "Returns the sum of the test values of all the equations that could
   possibly be true"
  [part eqns]
  (->> eqns
       (possibly-true-eqns part)
       (map first)
       (reduce +)))

;; Puzzle solutions
(defn part1
  "Determine which equations could possibly be true. 
   What is their total calibration result?"
  [input]
  (possibly-true-test-sums :part1 input))

(defn part2
  "Using your new knowledge of elephant hiding spots, determine which 
   equations could possibly be true. What is their total calibration result?"
  [input]
  (possibly-true-test-sums :part2 input))