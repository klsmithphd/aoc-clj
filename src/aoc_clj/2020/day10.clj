(ns aoc-clj.2020.day10
  "Solution to https://adventofcode.com/2020/day/10"
  (:require [clojure.string :as str]))

(defn parse
  [input]
  (map read-string input))

(defn freq-steps
  [jolts]
  (let [l (sort (conj jolts 0))
        fs (frequencies (map - (rest l) l))]
    [(get fs 1)
     (inc (get fs 3))]))

(defn combo-mapping
  [run-size]
  (case run-size
    2 2
    3 4
    4 7))

(defn combination-count
  [jolts]
  (let [l (sort (conj jolts 0))
        runs (-> (map - (rest l) l)
                 str/join
                 (str/split #"3"))]
    (->> (map count runs)
         (filter #(> % 1))
         (map combo-mapping)
         (reduce *))))

(defn part1
  [input]
  (let [freqs (freq-steps input)]
    (reduce * freqs)))

(defn part2
  [input]
  (combination-count input))