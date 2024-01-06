(ns aoc-clj.2015.day02
  "Solution to https://adventofcode.com/2015/day/2"
  (:require [clojure.string :as str]))

;; Input parsing
(defn parse-line
  [line]
  (sort (map read-string (str/split line #"x"))))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn wrapping-paper-area
  [[a b c]]
  (+ (* 2 a b) (* 2 a c) (* 2 b c) (* a b)))

(defn ribbon-length
  [[a b c]]
  (+ (* 2 (+ a b)) (* a b c)))

;; Puzzle solutions
(defn part1
  [input]
  (reduce + (map wrapping-paper-area input)))

(defn part2
  [input]
  (reduce + (map ribbon-length input)))