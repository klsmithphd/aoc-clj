(ns aoc-clj.2017.day15
  "Solution to https://adventofcode.com/2017/day/15"
  (:require [aoc-clj.utils.math :as math]))

;; Constants
(def lower-sixteen 65535)
(def a-mul 16807)
(def b-mul 48271)
(def modulus 2147483647)
(def sample-size 40000000)

;; Input parsing
(defn parse
  [input]
  (->> (map #(re-find #"\d+" %) input)
       (map read-string)))

;; Puzzle logic
(defn gen-a
  [num]
  (math/mod-mul modulus a-mul num))

(defn gen-b
  [num]
  (math/mod-mul modulus b-mul num))

(defn lower-bits-match?
  [[a b]]
  (= (bit-and a lower-sixteen) (bit-and b lower-sixteen)))

(defn judge-count
  [limit [start-a start-b]]
  (->> (map vector
            (drop 1 (iterate gen-a start-a))
            (drop 1 (iterate gen-b start-b)))
       (take limit)
       (filter lower-bits-match?)
       (count)))

;; Puzzle solutions
(defn part1
  [input]
  (judge-count sample-size input))