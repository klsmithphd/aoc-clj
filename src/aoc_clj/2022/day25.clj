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
  "Round away from zero, i.e., positive numbers round up toward 
   positive infinity and negative numbers round 'up' toward negative
   infinity. "
  [num]
  (cond
    (<      num -1.5) -2
    (< -1.5 num -0.5) -1
    (< -0.5 num 0.5)   0
    (< 0.5  num 1.5)   1
    (< 1.5  num)       2))

(defn places
  "Given a number and a seq of the powers of five in descending
   order (see `fives-places`), return a seq"
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
  "To heat the fuel, Bob needs to know the total amount of fuel that will be 
   processed ahead of time so it can correctly calibrate heat output and flow 
   rate. This amount is simply the sum of the fuel requirements of all of the 
   hot air balloons, and those fuel requirements are even listed clearly on 
   the side of each hot air balloon's burner.
   
   Apparently, the fuel requirements use numbers written in a format the Elves
   don't recognize; predictably, they'd like your help deciphering them.
   
   Bob needs the input value expressed as a SNAFU number, not in decimal."
  [input]
  (->> input
       (map snafu->decimal)
       (reduce +)
       decimal->snafu))

(defn day25-part1-soln
  "What SNAFU number do you supply to Bob's console?"
  []
  (requirements-sum day25-input))

