(ns aoc-clj.2016.day01
  "Solution to https://adventofcode.com/2016/day/1"
  (:require [clojure.string :as str]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.vectors :as v]))

;; Constants
(def origin [0 0])
(def start {:pos origin :heading :n})

;; Input parsing
(defn parse-cmd
  [cmd]
  (let [bearing  ({"R" :right "L" :left} (subs cmd 0 1))
        dist     (read-string (subs cmd 1))]
    [bearing dist]))

(defn parse
  [input]
  (map parse-cmd (str/split (first input) #", ")))

;; Puzzle logic
(defn step
  "Given an instruction to turn and then move forward a certain distance,
   determine the new state of the walker"
  [state [bearing dist]]
  (-> state (grid/turn bearing) (grid/forward dist)))

(defn move
  "Given all the instructions, determine the final state of the walker"
  [instructions]
  (reduce step start instructions))

(defn distance
  "Compute the Manhattan distance from the starting point after following
   the instructions"
  [instructions]
  (-> instructions move :pos (v/manhattan origin)))

(defn all-points
  "Determine all the points passed through by the walker following
   the instructions"
  [instructions]
  (->> (reductions step start instructions)
       (map :pos)
       (partition 2 1)
       (mapcat grid/interpolated)
       dedupe))

(defn distance-to-first-duplicate
  "Manhattan distance to the first location visited twice"
  [instructions]
  (->> instructions all-points u/first-duplicate (v/manhattan origin)))

;; Puzzle solutions
(defn part1
  "Distance to Easter Bunny HQ given the instructions"
  [input]
  (distance input))

(defn part2
  "Distance to first location visited twice given the instructions"
  [input]
  (distance-to-first-duplicate input))