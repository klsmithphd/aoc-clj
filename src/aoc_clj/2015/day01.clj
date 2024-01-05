(ns aoc-clj.2015.day01
  "Solution to https://adventofcode.com/2015/day/1")

;; Input parsing
(defn parse
  [input]
  (map {\( 1 \) -1} (first input)))

;; Puzzle logic
(defn final-floor
  "Compute the final floor as the sum of all the up and down moves"
  [input]
  (reduce + 0 input))

(defn first-pos-in-basement
  "Determine the first time the elevator reaches the basement by counting
   how many moves it takes until the sum becomes -1"
  [input]
  (->> (reductions + 0 input)
       (take-while #(not= -1 %))
       count))

;; Puzzle solutions
(defn part1
  "Given a list of `1`s and `-1`s, each representing an elevator moving up or 
   down one floor, return the floor reached at the end of the moves."
  [input]
  (final-floor input))

(defn part2
  "Given the same list of `1`s and `-1`s, each representing an elevator moving
   up or down one floor, return the position of the first move in the
   sequence where the elevator reaches the basement (at -1)"
  [input]
  (first-pos-in-basement input))