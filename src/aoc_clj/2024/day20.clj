(ns aoc-clj.2024.day20
  "Solution to https://adventofcode.com/2024/day/20"
  (:require [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mg]))

;; Constants
(def part1-savings 100)

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

(defn cheat-shifts
  [[x y]]
  [[(+ x 2) y]
   [(- x 2) y]
   [x (+ y 2)]
   [x (- y 2)]])

(defn cheat-savings
  "Computes the possible cheat savings options for a given node along the
   full path"
  [fullpath [node idx]]
  (->> (cheat-shifts node)
       (map #(- (get fullpath % -1) (+ idx 2)))
       (filter pos?)))

(defn cheat-freqs
  "Computes a histogram map of how many cheats result in what savings"
  [grid]
  (let [fullpath (full-path grid)]
    (->> (mapcat #(cheat-savings fullpath %) fullpath)
         frequencies)))

(defn cheats-more-than
  "Computes the number of cheats that are more than a given number of picoseconds"
  [n grid]
  (let [fullpath (full-path grid)]
    (->> (mapcat #(cheat-savings fullpath %) fullpath)
         (filter #(>= % n))
         count)))

;; Puzzle solutions
(defn part1
  "How many cheats would save you at least 100 picoseconds?"
  [input]
  (cheats-more-than part1-savings input))