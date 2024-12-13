(ns aoc-clj.2024.day13
  "Solution to https://adventofcode.com/2024/day/13"
  (:require [aoc-clj.utils.core :as u]))

;; Constants
(def a-cost 3)
(def b-cost 1)
(def part2-shift 10000000000000)

;; Input parsing
(defn parse-chunk
  [chunk]
  (mapcat #(map read-string (re-seq #"\d+" %)) chunk))

(defn parse
  [input]
  (map parse-chunk (u/split-at-blankline input)))

;; Puzzle logic
(defn buttons
  "Determine how many times to press button A and B to move
   the claw to the prize, given the claw-machine configuration"
  [[Ax Ay Bx By X Y]]
  ;; Ax * a + Bx * b = X
  ;; Ay * a + By * b = Y
  ;;
  ;; Ay*Ax * a + Ay*Bx * b = Ay*X
  ;; Ax*Ay * a + Ax*By * b = Ax*Y
  ;;
  ;; (Ay*Bx - Ax*By)*b = Ay*X - Ax*Y
  ;; b = (Ay*X - Ax*Y) / (Ay*Bx - Ax*By)
  ;; a = (X - Bx*b)/Ax
  (let [b (/ (- (* Ay X) (* Ax Y))
             (- (* Ay Bx) (* Ax By)))
        a (/ (- X (* Bx b)) Ax)]
    [a b]))

(defn winners
  "Returns the button presses that will result in any winning game"
  [machines]
  (->> machines
       (map buttons)
       (filter #(every? integer? %))))

(defn tokens
  "Returns the token cost for a given number of A and B button presses"
  [[a b]]
  (+ (* a a-cost) (* b b-cost)))

(defn fewest-tokens
  "Returns the total number of tokens required to win any winnable games"
  [machines]
  (->> machines
       winners
       (map tokens)
       (reduce +)))

(defn part2-update
  "Updates the claw machine configuration to adding 10000000000000
   to the X and Y prize coordinates"
  [[Ax Ay Bx By X Y]]
  [Ax Ay Bx By (+ X part2-shift) (+ Y part2-shift)])

;; Puzzle solutions
(defn part1
  "What is the fewest tokens you would have to spend to win all possible prizes?"
  [input]
  (fewest-tokens input))

(defn part2
  "Using the corrected prize coordinates, figure out how to win as many prizes
   as possible. What is the fewest tokens you would have to spend to win all
   possible prizes?"
  [input]
  (fewest-tokens (map part2-update input)))