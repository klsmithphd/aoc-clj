(ns aoc-clj.2023.day01
  "Solution to https://adventofcode.com/2023/day/1"
  (:require [aoc-clj.utils.core :as u]))

(def parse identity)

(def day01-input (u/parse-puzzle-input parse 2023 1))

(def numbers
  {"one"   1
   "two"   2
   "three" 3
   "four"  4
   "five"  5
   "six"   6
   "seven" 7
   "eight" 8
   "nine"  9})

(defn digits
  [s]
  (re-seq #"\d" s))

(defn digits2
  [s]
  (map second (re-seq #"(?=(\d|one|two|three|four|five|six|seven|eight|nine))" s)))

(defn s->int
  [s]
  (if (= 1 (count s))
    (read-string s)
    (numbers s)))

(defn calibration-value
  [digits-fn s]
  (let [ds (digits-fn s)
        a  (s->int (first ds))
        b  (s->int (last ds))]
    (+ (* 10 a) b)))

(defn calibration-value-sum
  [digits-fn input]
  (reduce + (map #(calibration-value digits-fn %) input)))

(defn day01-part1-soln
  []
  (calibration-value-sum digits day01-input))

(defn day01-part2-soln
  []
  (calibration-value-sum digits2 day01-input))