(ns aoc-clj.2016.day18
  "Solution to https://adventofcode.com/2016/day/18"
  (:require [aoc-clj.utils.core :as u]))

;; Constants
(def trap \^)
(def safe \.)
(def part1-rows 40)
(def part2-rows 400000)

;; Input parsing
(def parse first)

;; Puzzle logic
(defn tile-logic
  [[l c r]]
  (cond
    (and (= l c trap) (= r safe)) trap
    (and (= c r trap) (= l safe)) trap
    (and (= l trap) (= c r safe)) trap
    (and (= r trap) (= l c safe)) trap
    :else safe))

(defn next-row
  [s]
  (->> (str safe s safe)
       (partition 3 1)
       (map tile-logic)
       (apply str)))

(defn safe-tile-count
  [rows start]
  (->> (iterate next-row start)
       (map #(count (filter (u/equals? safe) %)))
       (take rows)
       (reduce +)))

(defn part1
  [input]
  (safe-tile-count part1-rows input))

(defn part2
  [input]
  (safe-tile-count part2-rows input))