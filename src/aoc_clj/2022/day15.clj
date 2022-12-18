(ns aoc-clj.2022.day15
  "Solution to https://adventofcode.com/2022/day/15"
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.intervals :as ivs]
            [aoc-clj.utils.math :as math]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [[x y bx by] (map read-string (re-seq #"\-?\d+" line))]
    {:sensor [x y] :beacon [bx by] :radius (math/manhattan [x y] [bx by])}))

(defn parse
  [input]
  (map parse-line input))

(def day15-input (parse (u/puzzle-input "2022/day15-input.txt")))

(defn visible-intervals
  "Along line `y`, identify the x interval range where the
   point is visible within the `sensor`'s `radius` (if any)."
  [y {:keys [sensor radius]}]
  (let [[sx sy] sensor
        xwidth (- radius (abs (- y sy)))]
    (when (pos? xwidth)
      [(- sx xwidth) (+ sx xwidth)])))

(defn no-beacon-points-in-line
  "Returns the number of points along the line at position `y`
   where there could not be any beacons (due to the fact that they
   would be seen by the `sensors`)"
  [sensors y]
  (let [intervals (->> (map #(visible-intervals y %) sensors)
                       (filter some?)
                       ivs/simplify)
        beacons-on-line  (->> (map :beacon sensors)
                              (filter #(= y (second %)))
                              (map first)
                              distinct
                              (filter #(ivs/in-intervals? % intervals))
                              count)]
    (- (reduce + (map #(- (inc (second %)) (first %)) intervals))
       beacons-on-line)))

(defn sensor-gap
  [s1 s2]
  (- (math/manhattan (:sensor s1) (:sensor s2))
     (:radius s1)
     (:radius s2)))

(defn sensor-overlap?
  "If two sensors have a negative or zero (non-positive) gap
   between them, their coverage overlaps"
  [s1 s2]
  (not (pos? (sensor-gap s1 s2))))

(defn rectangle?
  "Returns true if a sequence of four sensors forms a rectangle around a 
   gap. This requires that adjacent sensors overlap, but the opposite 
   sensors have a gap of exactly 2 units between them"
  [s1 s2 s3 s4]
  (and (sensor-overlap? s1 s2)
       (sensor-overlap? s2 s3)
       (sensor-overlap? s3 s4)
       (sensor-overlap? s4 s1)
       (= 2 (sensor-gap s1 s3))
       (= 2 (sensor-gap s2 s4))))

(defn find-gap-bounding-sensors
  "Given a collection of sensors, find the four sensors that bound a gap
   in coverage."
  [sensors]
  (->>
   (combo/permuted-combinations sensors 4)
   (filter #(apply rectangle? %))
   (map #(sort-by :sensor (comp - compare) %))
   distinct
   first))

(defn intersection-point
  "Compute the location of the gap using the information about the 
   bottom-right and top-right sensors that are known to bound the gap.
   This formula was derived by writing out the linear equations that represent
   the line of cells immediately outside a sensor for both the bottom-right
   and top-right sensors, and then solving for x and y where they intersect."
  [bottom-right top-right]
  (let [[sx1 sy1] (:sensor bottom-right)
        [sx2 sy2] (:sensor top-right)
        r1        (:radius bottom-right)
        r2        (:radius top-right)
        x (/ (- (+ sx1 sx2) (- sy2 sy1) (+ r2 r1) 2) 2)
        y (+ (- sx1 x) sy1 (- (inc r1)))]
    [x y]))

(defn gap-position
  "Given a collection of sensors, find the unique position where no sensors
   have any coverage"
  [sensors]
  (->> sensors
       ;; Will return the four sensors bounding the gap, sorted by
       ;; descending x, then descending y.
       find-gap-bounding-sensors
       ;; Take 2 selects the bottom-right and top-right sensors
       (take 2)
       (apply intersection-point)))

(defn tuning-frequency
  "The tuning frequency can be found by multiplying its x coordinate by 4000000
   and then adding its y coordinate."
  [[x y]]
  (+ (* 4000000 x) y))

(defn day15-part1-soln
  "Consult the report from the sensors you just deployed. In the row where 
   y=2000000, how many positions cannot contain a beacon?"
  []
  (no-beacon-points-in-line day15-input 2000000))

(defn day15-part2-soln
  "Find the only possible position for the distress beacon. 
   What is its tuning frequency?"
  []
  (tuning-frequency (gap-position day15-input)))
