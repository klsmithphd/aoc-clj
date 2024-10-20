(ns aoc-clj.2017.day21
  "Solution to https://adventofcode.com/2017/day/21"
  (:require [clojure.string :as str]
            [clojure.math :as math]))

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
  "Returns either 2 or 3 depending upon the size of a rule's key"
  [rule]
  (int (math/sqrt (count rule))))

(defn flip-h
  "Returns a rule pattern flipped horizontally."
  [rule]
  (let [size (size rule)]
    (->> rule
         (partition size)
         (map reverse)
         (apply concat)
         vec)))

(defn flip-v
  "Returns a rule pattern flipped vertically."
  [rule]
  (let [size (size rule)]
    (->> rule
         (partition size)
         reverse
         (apply concat)
         vec)))

(defn rotate
  "Returns a rule pattern rotated one quarter turn (counter-clockwise)"
  [rule]
  (let [size (size rule)]
    (case size
      2 (mapv rule [1 3 0 2])
      3 (mapv rule [2 5 8 1 4 7 0 3 6]))))

(defn equivalent-matches
  "Returns a new map of all the equivalent rule patterns"
  [[rule replacement]]
  (zipmap
   [rule
    (flip-h rule)
    (flip-v rule)
    (rotate rule)
    (rotate (rotate rule))
    (rotate (rotate (rotate rule)))]
   (repeat replacement)))

(defn full-rulebook
  "Returns an expanded rulebook with all of the unrepresented keys
   expressed"
  [rules]
  (into {} (map equivalent-matches rules)))