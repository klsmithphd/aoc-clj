(ns aoc-clj.2016.day22
  "Solution to https://adventofcode.com/2016/day/22"
  (:require [clojure.math.combinatorics :as combo]))

;; Input parsing
(defn parse-line
  [line]
  (let [[x y size used avail usepct] (map read-string (re-seq #"\d+" line))]
    {:pos [x y]
     :size size
     :used used
     :avail avail
     :usepct usepct}))

(defn parse
  [input]
  (map parse-line (drop 2 input)))

;; Puzzle logic
(defn viable-pair?
  "Is a node pair viable?"
  [[a b]]
  (and
   ;; Node A and B are not the same node
   (not= (:pos a) (:pos b))
   ;; Node A is not empty (its Used is not zero)
   (pos? (:used a))
   ;; The data on Node A (its Used) would fit on node B (its Avail)
   (<= (:used a) (:avail b))))

(defn viable-pair-count
  "Counts the number of viable pairs there are"
  [nodes]
  (->> (combo/permuted-combinations nodes 2)
       (filter viable-pair?)
       count))

;; Puzzle solutions
(defn part1
  "How many viable pairs of nodes are there?"
  [input]
  (viable-pair-count input))