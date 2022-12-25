(ns aoc-clj.2022.day25
  "Solution to https://adventofcode.com/2022/day/25"
  (:require [aoc-clj.utils.core :as u]
            [clojure.string :as str]))

(def snafu-map
  {\2 2
   \1 1
   \0 0
   \- -1
   \= -2})

(def day25-input (u/puzzle-input "2022/day25-input.txt"))

(def powers-of-five
  "An infinite seq of the powers of five, i.e. 5^0, 5^1, ..."
  (reductions (fn [a _] (* 5 a)) (drop 1 (range))))

(def halves-of-powers-of-five
  "An infinite seq of the halves of all the powers of five"
  (map #(quot % 2) powers-of-five))

(defn fives-places
  "Returns a finite seq of the powers of five up to 5^(n-1) in 
   descending order, i.e., (5^(n-1) 5^(n-2) ... 5^0)"
  [n]
  (reverse (take n powers-of-five)))

(defn snafu->decimal
  "Convert SNAFU numerals to a base-10 number"
  [s]
  (let [n (count s)]
    (reduce +
            (map *
                 (map snafu-map s)
                 (fives-places n)))))

(defn round-away
  [num]
  (cond
    (<      num -1.5) -2
    (< -1.5 num -0.5) -1
    (< -0.5 num 0.5)   0
    (< 0.5  num 1.5)   1
    (< 1.5  num)       2))

(defn places
  [num powers]
  (if (= 1 (count powers))
    [num]
    (let [power (first powers)
          place (round-away (/ num power))
          rem   (- num (* place power))]
      (cons place (places rem (rest powers))))))

(defn decimal->snafu
  "Convert a base-10 number into a string of SNAFU numerals"
  [num]
  (if (zero? num)
    0
    (let [n (count (take-while #(> num %) halves-of-powers-of-five))
          powers (fives-places n)]
      (str/join (map (u/invert-map snafu-map) (places num powers))))))

(defn requirements-sum
  [input]
  (->> input
       (map snafu->decimal)
       (reduce +)
       decimal->snafu))

(defn day25-part1-soln
  []
  (requirements-sum day25-input))

