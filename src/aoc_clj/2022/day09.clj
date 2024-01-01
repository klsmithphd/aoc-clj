(ns aoc-clj.2022.day09
  "Solution to https://adventofcode.com/2022/day/9"
  (:require [clojure.string :as str]))

;;;; Constants

(def move->delta
  {"R" [1 0]
   "L" [-1 0]
   "U" [0 1]
   "D" [0 -1]})

;;;; Input parsing

(defn parse-line
  [line]
  (let [[l r] (str/split line #" ")]
    [l (read-string r)]))

(defn expand
  [input]
  (flatten (map #(repeat (second %) (first %)) input)))

(defn parse
  [input]
  (expand (map parse-line input)))

;;;; Puzzle logic

(defn cap
  "Limit the size of the move to no more than one move in each direction."
  [num]
  (if (even? num)
    (/ num 2)
    num))

(defn delta
  "Compute the amount by which the tail position should change based
   on the new position of the knot in front of it"
  [tail head]
  (let [d (mapv - head tail)]
    (if (every? #(<= % 1) (map abs d))
      [0 0]
      (mapv cap d))))

(defn move-tail
  "Update the tail position based on the position of the knot in front of it"
  [tail head]
  (mapv + tail (delta tail head)))

(defn step
  "Recursively update the location of the tail based on the new position
   of the knot in front of it. When reaching the head, update the position
   based on the move `cmd`"
  [chain cmd]
  (if (= 1 (count chain))
    (vector (mapv + (first chain) (move->delta cmd)))
    (let [newchain (step (rest chain) cmd)]
      (cons (move-tail (first chain) (first newchain)) newchain))))

(defn all-moves
  "Returns a seq of all of the states of the chain after each move
   in `moves` has been applied"
  [chain-length moves]
  (reductions step (repeat chain-length [0 0]) moves))

(defn distinct-tail-positions
  "Computes how many unique positions the tail of the chain of length
   `chain-length` and the head moves in `moves`"
  [chain-length moves]
  (->> (all-moves chain-length moves)
       (map first)
       distinct
       count))

;;;; Puzzle solutions

(defn day09-part1-soln
  "Simulate your complete hypothetical series of motions. How many positions 
   does the tail of the rope visit at least once?"
  [input]
  (distinct-tail-positions 2 input))

(defn day09-part2-soln
  "Simulate your complete series of motions on a larger rope with ten knots. 
   How many positions does the tail of the rope visit at least once?"
  [input]
  (distinct-tail-positions 10 input))

