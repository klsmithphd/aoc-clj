(ns aoc-clj.utils.geometry
  "Utility functions for dealing with polygonal geometry"
  (:require [aoc-clj.utils.math :as math]
            [aoc-clj.utils.core :as u]))

(defn vertices->edges
  "Take an ordered collection of the vertices and return a collection of all 
   the edges"
  [vertices]
  (->> (u/ring vertices)
       (partition 2 1)))

(defn perimeter-length
  "Given the vertices of a polygon, compute the distance"
  [edges]
  (->> edges
       (map #(apply math/euclidean %))
       (reduce +)))

(defn shoelace-step
  "Helper function for Shoelace formula"
  [[[x1 y1] [x2 y2]]]
  (- (* x1 y2) (* x2 y1)))

(defn simple-polygon-area
  "Computes area of a simple polygon represented by the edges between
   its vertices using the Shoelace formula.
   https://en.wikipedia.org/wiki/Shoelace_formula"
  [edges]
  (let [area2 (->> edges
                   (map shoelace-step)
                   (reduce +))]
    (abs (/ area2 2))))

(defn interior-count
  "Computes the number of interior points using Pick's theorem"
  [edges]
  (let [area     (simple-polygon-area edges)
        boundary (perimeter-length edges)]
    (- area (/ boundary 2) -1)))