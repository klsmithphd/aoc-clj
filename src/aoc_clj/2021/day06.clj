(ns aoc-clj.2021.day06
  "Solution to https://adventofcode.com/2021/day/6"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (->> (str/split (first input) #",")
       (map read-string)))

(def zero-counts (vec (take 9 (repeat 0))))

(defn step
  [counts]
  (-> (u/rotate 1 counts)
      vec
      (update 6 + (get counts 0))))

(defn fish-after-n-days
  [input days]
  (let [counts (reduce #(assoc %1 (key %2) (val %2)) zero-counts (frequencies input))]
    (reduce + (nth (iterate step counts) days))))

(defn part1
  [input]
  (fish-after-n-days input 80))


(defn part2
  [input]
  (fish-after-n-days input 256))