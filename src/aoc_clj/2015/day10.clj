(ns aoc-clj.2015.day10
  "Solution to https://adventofcode.com/2015/day/10"
  (:require [clojure.string :as str]))

(def parse first)

(defn look-and-say
  [s]
  (let [chunks (partition-by identity s)
        counts (map #(vector (str  (count %)) (str (first %))) chunks)]
    (str/join  (flatten  counts))))

(defn part1
  [input]
  (count (nth (iterate look-and-say input) 40)))

(defn part2
  [input]
  (count (nth (iterate look-and-say input) 50)))