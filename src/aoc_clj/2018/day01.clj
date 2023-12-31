(ns aoc-clj.2018.day01
  (:require [aoc-clj.utils.core :as u]))

(def day01-input (map read-string (u/puzzle-input "inputs/2018/day01-input.txt")))

(defn net-freq-change
  "Sums all the changes in frequency, starting with zero"
  [deltas]
  (reduce + deltas))

(defn find-first-repeated-freq
  "Find the first net frequency that repeats"
  [deltas]
  (loop [freqs    (reductions + (cycle deltas))
         observed #{0}]
    (let [freq (first freqs)]
      (if (contains? observed freq)
        freq
        (recur (rest freqs) (conj observed freq))))))

(defn day01-part1-soln
  []
  (net-freq-change day01-input))

(defn day01-part2-soln
  []
  (find-first-repeated-freq day01-input))