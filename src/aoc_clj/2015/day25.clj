(ns aoc-clj.2015.day25
  "Solution to https://adventofcode.com/2015/day/25"
  (:require [aoc-clj.utils.math :as m]))

;; Constants
(def first-code 20151125)
(def modulus 33554393)
(def multiplier 252533)

;; Input parsing
(defn parse
  [input]
  (map read-string (re-seq #"\d+" (first input))))

;; Puzzle logic
(defn lazy-caterer
  "Computes the nth value in the lazy caterer's sequence using the formula
   p = (n^2 + n + 2)/2
   
   See https://en.wikipedia.org/wiki/Lazy_caterer%27s_sequence
   
   For this puzzle, the importance of this sequence is that it represents
   the code number for the first element in each row on the infinite sheet
   of paper."
  [n]
  (/ (+ (* n n) n 2) 2))

(defn code-number
  "Returns the code number (from 1 to infinity) that is found on the
   infinite sheet of codes at the given `row` and `col` (both 1-offset)."
  [row col]
  ;; Imagine each *tier* is counted starting at row=1, col=1 in the upper-left
  ;; and then numbered sequentially for each diagonal going from 
  ;; row=n, col=1 to row=1, col=n, i.e. this table depicts the tier value
  ;; for various row, col pairs:   
  ;;    | 1   2   3   4   5   6  
  ;; ---+---+---+---+---+---+---+
  ;;  1 |  1   2   3   4   5   6
  ;;  2 |  2   3   4   5   6   7
  ;;  3 |  3   4   5   6   7   8
  ;;  4 |  4   5   6   7   8   9
  ;;  5 |  5   6   7   8   9  10
  ;;  6 |  6   7   8   9  10  11

  ;; The tier then tells us where we are in the lazy caterer's sequence,
  ;; and then we add in the column offset to get the code number
  (let [tier (+ row col -1)]
    (+ (lazy-caterer (dec tier)) (dec col))))

(defn code
  "The code value from the infinite code sheet at the given `row` and `col`,
   (both 1-offset)"
  [row col]
  (let [code-num (dec (code-number row col))]
    (m/mod-mul modulus first-code (m/mod-pow modulus multiplier code-num))))

;; Puzzle solutions
(defn part1
  "The code value at the row/col positions (both 1-offset) in the input"
  [[row col]]
  (code row col))