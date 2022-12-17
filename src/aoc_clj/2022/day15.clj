(ns aoc-clj.2022.day15
  "Solution to https://adventofcode.com/2022/day/15"
  (:require [aoc-clj.utils.math :as math]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [[x y bx by] (map read-string (re-seq #"\-?\d+" line))]
    {:sensor [x y] :beacon [bx by] :range (math/manhattan [x y] [bx by])}))

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

(defn in-range?
  "Is the given position `pos` within the detection `range` of the sensor
   at position given by `sensor`"
  [{:keys [sensor range]} pos]
  (<= (math/manhattan sensor pos) range))

(defn any-in-range?
  [sensors pos]
  (some true? (map #(in-range? % pos) sensors)))

(defn sensor-x
  [{:keys [sensor]}]
  (first sensor))

(defn leftmost
  [sensors]
  (apply min (map #(- (sensor-x %) (:range %)) sensors)))

(defn rightmost
  [sensors]
  (apply max (map #(+ (sensor-x %) (:range %)) sensors)))

(defn no-beacon-points-in-line
  [sensors y]
  (let [xmin (leftmost sensors)
        xmax (rightmost sensors)
        points (for [x (range xmin (inc xmax))] [x y])
        seen?  (partial any-in-range? sensors)
        beacons (set (map :beacon sensors))]
    (->> points
         (filter seen?)
         (remove beacons))))

(defn day15-part1-soln
  []
  (count (no-beacon-points-in-line day15-input 2000000)))