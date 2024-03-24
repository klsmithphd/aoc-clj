(ns aoc-clj.2015.day04
  "Solution to https://adventofcode.com/2015/day/4"
  (:require [aoc-clj.utils.digest :as d]))

;; Input parsing
(def parse first)

;; Puzzle logic
(defn five-zero-start?
  "Whether the three bytes correspond in hex to starting with five zeroes"
  [bytes]
  (let [[a b c] (take 3 bytes)]
    (and (zero? a) (zero? b) (<= 0 c 15))))

(defn six-zero-start?
  "Whether the three bytes correspond in hex to starting with six zeroes"
  [bytes]
  (every? zero? (take 3 bytes)))

(defn first-integer
  "The first integer that satifies the supplied `pred` given the `secret`"
  [pred secret]
  (let [passing-hash? #(pred (d/md5-bytes (str secret %)))]
    (first (filter passing-hash? (range)))))

;; Puzzle solutions
(defn part1
  "The first integer whose MD5 hash in hex starts with five zeroes"
  [input]
  (first-integer five-zero-start? input))

(defn part2
  "The first integer whose MD5 hash in hex starts with six zeros"
  [input]
  (first-integer six-zero-start? input))