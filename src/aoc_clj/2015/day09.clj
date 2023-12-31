(ns aoc-clj.2015.day09
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [[a _ b _ c] (str/split line #" ")
        dist (read-string c)]
    [[a {b dist}]
     [b {a dist}]]))

(defn parse
  [input]
  (->> (mapcat parse-line input)
       (group-by first)
       (u/fmap #(apply merge (map second %)))))

(def day09-input (parse (u/puzzle-input "inputs/2015/day09-input.txt")))

(defn route-distance
  [dists route]
  (let [pairs (partition 2 1 route)
        steps (map (partial get-in dists) pairs)]
    (reduce + steps)))

(defn route-distances
  [dists]
  (let [nodes (keys dists)
        routes (combo/permutations nodes)]
    (map (partial route-distance dists) routes)))

(defn shortest-route-distance
  [dists]
  (apply min (route-distances dists)))

(defn longest-route-distance
  [dists]
  (apply max (route-distances dists)))

(defn day09-part1-soln
  []
  (shortest-route-distance day09-input))

(defn day09-part2-soln
  []
  (longest-route-distance day09-input))