(ns aoc-clj.2015.day02
  "Solution to https://adventofcode.com/2015/day/2"
  (:require [clojure.string :as str]))

(defn parse-line
  [line]
  (map read-string (str/split line #"x")))

(defn parse
  [input]
  (map parse-line input))

(defn wrapping-paper-area
  [[l w h]]
  (let [extra (take 2 (sort [l w h]))]
    (+ (* 2 l w) (* 2 l h) (* 2 w h) (apply * extra))))

(defn ribbon-length
  [[l w h]]
  (let [smallest (take 2 (sort [l w h]))]
    (+ (* 2 (reduce + smallest))
       (* l w h))))

(defn day02-part1-soln
  [input]
  (reduce + (map wrapping-paper-area input)))

(defn day02-part2-soln
  [input]
  (reduce + (map ribbon-length input)))