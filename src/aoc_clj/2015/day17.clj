(ns aoc-clj.2015.day17
  "Solution to https://adventofcode.com/2015/day/17"
  (:require [clojure.math.combinatorics :as combo]))

(defn parse
  [input]
  (map read-string input))

(defn sums-to?
  [sum coll]
  (= sum (reduce + (map second coll))))

(defn combinations
  [sum containers]
  (->> (map-indexed vector containers)
       (combo/subsets)
       (filter (partial sums-to? sum))
       (map (partial map  second))))

(defn minimal-combinations
  [sum containers]
  (let [combos (combinations sum containers)
        min-containers (apply min (map count combos))]
    (filter #(= min-containers (count %)) combos)))

(defn day17-part1-soln
  [input]
  (count (combinations 150 input)))

(defn day17-part2-soln
  [input]
  (count (minimal-combinations 150 input)))