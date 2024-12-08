(ns aoc-clj.2024.day08
  "Solution to https://adventofcode.com/2024/day/8"
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.vectors :as v]))

;; Constants
(def start-offset
  "How many delta-jumps off to one side to start the antinode-seq.
   This number was based on the puzzle input being a 50x50 grid"
  -50)

(def antinode-seq-size
  "How many deltas (the space between any pair of antenna) we
   need to explore"
  100)

;; Input parsing
(defn parse
  [input]
  (let [{:keys [width height grid]} (mg/ascii->MapGrid2D identity input)]
    {:width width
     :height height
     :antennae (->> grid
                    (remove #(= \. (val %)))
                    (group-by val)
                    (u/fmap #(into #{} (map first %))))}))

;; Puzzle logic
(defn in-bounds?
  "Returns true if the location is within the bounds of the map"
  [width height [x y]]
  (and (< -1 x width) (< -1 y height)))

(defn pair-antinodes
  "For a pair of two antenna locations, returns the two antinode locations"
  [[[x1 y1 :as a] [x2 y2 :as b]]]
  (let [diff [(- x2 x1) (- y2 y1)]]
    [(v/vec-add b diff)
     (v/vec-add a (v/scalar-mult diff -1))]))

(defn antinode-seq
  "For a pair of two same-frequency antenna locations, returns a seq of
   antinode grid positions. This seq is expected to return grid positions
   that may be out of bounds of the map."
  [[[x1 y1 :as a] [x2 y2]]]
  (let [diff [(- x2 x1) (- y2 y1)]
        start (v/vec-add a (v/scalar-mult diff start-offset))]
    (map #(v/vec-add start (v/scalar-mult diff %)) (range 1 antinode-seq-size))))

(defn antenna-antinodes
  "Returns a set of the antinode locations for the given antenna"
  [part width height [_ locs]]
  (let [antinodes-fn (case part
                       :part1 pair-antinodes
                       :part2 antinode-seq)]
    (->> (combo/combinations locs 2)
         (mapcat antinodes-fn)
         (filter #(in-bounds? width height %))
         set)))

(defn all-antinodes
  "For all antennas, returns a set of the location of the antinodes"
  [part {:keys [width height antennae]}]
  (->> antennae
       (mapcat #(antenna-antinodes part width height %))
       set))

;; Puzzle solutions
(defn part1
  "How many unique locations within the bounds of the map contain 
   an antinode?"
  [input]
  (count (all-antinodes :part1 input)))

(defn part2
  "Calculate the impact of the signal using this updated model.
   How many unique locations within the bounds of the map contain
   an antinode?"
  [input]
  (count (all-antinodes :part2 input)))