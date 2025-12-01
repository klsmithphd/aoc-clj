(ns aoc-clj.2025.day01
  "Solution to https://adventofcode.com/2025/day/1" 
  (:require [clojure.string :as str]
            [aoc-clj.utils.math :as math]))

;; Constants
(def init-pos 50)
(def dial-size 100)

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
(defn dial-positions
  [turns]
  (reductions (partial math/mod-add dial-size) init-pos turns))

(defn zero-count
  [turns] 
  (count (filter zero? (dial-positions turns))))

;; Puzzle solutions
(defn part1
  [input]
  (zero-count input))

(defn part2
  [_]
  :not-implemented)