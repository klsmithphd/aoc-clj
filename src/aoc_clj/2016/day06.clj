(ns aoc-clj.2016.day06
  (:require [aoc-clj.utils.core :as u]))

(def day06-input (u/puzzle-input "2016/day06-input.txt"))

(defn frequent-chars
  [max-or-min-f input]
  (let [size (count input)]
    (->> (apply interleave input)
         (partition size)
         (map frequencies)
         (map (partial apply max-or-min-f val))
         (map first)
         (apply str))))

(def most-frequent-chars (partial frequent-chars max-key))
(def least-frequent-chars (partial frequent-chars min-key))

(defn day06-part1-soln
  []
  (most-frequent-chars day06-input))

(defn day06-part2-soln
  []
  (least-frequent-chars day06-input))