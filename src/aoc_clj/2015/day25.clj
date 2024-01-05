(ns aoc-clj.2015.day25
  "Solution to https://adventofcode.com/2015/day/25"
  (:require [aoc-clj.utils.math :as m]))

(def first-code 20151125)
(def modulus 33554393)
(def multiplier 252533)

(defn parse
  [input]
  (map read-string (re-seq #"\d+" (first input))))

;https://en.wikipedia.org/wiki/Lazy_caterer%27s_sequence
(defn lazy-caterer
  [tier]
  (/ (+ (* tier tier) tier 2) 2))

(defn code-number
  [row col]
  (let [tier (+ row col -1)]
    (+ (lazy-caterer (dec tier)) (dec col))))

(defn next-code
  [num]
  (mod (* num multiplier) modulus))

(defn code
  [row col]
  (let [code-num (code-number row col)]
    (if (= 1 code-num)
      first-code
      (m/mod-mul modulus (m/mod-pow modulus multiplier (dec code-num)) first-code))))

(defn part1
  [[row col]]
  (code row col))