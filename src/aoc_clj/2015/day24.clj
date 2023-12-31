(ns aoc-clj.2015.day24
  "Solution to https://adventofcode.com/2015/day/24"
  (:require [clojure.math.combinatorics :as combo]))

(defn parse
  [input]
  (map read-string input))

(defn smallest-groups
  [groups input]
  (let [target (/ (reduce + input) groups)]
    (loop [size 1 partitions []]
      (if (seq partitions)
        partitions
        (recur (inc size) (filter #(= target (reduce + %)) (combo/combinations input size)))))))

(defn best-quantum-entanglement
  [partitions]
  (apply min (map (partial reduce *) partitions)))

(defn best-qe-thirds
  [input]
  (->> (smallest-groups 3 input)
       best-quantum-entanglement))

(defn best-qe-fourths
  [input]
  (->> (smallest-groups 4 input)
       best-quantum-entanglement))

(defn day24-part1-soln
  [input]
  (best-qe-thirds input))

(defn day24-part2-soln
  [input]
  (best-qe-fourths input))