(ns aoc-clj.2024.day19
  "Solution to https://adventofcode.com/2024/day/19"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse
  [input]
  (let [[towels patterns] (u/split-at-blankline input)]
    {:towels (str/split (first towels) #", ")
     :patterns patterns}))
