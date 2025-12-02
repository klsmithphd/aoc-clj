(ns aoc-clj.2025.day02
  "Solution to https://adventofcode.com/2025/day/2"
  (:require [clojure.string :as str]))

;; Input parsing
(defn parse-range
  [range]
  (->> (str/split range #"-")
       (mapv read-string)))


(defn parse
  [input]
  (->> (str/split (first input) #",")
       (map parse-range)))

;; Puzzle logic


;; Puzzle solutions
(defn part1
  [_]
  :not-implemented)

(defn part2
  [_]
  :not-implemented)