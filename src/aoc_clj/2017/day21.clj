(ns aoc-clj.2017.day21
  "Solution to https://adventofcode.com/2017/day/21"
  (:require [clojure.string :as str]))

;; Constants
(def start
  "Initial pattern   
   ```
   .#.
   ..#
   ###
   ```"
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

;; Puzzle logic
(defn size
  [rule]
  (if (= 4 (count rule)) 2 3))


(defn flip-h
  [match]
  (let [size (size match)]
    (->> match
         (partition size)
         (map reverse)
         (apply concat))))

(defn flip-v
  [match]
  (let [size (size match)]
    (->> match
         (partition size)
         reverse
         (apply concat))))

(defn rotate
  [match])


(defn equivalent-matches
  [[match pattern]]
  (zipmap
   [match
    (flip-h match)
    (flip-v match)
    (rotate match)
    (rotate (rotate match))
    (rotate (rotate (rotate match)))]
   (repeat pattern)))