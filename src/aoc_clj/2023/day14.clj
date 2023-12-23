(ns aoc-clj.2023.day14
  (:require [aoc-clj.utils.core :as u]))

(def parse identity)

(defn rev-compare
  [a b]
  (compare b a))

(defn roll-row
  "Allows the round rocks denoted by \\O to roll in the direction dictated
   by `dir`, with 0 meaning rocks roll east (to the right) and any other
   value (typically 1) meaning roll west (to the left)"
  [dir row]
  (let [sort-fn (if (zero? dir) sort (partial sort rev-compare))]
    (->> row
         (partition-by #{\#})
         (map sort-fn)
         flatten)))

(def roll-east  #(map (partial roll-row 0) %))
(def roll-west  #(map (partial roll-row 1) %))
(def roll-south #(map (partial roll-row 0) %))
(def roll-north #(map (partial roll-row 1) %))

(defn roll-north-solo
  "Roll the round rocks toward the north (top) end of the grid"
  [input]
  (-> input u/transpose roll-north u/transpose))

(defn spin-cycle
  "Each cycle tilts the platform four times so that the rounded rocks roll 
   north, then west, then south, then east. After each tilt, 
   the rounded rocks roll as far as they can before the platform 
   tilts in the next direction."
  [rows]
  (->> rows
       u/transpose
       roll-north
       u/transpose
       roll-west
       u/transpose
       roll-south
       u/transpose
       roll-east))


(defn total-load
  "The amount of load caused by a single rounded rock (O) is equal to the 
   number of rows from the rock to the south edge of the platform, 
   including the row the rock is on.
   
   The total load is the sum of the load caused by all of the rounded rocks."
  [rows]
  (let [height (count rows)]
    (reduce + (map-indexed (fn [idx row]
                             (* (- height idx)
                                (count (filter #{\O} row)))) rows))))


(defn day14-part1-soln
  "Tilt the platform so that the rounded rocks all roll north. 
   Afterward, what is the total load on the north support beams?"
  [input]
  (total-load (roll-north input)))