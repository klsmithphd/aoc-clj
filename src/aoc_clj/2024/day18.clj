(ns aoc-clj.2024.day18
  "Solution to https://adventofcode.com/2024/day/18"
  (:require [aoc-clj.utils.graph :as graph :refer [Graph]]
            [aoc-clj.utils.grid :as grid]))

;; Constants
(def grid-size 71)
(def byte-limit 1024)

;; Records
(defrecord MoveGraph [size corrupted]
  Graph
  (vertices
    [_]
    (->> (for [y (range size)
               x (range size)]
           [x y])
         (remove corrupted)))

  (edges
    [_ [x y :as v]]
    (if (nil? v)
      nil
      (->> (grid/adj-coords-2d [x y])
           (filter (fn [[a b]]
                     (and (< -1 a size)
                          (< -1 b size))))
           (remove corrupted))))

  (distance
    [_ _ _]
    1))

;; Input parsing
(defn parse-line
  [line]
  (map read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn shortest-path
  [size limit bytes]
  (let [graph   (->MoveGraph size (set (take limit bytes)))
        start   [0 0]
        finish? #(= [(dec size) (dec size)] %)]
    (dec (count (graph/dijkstra graph start finish?)))))

;; Puzzle solutions
(defn part1
  [input]
  (shortest-path grid-size byte-limit input))
