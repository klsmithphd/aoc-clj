(ns aoc-clj.2019.day03
  (:require [clojure.set :as set]
            [clojure.string :as str]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.math :as math]))

(defn parse-segment
  [segment]
  [(first segment) (read-string (str/join (rest segment)))])

(defn parse-segments
  [segments]
  (map parse-segment segments))

(def day03-input
  (->> (u/puzzle-input "inputs/2019/day03-input.txt")
       (map #(str/split % #","))
       (map parse-segments)))

(defn segment-points
  "From a given starting point [`x0` `y0`], list all the points
   along a segment indicated by a direction `dir` and `length`"
  [[x0 y0] [dir length]]
  (vec (case dir
         \U (for [y (range (inc y0) (inc (+ y0 length)))]    [x0 y])
         \D (for [y (range (dec y0) (dec (- y0 length)) -1)] [x0 y])
         \R (for [x (range (inc x0) (inc (+ x0 length)))]    [x y0])
         \L (for [x (range (dec x0) (dec (- x0 length)) -1)] [x y0]))))

(defn append-segment
  "Given a list of `points` and a `segment` directive, return a new list
   of all points with the new segment appended"
  [points segment]
  (vec (concat points (segment-points (peek points) segment))))

(defn wire-path
  "List all the points along a wire path starting at [0 0] following
   the segments (direction/distance pairs) given in `segments`"
  [segments]
  (reduce append-segment [[0 0]] segments))

(defn intersections
  "Find all the intersecting points along two wires described by their segments"
  [[wire1 wire2]]
  (let [path1 (set (wire-path wire1))
        path2 (set (wire-path wire2))]
    (set/intersection path1 path2)))

(defn closest-intersection-dist
  "Find the distance to the nearest intersection point for two wires
   each described by their segments"
  [wires]
  (->> (intersections wires)
       (map (partial math/manhattan [0 0]))
       sort
       ;; we take second because [0 0] is in the set
       second))

(defn day03-part1-soln
  []
  (closest-intersection-dist day03-input))

;; Slightly convoluted. I want a mapping from a point
;; to the shortest distance to that point. Because 
;; later values clobber earlier ones, I traverse the
;; path backwards so the shortest distance to a single
;; point remains in the map at the end
(defn shortest-distance-to-point
  "Returns a map from a point to the shortest distance from the origin
   to reach that point for a given path (assumes the path can cross 
   itself many times)"
  [path]
  (let [len (count path)]
    (zipmap (reverse path) (reverse (range len)))))

(defn distances-to-intersections
  "For each set of intersecting points along two lists of points, 
   find the combined distance (sum of distance along both wires)
   to all of the intersection points"
  [[wire1 wire2]]
  (let [path1 (wire-path wire1)
        path2 (wire-path wire2)
        path1-dists (shortest-distance-to-point path1)
        path2-dists (shortest-distance-to-point path2)
        ints (set/intersection (set path1) (set path2))]
    (map (fn [pos] [pos (+ (get path1-dists pos)
                           (get path2-dists pos))]) ints)))

(defn shortest-steps-to-intersection
  "Find the minimum number of steps to an intersection point for two wires
   each described by their segments"
  [[wire1 wire2]]
  (->> (distances-to-intersections [wire1 wire2])
       (sort-by second)
       ;; we take second because [0 0] is always the first intersection
       second
       ;; the second value of the pair is the number of steps
       second))

;; Sketch of alternative implementation
;; (defn shortest-steps-to-intersection
;;   "Find the minimum number of steps to an intersection point for two wires
;;    each described by their segments"
;;   [[wire1 wire2]]
;;   (let [path1 (wire-path wire1)
;;         path2 (wire-path wire2)
;;         path1-dists (shortest-distance-to-point path1)
;;         path2-dists (filter #((set (keys path1-dists)) (first %))
;;                             (map-indexed (fn [a b] [b a]) path2))
;;         dists (map #(+ (second %)
;;                        (get path1-dists (first %))) path2-dists)]
;;     (second (sort dists))))

(defn day03-part2-soln
  []
  (shortest-steps-to-intersection day03-input))