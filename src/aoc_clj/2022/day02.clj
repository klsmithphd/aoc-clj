(ns aoc-clj.2022.day02
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(def player1
  {"A" :rock
   "B" :paper
   "C" :scissors})

(def player2
  {"X" :rock
   "Y" :paper
   "Z" :scissors})

(def shape-score
  {:rock 1
   :paper 2
   :scissors 3})

(def outcome-score
  {:player2-lose 0
   :draw         3
   :player2-win  6})

(def round->outcome
  {[:rock :rock] :draw
   [:rock :paper] :player2-win
   [:rock :scissors] :player2-lose
   [:paper :rock] :player2-lose
   [:paper :paper] :draw
   [:paper :scissors] :player2-win
   [:scissors :rock] :player2-win
   [:scissors :paper] :player2-lose
   [:scissors :scissors] :draw})

(defn parse-line
  [line]
  (let [[p1 p2] (str/split line #" ")]
    [(player1 p1) (player2 p2)]))

(def day02-input (map parse-line (u/puzzle-input "2022/day02-input.txt")))

(defn round-score
  [round]
  (+ (shape-score (second round))
     (outcome-score (round->outcome round))))

(defn day02-part1-soln
  []
  (reduce + (map round-score day02-input)))

