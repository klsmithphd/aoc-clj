(ns aoc-clj.2017.day21
  "Solution to https://adventofcode.com/2017/day/21"
  (:require [clojure.string :as str]))

;; Constants
(def start
  "Initial pattern   
   .#.
   ..#
   ###"
  [0 1 0
   0 0 1
   1 1 1])

(def charmap {\. 0 \# 1})

;; Input parsing
(defn parse-line
  [line]
  (let [[l r] (-> line
                  (str/replace "/" "")
                  (str/split #" => "))]
    [(map charmap l) (map charmap r)]))

(defn parse
  [input]
  (into {} (map parse-line input)))