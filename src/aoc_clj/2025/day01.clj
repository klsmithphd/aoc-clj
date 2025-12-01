(ns aoc-clj.2025.day01
  "Solution to https://adventofcode.com/2025/day/1" 
  (:require
    [clojure.string :as str]))

;; Constants
(def init-pos 50)

;; Input parsing
(defn parse-line
  [line]
  (let [num (read-string (subs line 1))]
    (if (str/starts-with? line "L")
      (- num)
      num)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic


;; Puzzle solutions
(defn part1
  [_]
  :not-implemented)

(defn part2
  [_]
  :not-implemented)