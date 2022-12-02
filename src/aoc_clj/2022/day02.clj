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

(def player2-outcome
  {"X" :player2-lose
   "Y" :draw
   "Z" :player2-win})

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

(def outcome-player2-play
  {:player2-win {:rock :paper
                 :paper :scissors
                 :scissors :rock}
   :draw        {:rock :rock
                 :paper :paper
                 :scissors :scissors}
   :player2-lose {:rock :scissors
                  :paper :rock
                  :scissors :paper}})

(defn parse-line
  [col2 line]
  (let [[p1 p2] (str/split line #" ")]
    [(player1 p1) (col2 p2)]))

(def parse-line-1 (partial parse-line player2))
(def parse-line-2 (partial parse-line player2-outcome))

(def day02-input (u/puzzle-input "2022/day02-input.txt"))
(def day02-input-1 (map parse-line-1 day02-input))
(def day02-input-2 (map parse-line-2 day02-input))

(defn round-score-1
  [round]
  (+ (shape-score (second round))
     (outcome-score (round->outcome round))))

(defn round-score-2
  [[p1 outcome]]
  (let [p2 (get-in outcome-player2-play [outcome p1])]
    (+ (shape-score p2)
       (outcome-score outcome))))

(defn day02-part1-soln
  []
  (reduce + (map round-score-1 day02-input-1)))

(defn day02-part2-soln
  []
  (reduce + (map round-score-2 day02-input-2)))