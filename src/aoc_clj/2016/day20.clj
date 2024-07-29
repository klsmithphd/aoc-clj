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
  "Find the lowest unblocked IP address (i.e. the lowest
   address not excluded by the supplied ranges)"
  [blocked-ranges]
  ;; The simplify logic will collapse overlapping and adjacent
  ;; intervals in a sorted order, so it follows that there's a gap
  ;; the blocklist immediately one value higher than the upper 
  ;; value of the first interval.
  (->> (intervals/simplify blocked-ranges) 
       first
       second
       inc))

(defn- gap-size
  "Determine how many allowed values are between the two adjacent
   blocklist ranges"
  [[[_ r1] [l2 _]]]
  (- l2 r1 1))

(defn num-allowed-ips
  "How many IPs are allowed (i.e. not forbidden by the blocklist
   ranges)"
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
  "What is the lowest-valued IP that is not blocked?"
  [input]
  (lowest-unblocked-ip input))

(defn part2
  "How many IPs are allowed by the blocklist?"
  [input]
  (num-allowed-ips input))
