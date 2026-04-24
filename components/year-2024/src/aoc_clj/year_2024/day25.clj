(ns aoc-clj.year-2024.day25
  "Solution to https://adventofcode.com/2024/day/25"
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.util.interface :as u]
            [aoc-clj.grid.interface :as vg]))

;; Input parsing
(def charmap {\# 1 \. 0})

(defn key-lock-parse
  [lines]
  [(case (ffirst lines)
     1 :locks
     0 :keys)
   (mapv #(reduce + -1 %) lines)])

(defn parse
  [input]
  (->> (u/split-at-blankline input)
       (map #(:v (vg/ascii->VecGrid2D charmap %)))
       (map u/transpose)
       (map key-lock-parse)
       (group-by first)
       (u/fmap #(map second %))))

;; Puzzle logic
(defn fit?
  "A lock and a key pair fit together if the number of pins
   on the key and the lock don't overlap"
  [[lock key]]
  (every? #(<= % 5) (map + lock key)))

(defn lock-key-fits
  "Returns only lock-key pairs that fit together"
  [{:keys [locks keys]}]
  (->> (combo/cartesian-product locks keys)
       (filter fit?)))

(defn lock-key-fits-count
  "Returns the count of lock/key pairs that fit together"
  [input]
  (count (lock-key-fits input)))

;; Part 1 soln
(defn part1
  "How many unique lock/key pairs fit together without overlapping in any
   column?"
  [input]
  (lock-key-fits-count input))