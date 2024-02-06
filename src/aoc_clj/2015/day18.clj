(ns aoc-clj.2015.day18
  "Solution to https://adventofcode.com/2015/day/18"
  (:require [aoc-clj.utils.grid :as grid :refer [height value pos-seq val-seq]]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.utils.core :as u]))

;; Constants
(def soln-steps 100)

;; Input parsing
(def char-map {\. :off \# :on})
(defn parse
  [input]
  (vg/ascii->VecGrid2D char-map input :down true))

;; Puzzle logic
(defn corners-on
  "Force set the four corners to an on position"
  [grid]
  (let [x (dec (height grid))]
    (-> grid
        (assoc-in [:v 0 0] :on)
        (assoc-in [:v x 0] :on)
        (assoc-in [:v 0 x] :on)
        (assoc-in [:v x x] :on))))

(defn next-light-value
  "Computes the value of the light for the next step.
   
   A light which is on stays on when 2 or 3 neighbors are on, turns off otherwise.
   A light which is off turns on if 3 neighbors are on, stays off otherwise."
  [light on-neighbors]
  (case light
    :on  (if (<= 2 on-neighbors 3) :on :off)
    :off (if (= 3 on-neighbors)    :on :off)))

(defn update-lights
  "Update the light value in the grid at the given `position`, based
   on current neighbor values"
  [grid pos]
  (let [light (value grid pos)
        on-neighbors (->> (grid/neighbor-data grid pos :diagonals true)
                          (map :val)
                          (filter (u/equals? :on))
                          count)]
    (next-light-value light on-neighbors)))

(defn step
  "Update all lights in the grid based on their neighbors."
  ([grid]
   (step false grid))
  ([corners-on? grid]
   (let [new-data (->> (pos-seq grid)
                       (map #(update-lights grid %))
                       (partition (height grid))
                       (mapv vec)
                       vg/->VecGrid2D)]
     (if corners-on?
       (corners-on new-data)
       new-data))))

(defn lights-on-at-step-n
  "Evolve the light grid to the nth step"
  ([n grid]
   (lights-on-at-step-n false n grid))
  ([corners-on? n grid]
   (->>  (iterate (partial step corners-on?) grid)
         (drop n)
         first
         val-seq
         (filter (u/equals? :on))
         count)))

;; Puzzle solutions
(defn part1
  "How many lights are on after 100 steps"
  [input]
  (lights-on-at-step-n soln-steps input))

(defn part2
  "How many lights are on after 100 steps when the corners are kept always on"
  [input]
  (lights-on-at-step-n true soln-steps (corners-on input)))