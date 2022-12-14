(ns aoc-clj.2022.day13
  "Solution to https://adventofcode.com/2022/day/13"
  (:require [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (map (partial mapv read-string)
       (u/split-at-blankline input)))

(def day13-input (parse (u/puzzle-input "2022/day13-input.txt")))

(declare in-order?)
(defn in-order-int?
  [a b]
  (cond
    (> a b) false
    (< a b) true
    (= a b) :noop))

(defn size-check
  [a b]
  (let [lena (count a)
        lenb (count b)]
    (cond
      (> lena lenb) false
      (< lena lenb) true
      (= lena lenb) :noop)))

(defn in-order-vector?
  [a b]
  (let [size-check  (size-check a b)
        order-check (map in-order? a b)]
    (if (= :noop size-check)
      (if (some false? order-check)
        false
        (if (some true? order-check)
          true
          :noop))
      size-check)))

(defn in-order?
  [a b]
  (cond
    (and (number? a) (number? b)) (in-order-int? a b)
    (and (number? a) (vector? b)) (in-order? (vector a) b)
    (and (vector? a) (number? b)) (in-order? a (vector b))
    (and (vector? a) (vector? b)) (in-order-vector? a b)))

(defn right-order-packet-id-sum
  [input]
  (->>
   (map-indexed
    (fn [a b] [(inc a) (apply in-order? b)])
    input)
   (filter second)
   (map first)
   (reduce +)))

(defn day13-part1-soln
  "Determine which pairs of packets are already in the right order. 
   What is the sum of the indices of those pairs?"
  []
  (right-order-packet-id-sum day13-input))

(count day13-input)
(count (filter true? (map (partial apply in-order?) day13-input)))