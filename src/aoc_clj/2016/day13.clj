(ns aoc-clj.2016.day13
  "Solution to https://adventofcode.com/2016/day/13"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.binary :as b]
            [aoc-clj.utils.graph :as g :refer [Graph]]
            [aoc-clj.utils.grid :as grid]))

;; Constants
(def start-pos [1 1])
(def part1-target-pos [31 39])
(def part2-step-limit 50)

;; Input parsing
(defn parse
  [input]
  (read-string (first input)))

;; Puzzle logic
(defn valid-coord?
  "The grid stretches from 0,0 infinitely to all positive values of x and y"
  [[x y]]
  (and (>= x 0) (>= y 0)))

(defn wall-calc
  "Compute the _point value_ of a given x,y position using the office
   designer's favorite number `fav`"
  [fav [x y]]
  (+ fav (* x x) (* 3 x) (* 2 x y) y (* y y)))

(defn open?
  "Determine whether the given `pos` is open (i.e. not a wall)"
  [fav pos]
  (->> (wall-calc fav pos)
       b/int->bitstr
       (filter #(= \1 %))
       count
       even?))

(defn valid-neighbors
  "Given the office designer's favorite number `fav` and a given position
   `pos`, return a seq of the allowed neighboring moves."
  [fav pos]
  (->> (grid/adj-coords-2d pos)
       (filter valid-coord?)
       (filter #(open? fav %))))

(defrecord CubicleMazeGraph [fav]
  Graph
  (edges
    [_ v]
    (valid-neighbors fav v))

  (distance
    [_ _ _]
    1))

(defn shortest-path-length
  "Compute the length of the shortest path from 1,1 to the `finish` location
   for a given office designer's favorite number `fav`"
  [fav finish]
  (let [graph (->CubicleMazeGraph fav)]
    (->> (g/shortest-path graph start-pos (u/equals? finish))
         count
         dec)))

(defn reachable-locations
  "Returns a set of the locations reachable in up to `limit` moves
   away from 1,1 given the office designer's favorite number `fav`"
  [fav limit]
  (let [graph (->CubicleMazeGraph fav)]
    (g/flood-fill graph start-pos :limit limit)))

;; Puzzle solutions
(defn part1
  "Fewest number of steps required to reach 31,39"
  [input]
  (shortest-path-length input part1-target-pos))

(defn part2
  "How many locations including starting pos can you reach in at most 50 steps?"
  [input]
  (count (reachable-locations input part2-step-limit)))