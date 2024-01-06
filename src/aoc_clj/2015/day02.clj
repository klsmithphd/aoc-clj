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
  "Computes the wrapping paper area given the three dimensions of a box.
  
   The dimensions must be already ordered in ascending size. The wrapping 
   paper formula is the sum of the areas of all the sides plus an extra
   amount equal to the area of the smallest side."
  [[a b c]]
  (+ (* 2 a b) (* 2 a c) (* 2 b c) (* a b)))

(defn ribbon-length
  "Computes the ribbon length given the three dimensions of a box.
   
   The dimensions must already be ordered in ascending size. The ribbon 
   formula is the sum of the perimeter of the smallest side of the box 
   plus an extra amount equal to the volume of the box."
  [[a b c]]
  (+ (* 2 (+ a b)) (* a b c)))

;; Puzzle solutions
(defn part1
  "Computes the total wrapping paper required for all the input boxes."
  [input]
  (reduce + (map wrapping-paper-area input)))

(defn part2
  "Computes the total ribbon length required for all the input boxes."
  [input]
  (reduce + (map ribbon-length input)))