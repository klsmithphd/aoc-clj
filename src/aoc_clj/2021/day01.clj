(ns aoc-clj.2021.day01
  "Solution to https://adventofcode.com/2021/day/1")

(defn parse
  [input]
  (map read-string input))

(defn increases
  [depths]
  (->> depths
       (partition 2 1)
       (map (fn [[a b]] (> b a)))
       (filter identity)
       count))

(defn sliding-window-sum
  [depths]
  (->> depths
       (partition 3 1)
       (map #(reduce + %))))

(defn part1
  [input]
  (increases input))

(defn part2
  [input]
  (increases (sliding-window-sum input)))