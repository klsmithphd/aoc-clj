(ns aoc-clj.2015.day18
  "Solution to https://adventofcode.com/2015/day/18"
  (:require [clojure.set :as set]
            [aoc-clj.utils.grid :as grid]))

;; Constants
(def iterations 100)

;; Input parsing
(def grid
  "Returns a list of all NxN [x y] position vecs in a grid of size N"
  (memoize
   (fn [size]
     (for [y (range size) x (range size)] [x y]))))

(defn char-at
  "Returns the character in the input data at the yth row and xth column."
  [input [x y]]
  (get-in (vec input) [y x]))

(defn parse
  [input]
  (let [size   (count input)
        lights (->> (grid size)
                    (filter #(= \# (char-at input %)))
                    set)]
    [size lights]))

;; Puzzle logic
(def neighbors
  "Generates a list of the 8 neighboring coordinates for position `pos`.

    This function is memoized (cached) because it's going to be called
    O(100) times for each cell in the grid, and the result is identical
    every time"
  (memoize
   (fn [pos]
     (grid/adj-coords-2d pos :include-diagonals true))))

(defn on-neighbors
  "Counts the number of neighbors of `pos` that are currently on by 
   testing whether they're in the `lights` set"
  [lights pos]
  (->> (neighbors pos) (filter lights) count))

(def corners
  "Returns a set of the four corner positions"
  (memoize
   (fn [size]
     #{[0 0] [(dec size) 0] [0 (dec size)] [(dec size) (dec size)]})))

(defn corners-on
  "Updates the state to ensure that the corners are on"
  [[size lights]]
  [size (set/union (corners size) lights)])

(defn on-condition
  "A predicate that returns true if the light should be on in the next step.
  
   A light which is on stays on when 2 or 3 neighbors are on; turns off otherwise.
   A light which is off turns on if 3 neighbors are on; stays off otherwise."
  [lights pos]
  (if (lights pos)
    (<= 2 (on-neighbors lights pos) 3)
    (== 3 (on-neighbors lights pos))))

(defn step
  "Given `state`, which is a vec of `[size, lights]`, where `size`
   is the dimension of the square grid, and `lights` is a set of all the
   positions of currently on lights, compute the next state according to the
   neighbor rules."
  [[size lights]]
  [size (->> (grid size)
             (filter #(on-condition lights %))
             set)])

(defn corner-step
  "Similar to `step` above, but ensures that corners are set to on."
  [state]
  (corners-on (step state)))

(defn lights-at-n
  "Returns the number of lights that are on as of iteration `n`

   If `corners?` is set to `true`, the four corners will be forced to be
   in an `on` state at all times."
  ([state n]
   (lights-at-n state n false))
  ([state n corners?]
   (-> (iterate (if corners? corner-step step) state) (nth n) second count)))

;; Puzzle solutions
(defn part1
  "How many lights are on after 100 steps"
  [input]
  (lights-at-n input iterations))

(defn part2
  "How many lights are on after 100 steps when the corners are kept always on"
  [input]
  (lights-at-n (corners-on input) iterations true))