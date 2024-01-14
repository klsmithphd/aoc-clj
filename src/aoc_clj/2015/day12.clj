(ns aoc-clj.2015.day12
  "Solution to https://adventofcode.com/2015/day/11"
  (:require [cheshire.core :as c]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(def parse first)

;; Puzzle logic
(defn all-numbers
  "Simply read all the numbers out of the strings"
  [s]
  (map read-string (re-seq #"-?\d+" s)))

(defn all-numbers-total
  "Computes the sum of the numbers read out of the JSON docs"
  [input]
  (reduce + (all-numbers input)))

(defn drop-red
  "Drop any map (JSON object) that has any property whose value is `red`"
  [obj]
  (cond
    (map? obj)    (if (some #{"red"} (vals obj))
                    {}
                    (u/fmap drop-red obj))
    (vector? obj) (mapv drop-red obj)
    :else obj))

(defn JSON-without-red
  "Parse the JSON document string, strip out the objects with properties whose 
   value is red, and then re-emit as JSON string"
  [input]
  (-> input c/parse-string drop-red c/generate-string))

;; Puzzle solutions
(defn part1
  "Computes the sum of the numbers in the document"
  [input]
  (all-numbers-total input))

(defn part2
  "Computes the sum of the numbers in the doc after objects with properties
   whose values equal red are dropped"
  [input]
  (-> input JSON-without-red all-numbers-total))
