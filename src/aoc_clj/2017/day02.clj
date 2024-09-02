(ns aoc-clj.2017.day02
  "Solution to https://adventofcode.com/2017/day/2"
  (:require [clojure.math.combinatorics :as combo]))

;; Input parsing
(defn parse-line
  [line]
  (map read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn max-diff
  [values]
  (let [mx (apply max values)
        mn (apply min values)]
    (- mx mn)))

(defn even-quotient
  [values]
  (->> (combo/combinations (sort values) 2)
       (filter (comp zero? #(apply mod %) reverse))
       first
       reverse
       (apply /)))

(defn checksum
  [row-fn rows]
  (->> (map row-fn rows)
       (reduce +)))

;; Puzzle solutions
(defn part1
  [input]
  (checksum max-diff input))

(defn part2
  [input]
  (checksum even-quotient input))