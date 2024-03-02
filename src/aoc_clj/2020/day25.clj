(ns aoc-clj.2020.day25
  "Solution to https://adventofcode.com/2020/day/25"
  (:require [aoc-clj.utils.math :as math]))

(defn parse
  [input]
  (map read-string input))

(defn transform
  [loop-size subject-number]
  (math/mod-pow 20201227 subject-number loop-size))

(defn loop-size
  [public-key]
  (some #(when (= public-key (transform % 7)) %) (range 1 80000)))

(defn encryption-key
  [[card-public-key door-public-key]]
  (let [door-loop-size (loop-size door-public-key)]
    (transform door-loop-size card-public-key)))

(defn part1
  [input]
  (encryption-key input))