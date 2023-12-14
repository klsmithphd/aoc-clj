(ns aoc-clj.2023.day14
  (:require [aoc-clj.utils.core :as u]))

(def parse identity)

(defn str-transpose
  [rows]
  (mapv (partial apply str) (u/transpose rows)))

(defn rev-compare
  [a b]
  (compare b a))

(defn roll-north-col
  [col]
  (->> col
       (partition-by #{\#})
       (map #(sort rev-compare %))
       flatten
       (apply str)))

(defn roll-north
  [rows]
  (let [cols (str-transpose rows)]
    (str-transpose (mapv roll-north-col cols))))


(defn total-load
  [rows]
  (let [height (count rows)]
    (reduce + (map-indexed (fn [idx row]
                             (* (- height idx)
                                (count (filter #{\O} row)))) rows))))


(defn day14-part1-soln
  [input]
  (total-load (roll-north input)))