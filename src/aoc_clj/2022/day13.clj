(ns aoc-clj.2022.day13
  "Solution to https://adventofcode.com/2022/day/13"
  (:require [aoc-clj.utils.core :as u]))

;;;; Constants
(def divider-packets
  "The distress signal protocol also requires that you include two 
   additional divider packets:"
  [[[[2]]
    [[6]]]])

;;;; Input parsing

(defn parse
  [input]
  (map (partial mapv read-string)
       (u/split-at-blankline input)))

;;;; Puzzle logic

(declare packet-compare)

(defn compare-size
  [a b]
  (compare (count a) (count b)))

(defn compare-vector
  "If both values are lists, compare the first value of each list, 
   then the second value, and so on. 
   
   If the left list runs out of items first, the inputs are in the right order. 
   
   If the right list runs out of items first, the inputs are not in the right 
   order. 
   
   If the lists are the same length and no comparison makes a decision about 
   the order, continue checking the next part of the input."
  [a b]
  (let [order-comparison (first (remove zero? (map packet-compare a b)))
        size-comparison  (compare-size a b)]
    (if (= 0 order-comparison size-comparison)
      0
      (if (nil? order-comparison)
        size-comparison
        order-comparison))))

(defn packet-compare
  [a b]
  (cond
    (and (number? a) (number? b)) (compare a b)
    (and (number? a) (vector? b)) (packet-compare (vector a) b)
    (and (vector? a) (number? b)) (packet-compare a (vector b))
    (and (vector? a) (vector? b)) (compare-vector a b)))

(defn in-order?
  [a b]
  (not (pos? (packet-compare a b))))

(defn right-order-packet-id-sum
  "What are the indices of the pairs that are already in the right order? 
   (The first pair has index 1, the second pair has index 2, and so on.)"
  [input]
  (->>
   (map-indexed (fn [idx [a b]] [(inc idx) (in-order? a b)]) input)
   (filter second)
   (map first)
   (reduce +)))

(defn sorted
  [input]
  (sort packet-compare (apply concat (concat divider-packets input))))

(defn decoder-key
  "To find the decoder key for this distress signal, you need to determine the 
   indices of the two divider packets and multiply them together. 
   (The first packet is at index 1, the second packet is at index 2, 
   and so on.)"
  [input]
  (let [packets (sorted input)
        p0      (inc (u/index-of (u/equals? [[2]]) packets))
        p1      (inc (u/index-of (u/equals? [[6]]) packets))]
    (* p0 p1)))

;;;; Puzzle solutions

(defn day13-part1-soln
  "Determine which pairs of packets are already in the right order. 
   What is the sum of the indices of those pairs?"
  [input]
  (right-order-packet-id-sum input))

(defn day13-part2-soln
  "Organize all of the packets into the correct order. 
   What is the decoder key for the distress signal?"
  [input]
  (decoder-key input))