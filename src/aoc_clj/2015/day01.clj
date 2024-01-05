(ns aoc-clj.2015.day01
  "Solution to https://adventofcode.com/2015/day/1")

;; Input parsing
(defn parse
  [input]
  (map {\( 1 \) -1} (first input)))

;; Puzzle logic
(defn final-floor
  [input]
  (reduce + 0 input))

(defn first-pos-in-basement
  [input]
  (->> (reductions + 0 input)
       (take-while #(not= -1 %))
       count))

;; Puzzle solutions
(defn day01-part1-soln
  "Interepting `(` as going up one floor and `)` as going down
   one floor what floor do you end up on given a string of 
   parentheses as input"
  [input]
  (final-floor input))

(defn day01-part2-soln
  "Still interpreting `(` as up one floor and `)` as down one
   floor, what's the first character in the input string that 
   causes the elevator to reach the basement (floor -1) "
  [input]
  (first-pos-in-basement input))

