(ns aoc-clj.2021.day07
  "Solution to https://adventofcode.com/2021/day/7"
  (:require [clojure.string :as str]))

(defn parse
  [line]
  (->> (str/split (first line) #",")
       (map read-string)))

(defn mean
  [vals]
  (/ (reduce + vals) (count vals)))

(defn median
  [vals]
  (let [sorted-vals (sort vals)
        n           (count vals)
        half        (int (/ n 2))]
    (if (odd? half)
      (nth sorted-vals half)
      (/ (+ (nth sorted-vals half)
            (nth sorted-vals (dec half)))
         2))))

(defn min-fuel
  [positions]
  (let [med (median positions)]
    (->> positions
         (map #(Math/abs (- % med)))
         (reduce +))))

(defn gauss-sum
  [n]
  (/ (* n (inc n)) 2))

(defn fuel-part2
  [positions dest]
  (->> positions
       (map #(Math/abs (- % dest)))
       (map gauss-sum)
       (reduce +)))

(defn min-fuel-part2
  [positions]
  (let [mn (int (mean positions))]
    (min (fuel-part2 positions mn)
         (fuel-part2 positions (inc mn)))))

(defn part1
  [input]
  (min-fuel input))

(defn part2
  [input]
  (min-fuel-part2 input))