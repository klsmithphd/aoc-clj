(ns aoc-clj.2015.day12
  "Solution to https://adventofcode.com/2015/day/11"
  (:require [cheshire.core :as c]))

(def parse first)

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

(defn part1
  [input]
  (reduce + (all-numbers input)))

(defn part2
  [input]
  (->> (c/parse-string input)
       drop-red
       c/generate-string
       all-numbers
       (reduce +)))
