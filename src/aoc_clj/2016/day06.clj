(ns aoc-clj.2016.day06
  "Solution to https://adventofcode.com/2016/day/6"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Puzzle input
(def parse identity)

;; Puzzle logic

(defn frequent-chars
  "A string of the most or least frequent characters in each column of
   the code strings"
  [max-or-min-f codes]
  (->> (u/transpose codes)
       (map frequencies)
       (map (partial apply max-or-min-f val))
       (map key)
       str/join))

(def most-frequent-chars (partial frequent-chars max-key))
(def least-frequent-chars (partial frequent-chars min-key))

;; Puzzle solutions
(defn part1
  "The string composed of the most frequent chars in each column"
  [input]
  (most-frequent-chars input))

(defn part2
  "The string composed of the least frequent chars in each column"
  [input]
  (least-frequent-chars input))