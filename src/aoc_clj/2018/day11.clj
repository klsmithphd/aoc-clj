(ns aoc-clj.2018.day11
  "Solution to https://adventofcode.com/2018/day/11"
  (:require [clojure.string :as str]))

;; Constants
(def grid-size 300)

;; Input parsing
(defn parse
  [input]
  (read-string (first input)))

;; Puzzle logic
(defn rack-id
  "Computes the rack ID of a cell (its x position plus 10)"
  [[x _]]
  (+ x 10))

(defn hundreds
  "Returns the hundreds digit of any integer"
  [num]
  (mod (quot num 100) 10))

(def power-level
  "Returns the power level as a function of the serial number and the 
   cell coordinate"
  (memoize
   (fn [serial [_ y :as cell]]
     (let [r-id (rack-id cell)]
       (-> r-id
           (* y)
           (+ serial)
           (* r-id)
           hundreds
           (- 5))))))

(defn squares
  "For a square of `square-size` and the coordinate of the upper-left 
   corner of the square, returns all the cell coordinates within that
   square"
  [square-size [up-x up-y]]
  (for [y (range up-y (+ up-y square-size))
        x (range up-x (+ up-x square-size))]
    [x y]))

(defn upper-coords
  "For a given square size, returns all the possible upper-left coordinates
   of squares within the grid."
  [square-size]
  (for [y (range 1 (- grid-size square-size -2))
        x (range 1 (- grid-size square-size -2))]
    [x y]))

(defn total-power
  "For a given serial number, upper-left coordinate, and square size,
   computes the total power of the square"
  [serial [upper square-size]]
  (->> (squares square-size upper)
       (map #(power-level serial %))
       (reduce +)))

(defn highest-power-any-size-square
  "Returns the upper-left coordinate and size of the NxN square that has
   the highest total power for the given serial number, where N is an
   integer between `min-square` and `max-square`, inclusively"
  [serial min-square max-square]
  (let [square-options (for [size   (range min-square (inc max-square))
                             upper  (upper-coords size)]
                         [upper size])]
    (apply max-key #(total-power serial %) square-options)))

(defn highest-power-3x3-square
  "Returns the upper-left coordinates of the 3x3 square that has the
   highest total power for the given serial number"
  [serial]
  (->> (highest-power-any-size-square serial 3 3)
       first
       (str/join ",")))

;; Puzzle solution
(defn part1
  "What is the X,Y coordinate of the top-left fuel cell of the 3x3 square with the largest total power?"
  [input]
  (highest-power-3x3-square input))

(defn part2
  "What is the X,Y,size identifier of the square with the largest total power?"
  [input]
  (->> (highest-power-any-size-square input 1 grid-size)
       flatten
       (str/join ",")))