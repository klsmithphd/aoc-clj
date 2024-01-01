(ns aoc-clj.2020.day01
  "Solution to https://adventofcode.com/2020/day/1"
  (:require [clojure.math.combinatorics :as combo]))

(defn parse
  [input]
  (map read-string input))

(defn find-pair-that-sums-to-total
  [total candidates]
  (let [candidate-set (set candidates)]
    (->> candidates
         (map (partial - total))
         (filter candidate-set))))

(defn find-triple-that-sums-to-total
  [total candidates]
  (let [candidate-set (->> (combo/cartesian-product candidates candidates)
                           (map (partial apply +))
                           (map (partial - total))
                           set)]
    (->> candidates
         (filter candidate-set))))

(defn day01-part1-soln
  [input]
  (apply * (find-pair-that-sums-to-total 2020 input)))

(defn day02-part2-soln
  [input]
  (apply * (find-triple-that-sums-to-total 2020 input)))