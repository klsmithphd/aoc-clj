(ns aoc-clj.2022.day09
  "Solution to https://adventofcode.com/2022/day/9"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [[l r] (str/split line #" ")]
    [l (read-string r)]))

(defn parse
  [input]
  (map parse-line input))

(def d09-s01
  (parse
   ["R 4"
    "U 4"
    "L 3"
    "D 1"
    "R 4"
    "D 1"
    "L 5"
    "R 2"]))

(defn expand
  [input]
  (flatten (map #(repeat (second %) (first %)) input)))

(def day09-input (parse (u/puzzle-input "2022/day09-input.txt")))

(defn move-tail
  [tail head]
  (let [delta (mapv - head tail)]
    (mapv + tail (case delta
                   [-1  2] [-1  1]
                   [0   2] [0   1]
                   [1   2] [1   1]
                   [-1 -2] [-1 -1]
                   [0  -2] [0  -1]
                   [1  -2] [1  -1]
                   [2   1] [1   1]
                   [2   0] [1   0]
                   [2  -1] [1  -1]
                   [-2  1] [-1  1]
                   [-2  0] [-1  0]
                   [-2 -1] [-1 -1]
                   [0 0]))))

(defn step
  [{:keys [tail head]} cmd]
  (let [newhead (mapv + head (case cmd
                               "R" [1 0]
                               "L" [-1 0]
                               "U" [0 1]
                               "D" [0 -1]))]
    {:head newhead :tail (move-tail tail newhead)}))

(defn distinct-tail-positions
  [input]
  (->> (expand input)
       (reductions step {:head [0 0] :tail [0 0]})
       (map :tail)
       distinct
       count))

(defn day09-part1-soln
  "Simulate your complete hypothetical series of motions. How many positions 
   does the tail of the rope visit at least once?"
  []
  (distinct-tail-positions day09-input))
