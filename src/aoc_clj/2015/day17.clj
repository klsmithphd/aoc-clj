(ns aoc-clj.2015.day17
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.core :as u]))

(def day17-input (map read-string (u/puzzle-input "2015/day17-input.txt")))

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
  []
  (count (combinations 150 day17-input)))

(defn day17-part2-soln
  []
  (count (minimal-combinations 150 day17-input)))