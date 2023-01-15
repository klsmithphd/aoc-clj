(ns aoc-clj.2022.day02
  "Solution to https://adventofcode.com/2022/day/2"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (map #(str/split % #" ") input))

(def day02-input (parse (u/puzzle-input "2022/day02-input.txt")))

(def guide->p1move
  "The first column is what your opponent is going to play: 
   A for Rock, B for Paper, and C for Scissors."
  {"A" ::rock
   "B" ::paper
   "C" ::scissors})

(def guide->p2move
  "The second column, you reason, must be what you should play in response: 
   X for Rock, Y for Paper, and Z for Scissors."
  {"X" ::rock
   "Y" ::paper
   "Z" ::scissors})

(def guide->p2outcome
  "Anyway, the second column says how the round needs to end: 
   X means you need to lose, Y means you need to end the round in a draw, and 
   Z means you need to win."
  {"X" ::lose
   "Y" ::draw
   "Z" ::win})

(def shape-score
  "the score for the shape you selected 
   (1 for Rock, 2 for Paper, and 3 for Scissors)"
  {::rock     1
   ::paper    2
   ::scissors 3})

(def outcome-score
  "the score for the outcome of the round 
   (0 if you lost, 3 if the round was a draw, and 6 if you won)"
  {::lose 0
   ::draw 3
   ::win  6})

(def permutations
  "All possible rock-paper-scissor plays listed as [p1, p2, outcome]"
  [[::rock     ::rock     ::draw]
   [::rock     ::paper    ::win]
   [::rock     ::scissors ::lose]
   [::paper    ::rock     ::lose]
   [::paper    ::paper    ::draw]
   [::paper    ::scissors ::win]
   [::scissors ::rock     ::win]
   [::scissors ::paper    ::lose]
   [::scissors ::scissors ::draw]])

(def outcomes
  "Map from [p1 p2] to game outcome (for player 2)"
  (into {} (map (fn [[p1 p2 o]] [[p1 p2] o]) permutations)))

(def p2-plays
  "Map from [p1 outcome] to player 2 play"
  (into {} (map (fn [[p1 p2 o]] [[p1 o] p2]) permutations)))

(defn interpret
  "Interpret the input data according to the guidance of part1 or part2,
   as given by the `interpret-fn` for the second column"
  [interpret-fn input]
  (letfn [(interpret [[p1 p2]]
            [(guide->p1move p1) (interpret-fn p2)])]
    (map interpret input)))

(def interpret-part1 (partial interpret guide->p2move))
(def interpret-part2 (partial interpret guide->p2outcome))

(defn score-part1
  "Compute the round score using the part1 interpretation of the strategy guide"
  [[_ p2 :as round]]
  (+ (shape-score p2)
     (outcome-score (outcomes round))))

(defn score-part2
  "Compute the round score using the part2 interpretation of the strategy guide"
  [[_ outcome :as round]]
  (+ (shape-score (p2-plays round))
     (outcome-score outcome)))

(defn day02-part1-soln
  "What would your total score be if everything goes exactly according to 
   your strategy guide?"
  []
  (reduce + (map score-part1 (interpret-part1 day02-input))))

(defn day02-part2-soln
  "Following the Elf's instructions for the second column, what would your 
   total score be if everything goes exactly according to your strategy guide?"
  []
  (reduce + (map score-part2 (interpret-part2 day02-input))))