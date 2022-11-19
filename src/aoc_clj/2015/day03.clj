(ns aoc-clj.2015.day03
  (:require [aoc-clj.utils.core :as u]))

(def day03-input
  (first (u/puzzle-input "2015/day03-input.txt")))

(def dir-map
  {\^ [0 -1]
   \v [0 1]
   \> [1 0]
   \< [-1 0]})

(defn vec-add [a b]
  (map + a b))

(defn houses-visited
  [input]
  (->> (map dir-map input)
       (reductions vec-add [0 0])
       set
       count))

(defn split-houses-visited
  [input]
  (let [steps (map dir-map input)
        steps1 (take-nth 2 steps)
        steps2 (take-nth 2 (rest steps))
        houses1 (reductions vec-add [0 0] steps1)
        houses2 (reductions vec-add [0 0] steps2)]
    (->> (concat houses1 houses2)
         set
         count)))

(defn day03-part1-soln
  []
  (houses-visited day03-input))

(defn day03-part2-soln
  []
  (split-houses-visited day03-input))