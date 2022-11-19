(ns aoc-clj.2015.day02
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (map read-string (str/split line #"x")))

(def day02-input
  (map parse-line (u/puzzle-input "2015/day02-input.txt")))

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
  []
  (reduce + (map wrapping-paper-area day02-input)))

(defn day02-part2-soln
  []
  (reduce + (map ribbon-length day02-input)))