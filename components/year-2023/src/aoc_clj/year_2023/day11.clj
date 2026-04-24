(ns aoc-clj.year-2023.day11
  (:require [clojure.math.combinatorics :as combo]
            [clojure.set :as set]
            [aoc-clj.grid.interface :as grid :refer [height width value]]
            [aoc-clj.vectors.interface :as v]))

(def charmap {\. :empty \# :galaxy})

(defn parse
  [input]
  (grid/ascii->VecGrid2D charmap input))

(defn galaxies
  "Find the location of the galaxies in the grid"
  [grid]
  (filter #(= :galaxy (value grid %)) (grid/pos-seq grid)))

(defn voids
  "Finds the rows and columns where there are no galaxies in the map"
  [grid]
  (let [galaxy-locs   (galaxies grid)
        occupied-rows (set (map first galaxy-locs))
        occupied-cols (set (map second galaxy-locs))]
    {:cols (set/difference (set (range (width grid))) occupied-cols)
     :rows (set/difference (set (range (height grid))) occupied-rows)}))

(defn expand-coord
  "Scale out the coordinate location by expansion factor `expn` given
   the location of the `voids`."
  [expn {:keys [rows cols]} [row col]]
  (let [col-shift (count (filter #(> col %) cols))
        row-shift (count (filter #(> row %) rows))]
    [(+ row (* (dec expn) row-shift))
     (+ col (* (dec expn) col-shift))]))

(defn expanded-coords
  "Given a galaxy map `grid` and an expansion factor `expn`, return the
   location of the galaxies after expansion is taken into effect"
  [grid expn]
  (let [locs  (galaxies grid)
        voids (voids grid)]
    (mapv #(expand-coord expn voids %) locs)))

(defn pairwise-distance-sum
  "For any collection of points, compute the sum of the distances between the
   n*(n-1)/2 pairs of points"
  [locs]
  (->> (combo/combinations locs 2)
       (map #(apply v/manhattan %))
       (reduce +)))

(defn part1
  "Expand the universe, then find the length of the shortest path between every
   pair of galaxies. What is the sum of these lengths?"
  [input]
  (pairwise-distance-sum (expanded-coords input 2)))

(defn part2
  "Starting with the same initial image, expand the universe according to these
   new rules, then find the length of the shortest path between every pair of
   galaxies. What is the sum of these lengths?"
  [input]
  (pairwise-distance-sum (expanded-coords input 1000000)))

