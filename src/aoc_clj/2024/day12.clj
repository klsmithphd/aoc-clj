(ns aoc-clj.2024.day12
  "Solution to https://adventofcode.com/2024/day/12"
  (:require [clojure.set :as set]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn adjacent
  "Returns a set of the adjacent cells that are part of the same plot"
  [cells cell]
  (set/intersection cells (set (grid/adj-coords-2d cell))))

(defn region
  "Given the cells belonging to a plot and a specific cell, return a set
   of all the contiguously connected cells"
  [cells cell]
  (loop [regn #{cell}
         front (adjacent cells cell)
         counter 0]
    (if-not (seq front)
      regn
      (let [new-region (into regn front)]
        (recur new-region
               (set (remove new-region (mapcat #(adjacent cells %) front)))
               (inc counter))))))

(defn regions
  "Returns all the regions (mutually connected cells)"
  [cells]
  (loop [groups #{} yet-ungrouped cells]
    (if (empty? yet-ungrouped)
      groups
      (let [new-group (region cells (first yet-ungrouped))]
        (recur (conj groups new-group)
               (remove new-group yet-ungrouped))))))

(defn plots
  "Returns a map of all the lettered plots, with the plot letter as the key
   and the continguous coordinate location regions as the value"
  [{:keys [grid]}]
  (->> (group-by val grid)
       (u/fmap #(regions (set (map key %))))))

(defn parse
  [input]
  (plots (mg/ascii->MapGrid2D identity input :down true)))

;; Puzzle logic
(defn area
  "Computes the area of a plot"
  [cells]
  (count cells))

(defn cell-edges
  "Counts how many perimeter edges a given cell has"
  [cells cell]
  (count (set/difference (set (grid/adj-coords-2d cell)) cells)))

(defn perimeter
  "Computes the perimeter of a plot (including both inner and outer
   perimeters)"
  [cells]
  (->> cells
       (map #(cell-edges cells %))
       (reduce +)))

(defn region-price
  "Returns the price, which is the product of the area and the perimeter"
  [cells]
  (* (perimeter cells) (area cells)))

(defn plot-price
  "Returns the price for a given plot of a type of plant"
  [[_ regions]]
  (->> (map region-price regions)
       (reduce +)))

(defn total-price
  "Computes the total price for a map of regions"
  [region-map]
  (->> (map plot-price region-map)
       (reduce +)))

;; Puzzle solutions
(defn part1
  [input]
  (total-price input))