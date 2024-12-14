(ns aoc-clj.2024.day14
  "Solution to https://adventofcode.com/2024/day/14"
  (:require [aoc-clj.utils.vectors :as v]
            [aoc-clj.utils.core :as u]))

;; Constants
(def grid-width 101)
(def grid-height 103)
(def part1-time 100)

;; Input parsing
(defn parse-line
  [line]
  (->> (re-seq #"-?\d+" line)
       (map read-string)
       (partition 2)
       (map vec)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn robot-at-t
  "Returns the position of a single robot at time t, given a wrap-around
   grid of width w and height h"
  [w h t [pos vel]]
  (let [[x y] (->> (v/scalar-mult vel t)
                   (v/vec-add pos))]
    [(mod x w) (mod y h)]))

(defn robots-at-t
  "Returns the position of all the robots at time t, given a wrap-around
   grid of width w and height h"
  [w h t robots]
  (map #(robot-at-t w h t %) robots))

(defn quadrant-key
  "For a grid of a given width and height, maps a position into a quadrant
   number, numbered 1, 2 across the top left-to-right and 3, 4 across the
   bottom left-to-right. Positions that fall on the boundary are mapped to 0"
  [w h [x y]]
  (let [x-mid (quot w 2)
        y-mid (quot h 2)]
    (cond
      (and (< x x-mid) (< y y-mid)) 1
      (and (> x x-mid) (< y y-mid)) 2
      (and (< x x-mid) (> y y-mid)) 3
      (and (> x x-mid) (> y y-mid)) 4
      :else 0)))

(defn quadrant-counts
  "Returns a map of the number of robots in each quadrant"
  [w h positions]
  (-> (map #(quadrant-key w h %) positions)
      frequencies
      (u/without-keys [0])))

(defn safety-factor
  "Returns the safety factor, which is the product of the number of robots
   in each quadrant of the grid at a given time"
  [w h t robots]
  (->> robots
       (robots-at-t w h t)
       (quadrant-counts w h)
       vals
       (reduce *)))

;; Puzzle solutions
(defn part1
  "What will the safety factor be after exactly 100 seconds have elapsed?"
  [input]
  (safety-factor grid-width grid-height part1-time input))