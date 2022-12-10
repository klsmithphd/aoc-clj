(ns aoc-clj.2022.day08
  "Solution to https://adventofcode.com/2022/day/8"
  (:require [aoc-clj.utils.core :as u]))

(defn parse-row
  [row]
  (mapv (comp read-string str) row))

(defn parse
  [input]
  (mapv parse-row input))

(def day08-input (parse (u/puzzle-input "2022/day08-input.txt")))

(def d08-s01
  (parse
   ["30373"
    "25512"
    "65332"
    "33549"
    "35390"]))

(defn transpose
  [m]
  (apply map vector m))

(defn visible?
  [s]
  (loop [nxt (rest s) mx (first s) acc [1]]
    (if (empty? nxt)
      acc
      (let [tree    (first nxt)
            taller? (> tree mx)]
        (recur (rest nxt)
               (if taller? tree mx)
               (conj acc (if taller? 1 0)))))))

(defn row-visibility
  [row]
  (mapv bit-or (visible? row) (reverse (visible? (reverse row)))))


(defn grid-visibility
  [grid]
  (let [width (count (first grid))]
    (vec
     (concat [(vec (repeat width 1))]
             (mapv row-visibility (butlast (drop 1 grid)))
             [(vec (repeat width 1))]))))

(defn visible-trees
  [input]
  (reduce + (map bit-or
                 (flatten (grid-visibility input))
                 (flatten (transpose (grid-visibility (transpose input)))))))



(defn day08-part1-soln
  []
  (visible-trees day08-input))