(ns aoc-clj.2023.day01
  "Solution to https://adventofcode.com/2023/day/1"
  (:require [aoc-clj.utils.core :as u]))

(def parse identity)

(def day01-input (u/parse-puzzle-input parse 2023 1))

(defn digits
  [s]
  (re-seq #"\d" s))

(defn calibration-value
  [s]
  (let [ds (digits s)
        a  (read-string (first ds))
        b  (read-string (last ds))]
    (+ (* 10 a) b)))

(defn calibration-value-sum
  [input]
  (reduce + (map calibration-value input)))


(defn day01-part1-soln
  []
  (calibration-value-sum day01-input))