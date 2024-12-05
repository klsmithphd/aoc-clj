(ns aoc-clj.2024.day05
  "Solution to https://adventofcode.com/2024/day/5"
  (:require [aoc-clj.utils.core :as u]))

;; Input parsing
(defn numbers
  [line]
  (map read-string (re-seq #"\d+" line)))

(defn parse-updates
  [updates]
  (map numbers updates))

(defn parse-ordering
  [ordering]
  (->> (map numbers ordering)
       (group-by first)
       (u/fmap #(set (map second %)))))

;; Input parsing
(defn parse
  [input]
  (let [[ordering updates] (u/split-at-blankline input)]
    {:ordering (parse-ordering ordering)
     :updates  (parse-updates updates)}))