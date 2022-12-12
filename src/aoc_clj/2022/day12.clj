(ns aoc-clj.2022.day12
  "Solution to https://adventofcode.com/2022/day/12"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.graph :as graph :refer [->MapGraph]]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]))

(defn translate [c]
  (case c
    \S -1
    \E 26
    (- (int c) (int \a))))

(defn parse
  [input]
  (mapgrid/lists->MapGrid2D (map (partial map translate) input)))

(def d12-s01
  (parse
   ["Sabqponm"
    "abcryxxl"
    "accszExk"
    "acctuvwj"
    "abdefghi"]))

(def day12-input (parse (u/puzzle-input "2022/day12-input.txt")))

(defn find-matches
  "Given the `grid`, find the coordinates of the location with `value`"
  [{:keys [grid]} value]
  (map first (filter #(= value (second %)) grid)))

(def start (find-matches d12-s01 -1))
(def end   (find-matches d12-s01 26))

(defn transitions
  [grid pos]
  (let [v (get grid pos)
        candidates (grid/neighbors-2d grid pos)
        moves (keys (filter #(<= (- (val %) v) 1) candidates))]
    [pos (zipmap moves (repeat 1))]))

(defn grid->graph
  "Using the transition rules (that you can only move up at most one 
   level in height), construct a graph of the possible moves between
   grid locations"
  [{:keys [grid]}]
  (->MapGraph (into {} (map (partial transitions grid) (keys grid)))))

(defn shortest-path-length
  [g s e]
  (-> (graph/dijkstra g s e) count dec))

(defn shortest-path-from-start
  [input]
  (let [start (first (find-matches input -1))
        end   (first (find-matches input 26))
        graph (grid->graph input)]
    (shortest-path-length graph start end)))

(defn shortest-path-from-any-a
  [input]
  (let [starts  (concat (find-matches input -1)
                        (find-matches input 0))
        end     (first (find-matches input 26))
        graph   (grid->graph input)
        lengths (remove zero? (map #(shortest-path-length graph % end) starts))]
    (apply min lengths)))

(defn day12-part1-soln
  []
  (shortest-path-from-start day12-input))

(defn day12-part2-soln
  []
  (shortest-path-from-any-a day12-input))
