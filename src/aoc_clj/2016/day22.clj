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
  [[a b]]
  (and
   (not= (:pos a) (:pos b))
   (pos? (:used a))
   (<= (:used a) (:avail b))))


(defn viable-pair-count
  [nodes]
  (->> (combo/permuted-combinations nodes 2)
       (filter viable-pair?)
       count))

;; Puzzle solutions
(defn part1
  [input]
  (viable-pair-count input))