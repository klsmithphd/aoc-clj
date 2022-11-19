(ns aoc-clj.2015.day24
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.core :as u]))

(def day24-input (map read-string (u/puzzle-input "2015/day24-input.txt")))

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
  []
  (best-qe-thirds day24-input))

(defn day24-part2-soln
  []
  (best-qe-fourths day24-input))