(ns aoc-clj.2015.day14
  "Solution to https://adventofcode.com/2015/day/14"
  (:require [aoc-clj.utils.vectors :as v]))

;; Input parsing
(defn parse-line
  [line]
  (let [[speed fly-time rest-time] (map read-string (re-seq #"\d+" line))]
    {:speed speed :fly fly-time :span (+ fly-time rest-time)}))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn position
  "Position of a single reindeer at a given `time`"
  [time {:keys [speed fly span]}]
  (let [intervals (quot time span)
        remainder (min fly (rem time span))]
    (+ (* intervals fly speed) (* remainder speed))))

(defn positions
  "Positions of all reindeer at a given `time`"
  [time reindeers]
  (map #(position time %) reindeers))

(defn points
  "Computes the points allocated to each reindeer, where any reindeer in the
   lead get 1, and everyone else gets 0"
  [positions]
  (let [lead (apply max positions)]
    (map #(if (= lead %) 1 0) positions)))

(defn cumulative_points
  "Constructs the number of points each reindeer has accumulated as of `time`"
  [time reindeers]
  (->> (range 1 (inc time))
       (map #(points (positions % reindeers)))
       v/vec-sum))

;; Puzzle solutions
(defn part1
  "Distance that the winning reindeer traveled after exactly 2503 seconds"
  [input]
  (apply max (map (partial position 2503) input)))

(defn part2
  "Maximum number of points any reindeer has accumulated after 2503 seconds"
  [input]
  (apply max (cumulative_points 2503 input)))