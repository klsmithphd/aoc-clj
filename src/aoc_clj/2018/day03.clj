(ns aoc-clj.2018.day03
  "Solution to https://adventofcode.com/2018/day/3"
  (:require [clojure.set :as set]))

;; Input parsing
(defn parse-line
  [line]
  (zipmap
   [:id :x :y :w :h]
   (->> (re-seq #"\d+" line)
        (map read-string))))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn swath-grid
  "Returns the full set of cells represented by the swath"
  [{:keys [x y w h]}]
  (for [i (range x (+ x w))
        j (range y (+ y h))]
    [i j]))

(defn swath-grid-with-ids
  "Returns a map of swath squares to swath id"
  [{:keys [id] :as swath}]
  (zipmap (swath-grid swath) (repeat [id])))

(defn overlapping-squares
  "Returns the count of squares that occur in more than one claim"
  [swaths]
  (->> swaths
       (mapcat swath-grid)
       frequencies
       (filter #(> (val %) 1))
       count))

(defn overlapping-swath-ids
  "Returns the set of ids of swaths that overlap with any other swath"
  [swaths]
  (->> swaths
       (map swath-grid-with-ids)
       (apply merge-with into)
       vals
       (filter #(> (count %) 1))
       flatten
       set))

(defn all-swath-ids
  "Returns the set of all swath ids"
  [swaths]
  (set (map :id swaths)))

(defn nonoverlapping-swath
  "Returns the id of the swath that doesn't overlap with any other"
  [swaths]
  (let [all-ids     (all-swath-ids swaths)
        overlap-ids (overlapping-swath-ids swaths)]
    (first (set/difference all-ids overlap-ids))))

;; Puzzle solutions
(defn part1
  "How many square inches of fabric are within two or more claims?"
  [input]
  (overlapping-squares input))

(defn part2
  "What is the ID of the only claim that doesn't overlap?"
  [input]
  (nonoverlapping-swath input))


