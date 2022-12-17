(ns aoc-clj.2022.day15
  "Solution to https://adventofcode.com/2022/day/15"
  (:require [aoc-clj.utils.intervals :as ivs]
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

