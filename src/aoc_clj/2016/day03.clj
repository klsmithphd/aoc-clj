(ns aoc-clj.2016.day03
  (:require [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (read-string (str "[" line "]")))

(def day03-input (map parse-line (u/puzzle-input "inputs/2016/day03-input.txt")))

(defn valid-triangle?
  [[a b c]]
  (and (> (+ a b) c)
       (> (+ b c) a)
       (> (+ c a) b)))

(defn count-of-valid-triangles
  [input]
  (->> input (filter valid-triangle?) count))

(defn day03-part1-soln
  []
  (count-of-valid-triangles day03-input))

(defn group-by-columns
  [input]
  (->> (concat (map #(nth % 0) input)
               (map #(nth % 1) input)
               (map #(nth % 2) input))
       (partition 3)))

(defn day03-part2-soln
  []
  (-> day03-input group-by-columns count-of-valid-triangles))