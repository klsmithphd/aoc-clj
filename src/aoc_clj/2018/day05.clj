(ns aoc-clj.2018.day05
  "Solution to https://adventofcode.com/2018/day/5"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Constants
(def lowers (map char (range 97 123)))
(def uppers (map char (range 65 91)))
(def pair-pattern
  (->> (concat (map str lowers uppers)
               (map str uppers lowers))
       (str/join "|")
       re-pattern))

;; Input parsing
(def parse first)

;; Puzzle logic
(defn collapsed
  [s]
  (str/replace s pair-pattern ""))

(defn fully-collapsed
  [s]
  (last (u/converge collapsed s)))

(defn fully-collapsed-count
  [s]
  (count (fully-collapsed s)))

;; Puzzle solutions
(defn part1
  [input]
  (fully-collapsed-count input))