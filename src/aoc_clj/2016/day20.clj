(ns aoc-clj.2016.day20
  "Solution to https://adventofcode.com/2016/day/20"
  (:require [aoc-clj.utils.intervals :as intervals]))

;; Input parsing
(defn parse-line
  [line]
  (mapv read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn lowest-unblocked-ip
  [blocked-ranges]
  (->> (intervals/simplify blocked-ranges) 
       first
       second
       inc))

(defn- gap-size
  [[[_ r1] [l2 _]]]
  (- l2 r1 1))

(defn num-allowed-ips
  [blocked-ranges]
  (let [ranges (intervals/simplify blocked-ranges)
        first-gap (- (first (first ranges)) 0)
        last-gap (- 4294967295 (second (last ranges)))]
    (->> (intervals/simplify blocked-ranges)
         (partition 2 1)
         (map gap-size)
         (reduce +)
         (+ first-gap last-gap))))

;; Puzzle solutions
(defn part1
  [input]
  (lowest-unblocked-ip input))

(defn part2
  [input]
  (num-allowed-ips input))
