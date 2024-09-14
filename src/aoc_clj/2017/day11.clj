(ns aoc-clj.2017.day11
  "Solution to https://adventofcode.com/2017/day/11"
  (:require [clojure.string :as str]))

;; Input parsing
(defn parse
  [input]
  (str/split (first input) #","))