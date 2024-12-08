(ns aoc-clj.2024.day08
  "Solution to https://adventofcode.com/2024/day/8"
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.vectors :as v]))

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
  [width height [x y]]
  (and (< -1 x width) (< -1 y height)))

(defn pair-antinodes
  [[[x1 y1 :as a] [x2 y2 :as b]]]
  (let [diff [(- x2 x1) (- y2 y1)]]
    [(v/vec-add b diff)
     (v/vec-add a (v/scalar-mult diff -1))]))

(defn antenna-antinodes
  [width height [_ locs]]
  (->> (combo/combinations locs 2)
       (mapcat pair-antinodes)
       (filter #(in-bounds? width height %))
       set))

(defn all-antinodes
  [{:keys [width height antennae]}]
  (->> antennae
       (mapcat #(antenna-antinodes width height %))
       set))

;; Puzzle solutions
(defn part1
  [input]
  (count (all-antinodes input)))