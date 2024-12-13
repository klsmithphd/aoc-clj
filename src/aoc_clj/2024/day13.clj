(ns aoc-clj.2024.day13
  "Solution to https://adventofcode.com/2024/day/13"
  (:require [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-chunk
  [chunk]
  (mapcat #(map read-string (re-seq #"\d+" %)) chunk))

(defn parse
  [input]
  (map parse-chunk (u/split-at-blankline input)))