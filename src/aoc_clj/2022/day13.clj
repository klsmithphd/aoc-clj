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
  (let [order-check (first (remove #{:noop} (map in-order? a b)))
        size-check  (size-check a b)]
    (if (= :noop order-check size-check)
      :noop
      (if (nil? order-check)
        size-check
        order-check))))

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

(defn in-order-compare
  [a b]
  (case (in-order? a b)
    true -1
    :noop 0
    false 1))

(def divider-packets [[[[2]] [[6]]]])
(ffirst divider-packets)

(defn sorted
  [input]
  (sort in-order-compare (apply concat (concat divider-packets input))))

(defn decoder-key
  "To find the decoder key for this distress signal, you need to determine the 
   indices of the two divider packets and multiply them together. 
   (The first packet is at index 1, the second packet is at index 2, 
   and so on.)"
  [input]
  (let [packets (sorted input)
        p0      (inc (u/index-of [[2]] packets))
        p1      (inc (u/index-of [[6]] packets))]
    (* p0 p1)))

(defn day13-part1-soln
  "Determine which pairs of packets are already in the right order. 
   What is the sum of the indices of those pairs?"
  []
  (right-order-packet-id-sum day13-input))

(defn day13-part2-soln
  "Organize all of the packets into the correct order. 
   What is the decoder key for the distress signal?"
  []
  (decoder-key day13-input))