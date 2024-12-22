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
  "Returns the grid coordinate position of a node of a given type"
  [type grid]
  (->> (grid/pos-seq grid)
       (filter #(= type (grid/value grid %)))
       first))

(def start-node (partial find-node :start))
(def end-node   (partial find-node :end))

(defn shortest-path-score
  "Returns a path with the shortest distance between the start
   and end points of the maze."
  [grid]
  (let [graph   (->MoveGraph grid)
        start   {:pos (start-node grid) :heading :e}
        end     (end-node grid)
        finish? #(= end (:pos %))]
    (->> (graph/shortest-path graph start finish?)
         (graph/path-distance graph))))

(defn all-shortest-path-tile-count
  "Counts the number of tiles that appear in any possible
   shortest path from start to finish."
  [grid]
  (let [graph   (->MoveGraph grid)
        start   {:pos (start-node grid) :heading :e}
        end     (end-node grid)
        finish? #(= end (:pos %))]
    (->> (graph/all-shortest-paths true graph start finish?)
         (mapcat #(map :pos %))
         set
         count)))

;; Puzzle solutions
(defn part1
  "What is the lowest score a Reindeer could possibly get?"
  [input]
  (shortest-path-score input))

(defn part2
  "How many tiles are part of at least one of the best paths through the maze?"
  [input]
  (all-shortest-path-tile-count input))