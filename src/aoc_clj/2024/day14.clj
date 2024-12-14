(ns aoc-clj.2024.day14
  "Solution to https://adventofcode.com/2024/day/14"
  (:require [clojure.core.async :as a :refer [go <! timeout]]
            [clojure.set :as set]
            [clojure.string :as str]
            [lanterna.screen :as scr]
            [aoc-clj.utils.vectors :as v]
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

(defn robots->strs
  [w h position-set]
  (->> (for [y (range h) x (range w)]
         (if (position-set [x y]) \# \ ))
       (partition w)
       (map str/join)
       (str/join "\n")))

(defn vec-mean
  "Computes the mean of a collection of vectors"
  [vs]
  (let [n (count vs)]
    (-> (reduce v/vec-add vs)
        (v/scalar-mult (float (/ 1 n))))))

(defn vec-mult
  "Hadamard (element-by-element) multiplication"
  [a b]
  (vec (map * a b)))

(defn variance
  "Computes the variance of all the robots' positions"
  [positions]
  (let [n       (count positions)
        mn-diff (v/scalar-mult (vec-mean positions) -1)
        vars    (->> (map #(v/vec-add % mn-diff) positions)
                     (map #(vec-mult % %))
                     (reduce v/vec-add))]
    (reduce + (v/scalar-mult vars (float (/ 1 n))))))

(defn min-4-variance-times
  "Finds the earliest 4 times when the variance of the robots' positions
   are at a minimum"
  [w h robots]
  (->> (range 200)
       (sort-by #(variance (robots-at-t w h % robots)))
       (take 4)
       sort))

(defn min-easter-egg-time
  "Finds the earliest time when the robots will form the easter egg
   image of a picture of a Christmas tree"
  [w h robots]
  (let [[t1 t2 t3 t4] (min-4-variance-times w h robots)]
    (apply min (clojure.set/intersection
                (set (range t1 100000 (- t3 t1)))
                (set (range t2 100000 (- t4 t2)))))))

;; Puzzle solutions
(defn part1
  "What will the safety factor be after exactly 100 seconds have elapsed?"
  [input]
  (safety-factor grid-width grid-height part1-time input))

(defn part2
  "What is the fewest number of seconds that must elapse for the robots to
   display the Easter egg?"
  [input]
  (let [t (min-easter-egg-time grid-width grid-height input)]
    (println (robots->strs
              grid-width
              grid-height
              (set (robots-at-t grid-width grid-height t input))))
    t))