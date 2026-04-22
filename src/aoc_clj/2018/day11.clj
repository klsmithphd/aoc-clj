(ns aoc-clj.2018.day11
  "Solution to https://adventofcode.com/2018/day/11"
  (:require [clojure.string :as str]
            [aoc-clj.grid.interface :as vg]))

;; Constants
(def grid-size 300)

;; Input parsing
(defn parse
  [input]
  (read-string (first input)))

;; Puzzle logic
(defn rack-id
  "Computes the rack ID of a cell (its col position plus 10)"
  [[_ col]]
  (+ col 10))

(defn hundreds
  "Returns the hundreds digit of any integer"
  [num]
  (mod (quot num 100) 10))

(def cell-power-level
  "Returns the power level as a function of the serial number and the
   cell coordinate [row col]"
  (memoize
   (fn [serial [row _ :as cell]]
     (let [r-id (rack-id cell)]
       (-> r-id
           (* row)
           (+ serial)
           (* r-id)
           hundreds
           (- 5))))))

(defn power-levels
  "Returns a vec-of-vecs of the power levels across the grid"
  [serial]
  (vec (for [row (range grid-size)]
         (vec (for [col (range grid-size)]
                (cell-power-level serial [row col]))))))

(defn upper-coords
  "For a given square size, returns all the possible upper-left coordinates
   of squares within the grid."
  [square-size]
  (for [row (range (- grid-size square-size -1))
        col (range (- grid-size square-size -1))]
    [row col square-size]))

(defn highest-power-square
  "Returns the upper-left coordinate and size of the square of size
   `size` that has the highest total power."
  [sat size]
  (->> (upper-coords size)
       (apply max-key #(vg/square-area-sum sat %))))

(defn highest-power-any-size-square
  "Returns the upper-left coordinate and size of the NxN square that has
   the highest total power for the given serial number, where N is an
   integer between `min-square` and `max-square`, inclusively"
  [serial]
  (let [sat (vg/summed-area-table (power-levels serial))
        [row col size] (->> (range 1 (inc grid-size))
                            (map #(highest-power-square sat %))
                            (apply max-key #(vg/square-area-sum sat %)))]
    (str/join "," [col row size])))

(defn highest-power-3x3-square
  "Returns the upper-left coordinates of the 3x3 square that has the
   highest total power for the given serial number"
  [serial]
  (let [sat (vg/summed-area-table (power-levels serial))
        [row col] (highest-power-square sat 3)]
    (str/join "," [col row])))

;; Puzzle solution
(defn part1
  "What is the X,Y coordinate of the top-left fuel cell of the 3x3 square with the largest total power?"
  [input]
  (highest-power-3x3-square input))

(defn part2
  "What is the X,Y,size identifier of the square with the largest total power?"
  [input]
  (highest-power-any-size-square input))