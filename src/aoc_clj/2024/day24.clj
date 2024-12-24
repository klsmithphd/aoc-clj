(ns aoc-clj.2024.day24
  "Solution to https://adventofcode.com/2024/day/24"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-inputs
  [input]
  [(keyword (subs input 0 3)) (read-string (subs input 4))])

(defn parse-logic
  [logic]
  (let [[w1 op w2 _ w3] (str/split logic #" ")
        w1 (keyword w1)
        w2 (keyword w2)
        w3 (keyword w3)]
    [[w1 w2] [(keyword (str/lower-case op)) w3]]))

(defn parse
  [input]
  (let [[inputs logic] (u/split-at-blankline input)]
    {:init  (map parse-inputs inputs)
     :logic (into {} (map parse-logic logic))}))