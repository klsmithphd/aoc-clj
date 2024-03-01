(ns aoc-clj.2015.day24
  "Solution to https://adventofcode.com/2015/day/24"
  (:require [clojure.math.combinatorics :as combo]))

;; Input parsing
(defn parse
  [input]
  (map read-string input))

;; Puzzle logic
(defn smallest-passenger-packages
  "Returns the valid combination of package `weights` in the passenger 
   compartment that will balance the weight across all `n` compartments but 
   results in the smallest number of packages in the passenger compartment."
  [n weights]
  ;; We don't need to actually work out the distribution of packages across
  ;; the other compartments --- every compartment needs an equal weight, so
  ;; we just take the total weight, divide by the number of compartments,
  ;; and return the combinations of items for the first (passenger) compartment
  ;; that match the compartment weight target, starting with the fewest
  ;; items possible (two)
  (let [target (/ (reduce + weights) n)]
    (loop [size 1 partitions []]
      (if (seq partitions)
        partitions
        (recur
         (inc size)
         (filter #(= target (reduce + %)) (combo/combinations weights size)))))))

(defn best-quantum-entanglement
  "Finds the best (minimum) quantum entanglement, which is just the product
   of the packages' weights"
  [partitions]
  (->> (map (partial reduce *) partitions)
       (apply min)))

(defn ideal-quantum-entanglement
  "Returns the ideal quantum entanglement value for the packages divided
   into `n` compartments"
  [n weights]
  (->> (smallest-passenger-packages n weights)
       best-quantum-entanglement))

;; Puzzle solutions
(defn part1
  "Quantum entanglement of the packages in the passenger compartment for
   packages divided among 3 compartments"
  [input]
  (ideal-quantum-entanglement 3 input))

(defn part2
  "Quantum entanglement of the packages in the passenger compartment for
   packages divided among 4 compartments"
  [input]
  (ideal-quantum-entanglement 4 input))