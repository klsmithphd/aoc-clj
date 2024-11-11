(ns aoc-clj.2018.day16
  "Solution to https://adventofcode.com/2018/day/16"
  (:require [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-stanza
  [stanza]
  (map #(mapv read-string (re-seq #"\d+" %)) stanza))

(defn parse
  [input]
  (->> (u/split-at-blankline input)
       (map parse-stanza)))