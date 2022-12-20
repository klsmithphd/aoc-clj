(ns aoc-clj.2022.day20
  "Solution to https://adventofcode.com/2022/day/20"
  (:require [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (map read-string input))

(def d20-s01
  (parse
   ["1"
    "2"
    "-3"
    "3"
    "-2"
    "0"
    "4"]))

(def day20-input (parse (u/puzzle-input "2022/day20-input.txt")))

(defn grove-coordinates
  [nums]
  (let [n (count nums)
        zero-pos (u/index-of 0 nums)]
    (+ (nth nums (mod (+ zero-pos 1000) n))
       (nth nums (mod (+ zero-pos 2000) n))
       (nth nums (mod (+ zero-pos 3000) n)))))

