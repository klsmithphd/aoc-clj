(ns aoc-clj.2015.day09
  "Solution to https://adventofcode.com/2015/day/9"
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-line
  [line]
  (let [[a _ b _ c] (str/split line #" ")
        dist (read-string c)]
    [[a b dist]
     [b a dist]]))

(defn parse
  [input]
  (->> (mapcat parse-line input)
       (group-by first)
       (u/fmap #(into {} (map (fn [v] (subvec v 1)) %)))))

;; Puzzle logic
(defn route-distance
  "Computes the distance along a route denoted as a sequence of nodes"
  [dists route]
  (let [pairs (partition 2 1 route)
        steps (map (partial get-in dists) pairs)]
    (reduce + steps)))

(defn route-distances
  "Computes all possible route distances"
  [dists]
  (let [nodes (keys dists)
        routes (combo/permutations nodes)]
    (map (partial route-distance dists) routes)))

(defn shortest-route-distance
  "Finds the distance of the shortest route"
  [dists]
  (apply min (route-distances dists)))

(defn longest-route-distance
  "Finds the distance of the longest route"
  [dists]
  (apply max (route-distances dists)))

;; Puzzle solutions
(defn part1
  "Distance of shortest route visiting every location once"
  [input]
  (shortest-route-distance input))

(defn part2
  "Distance of longest route visiting every location once"
  [input]
  (longest-route-distance input))