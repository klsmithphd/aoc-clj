(ns aoc-clj.2024.day20
  "Solution to https://adventofcode.com/2024/day/20"
  (:require [aoc-clj.grid.interface :as grid]
            [aoc-clj.grid.interface :as mg]
            [aoc-clj.vectors.interface :as v]))

;; Constants
(def part1-savings 100)
(def p2-limit 20)

;; Input parsing
(def charmap
  {\# :wall
   \. :space
   \S :start
   \E :end})

(defn parse
  [input]
  (mg/ascii->MapGrid2D charmap input))

;; Puzzle logic
(def start-node (comp first (partial grid/find-nodes :start)))

(defn extend-path
  "Examine the neighborhood around the last node on the path and add
   the next open cell to the path."
  [grid path]
  (let [newnode (->> (grid/adj-coords-2d (peek path))
                     (filter #(not= :wall (grid/value grid %)))
                     (filter #(not= (peek (pop path)) %))
                     first)]
    (conj path newnode)))

(defn full-path
  "Returns a map of the full maze path, with the keys being the coordinate
   location of each step and the value being its order from 0..N along
   the path"
  [grid]
  (->> [(start-node grid)]
       (iterate #(extend-path grid %))
       (take-while #(some? (peek %)))
       last
       (map-indexed #(vector %2 %1))
       (into {})))

(defn p1-range
  "Returns a collection of the possible cheat points to jump to from
   the current position in part1, where we only have 2 picoseconds to cheat"
  [[row col]]
  [[(+ row 2) col]
   [(- row 2) col]
   [row (+ col 2)]
   [row (- col 2)]])

(defn p2-range
  "Returns a collection of the possible cheat points to jump to from the
   current position in part2, where we can move to any non-wall location
   that we can reach in 20 picoseconds or less."
  [pos]
  (for [dr (range (- p2-limit) (inc p2-limit))
        dc (range (- (abs dr) p2-limit) (- (inc p2-limit) (abs dr)))]
    (v/vec-add pos [dr dc])))

(defn cheat-savings
  "Computes the possible cheat savings options for a given node along the
   full path"
  [cheat-range-fn fullpath [node idx]]
  (->> (cheat-range-fn node)
       (map #(- (get fullpath % -1) (+ idx (v/manhattan node %))))
       (filter pos?)))

(defn cheats-more-than
  "Returns the cheats that are more than a given number of picoseconds"
  [range-fn n grid]
  (let [fullpath (full-path grid)]
    (->> (mapcat #(cheat-savings range-fn fullpath %) fullpath)
         (filter #(>= % n)))))

;; Puzzle solutions
(defn part1
  "How many cheats would save you at least 100 picoseconds?"
  [input]
  (count (cheats-more-than p1-range part1-savings input)))

(defn part2
  "Find the best cheats using the updated cheating rules.
   How many cheats would save you at least 100 picoseconds?"
  [input]
  (count (cheats-more-than p2-range part1-savings input)))