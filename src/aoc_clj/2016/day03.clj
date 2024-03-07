(ns aoc-clj.2016.day03
  "Solution to https://adventofcode.com/2016/day/3"
  (:require [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-line
  [line]
  (read-string (str "[" line "]")))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn valid-triangle?
  "A triangle is valid if the sum of two sides are strictly greater than
   the the remaining side"
  [[a b c]]
  (and (> (+ a b) c)
       (> (+ b c) a)
       (> (+ c a) b)))

(defn count-of-valid-triangles
  "Count of triangles that are considered valid"
  [triangles]
  (->> triangles (filter valid-triangle?) count))

(defn group-by-columns
  "Re-interpret data with triangles spanning three consecutive rows
   in the same column."
  [input]
  ;; This implementation looks at rows in chunks of 3 and then just
  ;; transposes rows to columns
  (->> (partition 3 input)
       (mapcat u/transpose)))

;; Puzzle solutions
(defn part1
  "How many triangles are valid"
  [input]
  (count-of-valid-triangles input))

(defn part2
  "How many triangles are valid re-interpreting the data as spanning 3 rows"
  [input]
  (-> input group-by-columns count-of-valid-triangles))