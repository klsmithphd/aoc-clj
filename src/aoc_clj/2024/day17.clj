(ns aoc-clj.2024.day17
  "Solution to https://adventofcode.com/2024/day/17"
  (:require [aoc-clj.utils.core :as u]))

;; Input parsing
(defn get-nums
  [s]
  (map read-string (re-seq #"\d+" s)))

(defn parse
  [input]
  (let [[reg-str prog-str] (u/split-at-blankline input)]
    {:regs (mapcat get-nums reg-str)
     :prog (vec (get-nums (first prog-str)))}))