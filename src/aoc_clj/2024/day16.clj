(ns aoc-clj.2024.day16
  "Solution to https://adventofcode.com/2024/day/16"
  (:require [aoc-clj.utils.graph :as graph :refer [Graph]]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mg]))

;; Records
(defrecord MoveGraph [grid]
  Graph
  (vertices
    [_]
    (->> (grid/pos-seq grid)
         (remove #{:wall})))

  (edges
    [_ v]
    (if (not= :wall (grid/value grid (:pos (grid/forward v 1))))
      [(grid/forward v 1) (grid/turn v :right) (grid/turn v :left)]
      [(grid/turn v :right) (grid/turn v :left)]))

  (distance
    [_ v1 v2]
    (if (= (:pos v1) (:pos v2))
      1000
      1)))

;; Input parsing
(def charmap
  {\# :wall
   \. :space
   \S :start
   \E :end})

(defn parse
  [input]
  (mg/ascii->MapGrid2D charmap input))

;; Puzzle logic
(defn find-node
  [type grid]
  (->> (grid/pos-seq grid)
       (filter #(= type (grid/value grid %)))
       first))

(def start-node (partial find-node :start))
(def end-node   (partial find-node :end))

(defn shortest-path
  [grid]
  (let [graph   (->MoveGraph grid)
        start   {:pos (start-node grid) :heading :e}
        end     (end-node grid)
        finish? #(= end (:pos %))]
    (graph/dijkstra graph start finish? :limit 100000)))

(defn score
  [[s1 s2]]
  (if (= (:pos s1) (:pos s2))
    1000
    1))

(defn path-score
  [grid]
  (->> (shortest-path grid)
       (partition 2 1)
       (map score)
       (reduce +)))

;; Puzzle solutions
(defn part1
  [input]
  (path-score input))