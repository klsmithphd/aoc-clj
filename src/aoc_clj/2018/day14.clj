(ns aoc-clj.2018.day14
  "Solution to https://adventofcode.com/2018/day/14"
  (:require [clojure.string :as str]))

;; Constants
(def init-state {:e1 0 :e2 1 :scores [3 7]})

;; Input parsing
(def parse (comp read-string first))

;; Puzzle logic
(defn step
  "Evolve the recipe state forward by one iteration"
  [{:keys [e1 e2 scores]}]
  (let [e1-score   (get scores e1)
        e2-score   (get scores e2)
        new-score  (+ e1-score e2-score)
        new-e1     (quot new-score 10)
        new-e2     (mod new-score 10)
        new-scores (if (pos? new-e1)
                     (conj scores new-e1 new-e2)
                     (conj scores new-e2))
        score-cnt  (count new-scores)]
    {:e1 (mod (+ e1 e1-score 1) score-cnt)
     :e2 (mod (+ e2 e2-score 1) score-cnt)
     :scores new-scores}))

(defn ten-recipes-after-n
  "What are the ten recipe scores after generating `n` recipes?"
  [n]
  (->> init-state
       (iterate step)
       (drop-while #(< (count (:scores %)) (+ n 10)))
       first
       :scores
       (drop n)
       (take 10)
       (apply str)))

(defn recipes-until-score
  "Returns the number of recipes generated until the sequence of scores
   given by `score` is produced. `limit` is used to control how many 
   scores are pre-computed before checking to see if `score` is produced.
   
   If `limit` is too low, this function will return nil."
  [score limit]
  (let [big-scores (->> init-state
                        (iterate step)
                        (drop limit)
                        first
                        :scores
                        (apply str))]
    (str/index-of big-scores score)))

;; Puzzle solutions
(defn part1
  "What are the scores of the ten recipes immediately after the number of
   recipes in your puzzle input?"
  [input]
  (ten-recipes-after-n input))

(defn part2
  "How many recipes appear on the scoreboard to the left of the score sequence
   in your puzzle input?"
  [input]
  (recipes-until-score (str input) 30000000))