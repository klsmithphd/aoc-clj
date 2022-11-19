(ns aoc-clj.2015.day12
  (:require [cheshire.core :as c]
            [aoc-clj.utils.core :as u]))

(def day12-input (first  (u/puzzle-input "2015/day12-input.txt")))

(defn all-numbers
  [s]
  (map read-string (re-seq #"-?\d+" s)))

(defn drop-red
  [s]
  (if (map? s)
    (if (some #{"red"} (vals s))
      {}
      (zipmap (keys s)
              (map drop-red (vals s))))

    (if (vector? s)
      (mapv drop-red s)
      s)))

(defn day12-part1-soln
  []
  (reduce + (all-numbers day12-input)))

(defn day12-part2-soln
  []
  (->> (c/parse-string day12-input)
       drop-red
       c/generate-string
       all-numbers
       (reduce +)))
