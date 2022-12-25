(ns aoc-clj.2022.day25
  "Solution to https://adventofcode.com/2022/day/25"
  (:require [aoc-clj.utils.core :as u]
            [clojure.string :as str]))

(def d25-s01-decimal
  [1
   2
   3
   4
   5
   6
   7
   8
   9
   10
   15
   20
   2022
   12345
   314159265])

(def d25-s01-snafu
  ["1"
   "2"
   "1="
   "1-"
   "10"
   "11"
   "12"
   "2="
   "2-"
   "20"
   "1=0"
   "1-0"
   "1=11-2"
   "1-0---0"
   "1121-1110-1=0"])

(def d25-s02-decimal
  [1747
   906
   198
   11
   201
   31
   1257
   32
   353
   107
   7
   3
   37])

(def d25-s02-snafu
  ["1=-0-2"
   "12111"
   "2=0="
   "21"
   "2=01"
   "111"
   "20012"
   "112"
   "1=-1="
   "1-12"
   "12"
   "1="
   "122"])

(def snafu-map
  {\2 2
   \1 1
   \0 0
   \- -1
   \= -2})

(def day25-input (u/puzzle-input "2022/day25-input.txt"))

(defn powers-of-five
  "Returns a seq of the powers of five up to 5^(n-1) in descending order, i.e.,
   (5^(n-1) 5^(n-2) ... 5^0)"
  [n]
  (->> (drop 1 (range))
       (reductions (fn [a _] (* 5 a)))
       (take n)
       reverse))

(defn snafu->decimal
  "Convert SNAFU numerals to a base-10 number"
  [s]
  (let [n (count s)]
    (reduce +
            (map *
                 (map snafu-map s)
                 (powers-of-five n)))))

(defn decimal->snafu
  "Convert a base-10 number into a string of SNAFU numerals"
  [n]
  "1")
