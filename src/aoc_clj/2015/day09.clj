(ns aoc-clj.2015.day09
  "Solution to https://adventofcode.com/2015/day/9"
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

(defn part1
  [input]
  (shortest-route-distance input))

(defn part2
  [input]
  (longest-route-distance input))