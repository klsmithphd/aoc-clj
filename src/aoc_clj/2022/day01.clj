(ns aoc-clj.2022.day01
  (:require [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (map read-string input))

(def day01-input (->> (u/puzzle-input "2022/day01-input.txt")
                      (u/split-at-blankline)
                      (map parse)))
(defn elf-capacity
  [calorie-counts]
  (map-indexed (fn [idx cals] [idx (reduce + cals)]) calorie-counts))

(defn max-capacity
  [input]
  (apply max (map second (elf-capacity input))))

(defn day01-part1-soln
  []
  (max-capacity day01-input))

(defn top-three-max-capacity
  [input]
  (reduce + (take 3 (sort > (map second (elf-capacity input))))))

(defn day01-part2-soln
  []
  (top-three-max-capacity day01-input))
