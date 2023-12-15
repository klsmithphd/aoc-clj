(ns aoc-clj.2023.day15
  (:require [clojure.string :as str]))

(defn parse
  [input]
  (str/split (first input) #","))

(defn hash-char
  [acc char]
  (-> (+ acc (int char))
      (* 17)
      (rem 256)))

(defn hash-alg
  [chars]
  (reduce hash-char 0 chars))

(defn hash-sum
  [input]
  (reduce + (map hash-alg input)))

(defn day15-part1-soln
  [input]
  (hash-sum input))