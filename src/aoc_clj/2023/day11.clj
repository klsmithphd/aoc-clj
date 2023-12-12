(ns aoc-clj.2023.day11
  (:require [clojure.math.combinatorics :as combo]
            [clojure.set :as set]
            [aoc-clj.utils.grid :as grid :refer [height width value]]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.utils.math :as math]))

(def charmap {\. :empty \# :galaxy})

(defn parse
  [input]
  (vg/ascii->VecGrid2D charmap input :down true))

;; TODO -- finding the points that match a type should be part of the grid
;; protocol
(defn galaxies
  "Find the location of the galaxies in the grid"
  [grid]
  (let [locs (for [y (range (height grid))
                   x (range (width grid))]
               [x y])]
    (filter #(= :galaxy (value grid %)) locs)))

(defn voids
  "Finds the rows and columns (y and x indices) where there are no galaxies
   in the map"
  [grid]
  (let [galaxy-locs (galaxies grid)
        xs          (set (map first galaxy-locs))
        ys          (set (map second galaxy-locs))]
    {:cols (set/difference (set (range (width grid))) xs)
     :rows (set/difference (set (range (height grid))) ys)}))

(defn expand-coord
  "Scale out the coordinate location by expansion factor `expn` given
   the location of the `voids`."
  [expn {:keys [rows cols]} [x y]]
  (let [x-shift (count (filter #(> x %) cols))
        y-shift (count (filter #(> y %) rows))]
    [(+ x (* (dec expn) x-shift))
     (+ y (* (dec expn) y-shift))]))

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
       (map #(apply math/manhattan %))
       (reduce +)))

(defn day11-part1-soln
  "Expand the universe, then find the length of the shortest path between every
   pair of galaxies. What is the sum of these lengths?"
  [input]
  (pairwise-distance-sum (expanded-coords input 2)))

(defn day11-part2-soln
  "Starting with the same initial image, expand the universe according to these
   new rules, then find the length of the shortest path between every pair of
   galaxies. What is the sum of these lengths?"
  [input]
  (pairwise-distance-sum (expanded-coords input 1000000)))

