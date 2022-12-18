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

(def d15-s01
  (parse
   ["Sensor at x=2, y=18: closest beacon is at x=-2, y=15"
    "Sensor at x=9, y=16: closest beacon is at x=10, y=16"
    "Sensor at x=13, y=2: closest beacon is at x=15, y=3"
    "Sensor at x=12, y=14: closest beacon is at x=10, y=16"
    "Sensor at x=10, y=20: closest beacon is at x=10, y=16"
    "Sensor at x=14, y=17: closest beacon is at x=10, y=16"
    "Sensor at x=8, y=7: closest beacon is at x=2, y=10"
    "Sensor at x=2, y=0: closest beacon is at x=2, y=10"
    "Sensor at x=0, y=11: closest beacon is at x=2, y=10"
    "Sensor at x=20, y=14: closest beacon is at x=25, y=17"
    "Sensor at x=17, y=20: closest beacon is at x=21, y=22"
    "Sensor at x=16, y=7: closest beacon is at x=15, y=3"
    "Sensor at x=14, y=3: closest beacon is at x=15, y=3"
    "Sensor at x=20, y=1: closest beacon is at x=15, y=3"]))

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
  [s1 s2]
  (not (pos? (sensor-gap s1 s2))))

(defn rectangle?
  [s1 s2 s3 s4]
  (and (sensor-overlap? s1 s2)
       (sensor-overlap? s2 s3)
       (sensor-overlap? s3 s4)
       (sensor-overlap? s4 s1)
       (= 2 (sensor-gap s1 s3))
       (= 2 (sensor-gap s2 s4))))

(defn find-bounding-sensors
  [sensors]
  (->>
   (combo/permuted-combinations sensors 4)
   (filter #(apply rectangle? %))
   (map #(map :sensor %))
   (map sort)
   distinct))

(defn bounding-direction
  [[x1 y1] [x2 y2]]
  (if (> x2 x1)
    (if (> y2 y1)
      :ne
      :se)
    (if (> y2 y1)
      :nw
      :sw)))

(defn gap-position
  "s1 is the bottom-right rectangle adjacent to the gap
   s2 is the bottom-left rectangle adjacent to the gap"
  [s1 s2]
  (let [[sx1 sy1] (:sensor s1)
        [sx2 sy2] (:sensor s2)
        r1        (:radius s1)
        r2        (:radius s2)
        x (/ (+ (+ sx1 sx2) (- sy1 sy2) (- r2 r1)) 2)
        y (+ (- sx1 x) sy1 (- (inc r1)))]
    [x y]))

(gap-position {:sensor [20 14] :radius 8} {:sensor [12 14] :radius 4})



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
  12)

