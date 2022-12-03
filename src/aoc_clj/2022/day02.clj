(ns aoc-clj.2022.day02
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(def guide->p1move
  {"A" ::rock
   "B" ::paper
   "C" ::scissors})

(def guide->p2move
  {"X" ::rock
   "Y" ::paper
   "Z" ::scissors})

(def guide->p2outcome
  {"X" ::lose
   "Y" ::draw
   "Z" ::win})

(def shape-score
  {::rock     1
   ::paper    2
   ::scissors 3})

(def outcome-score
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

(defn parse-line
  "Parse a `line` from the strategy guide input using `f` to parse
   the second column's value"
  [f line]
  (let [[p1 p2] (str/split line #" ")]
    [(guide->p1move p1) (f p2)]))

(def parse-line-part1 (partial parse-line guide->p2move))
(def parse-line-part2 (partial parse-line guide->p2outcome))

(def day02-input (u/puzzle-input "2022/day02-input.txt"))
(def day02-input-part1 (map parse-line-part1 day02-input))
(def day02-input-part2 (map parse-line-part2 day02-input))

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
  (reduce + (map score-part1 day02-input-part1)))

(defn day02-part2-soln
  "Following the Elf's instructions for the second column, what would your 
   total score be if everything goes exactly according to your strategy guide?"
  []
  (reduce + (map score-part2 day02-input-part2)))