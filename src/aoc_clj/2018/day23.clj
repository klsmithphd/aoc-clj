(ns aoc-clj.2018.day23
  "Solution to https://adventofcode.com/2018/day/23"
  (:require
   [aoc-clj.utils.vectors :as vec]))

;; Input parsing
(defn parse-line
  [line]
  (let [[x y z r] (map read-string (re-seq #"-?\d+" line))]
    [[x y z] r]))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn strongest-signal
  "Returns the nanobot with the strongest signal"
  [nanobots]
  (apply max-key second nanobots))

(defn in-range?
  "Returns whether nanobot b is within range of nanobot a"
  [[pos-a r] [pos-b _]]
  (<= (vec/manhattan pos-a pos-b) r))

(defn in-range-of-strongest-count
  "Counts the number of nanobots that are within range of the one
   with the strongest signal"
  [nanobots]
  (let [strongest (strongest-signal nanobots)]
    (->> nanobots
         (filter #(in-range? strongest %))
         count)))

;; Puzzle solutions
(defn part1
  "Find the nanobot with the largest signal radius. 
   How many nanobots are in range of its signals?"
  [input]
  (in-range-of-strongest-count input))