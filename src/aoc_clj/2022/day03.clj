(ns aoc-clj.2022.day03
  (:require [clojure.set :as set]
            [aoc-clj.utils.core :as u]))

(def day03-input (u/puzzle-input "2022/day03-input.txt"))

(defn split-halfway
  [line]
  (split-at (/ (count line) 2) line))

(defn overlap
  [coll]
  (apply set/intersection (map set coll)))

(defn split
  [type coll]
  (if (= :thirds type)
    (partition 3 coll)
    (map split-halfway coll)))

(defn overlaps
  ([input]
   (overlaps input :halfway))
  ([input type]
   (->> input
        (split type)
        (map overlap)
        (map seq)
        flatten)))

(defn priority
  [c]
  (if (Character/isUpperCase c)
    (+ 27 (- (int c) (int \A)))
    (+  1 (- (int c) (int \a)))))

(defn overlaps-priority
  ([input]
   (overlaps-priority input :halfway))
  ([input type]
   (->> (overlaps input type)
        (map priority)
        (reduce +))))

(defn day03-part1-soln
  []
  (overlaps-priority day03-input))

(defn day03-part2-soln
  []
  (overlaps-priority day03-input :thirds))
