(ns aoc-clj.2024.day18
  "Solution to https://adventofcode.com/2024/day/18"
  (:require [clojure.string :as str]
            [aoc-clj.utils.graph :as graph :refer [Graph]]
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
  "Returns the length of the shortest path from [0,0] to [size-1,size-1],
   after the first `limit` bytes from `bytes` have fallen into the grid"
  [size limit bytes]
  (let [graph   (->MoveGraph size (set (take limit bytes)))
        start   [0 0]
        finish? #(= [(dec size) (dec size)] %)]
    (dec (count (graph/shortest-path graph start finish?)))))

(defn first-blocking-byte
  "Returns the first byte to fall that completely blocks the path
   to the exit, for a square grid of size `size`, starting from
   taking the first `start-limit` bytes, and then the coordinates
   of the falling bytes"
  [size start-limit bytes]
  (->> (range start-limit (count bytes))
       (remove #(pos? (shortest-path size % bytes)))
       first
       dec
       (nth bytes)
       (str/join ",")))

;; Puzzle solutions
(defn part1
  "Simulate the first kilobyte (1024 bytes) falling onto your memory space.
   Afterward, what is the minimum number of steps needed to reach the exit?"
  [input]
  (shortest-path grid-size byte-limit input))

(defn part2
  "What are the coordinates of the first byte that will prevent the exit
   from being reachable from your starting position? "
  [input]
  (first-blocking-byte grid-size byte-limit input))
