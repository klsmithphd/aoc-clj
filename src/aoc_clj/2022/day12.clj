(ns aoc-clj.2022.day12
  "Solution to https://adventofcode.com/2022/day/12"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.graph :as graph :refer [->MapGraph]]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]))

;;;; Input parsing

(defn translate [c]
  (case c
    \S -1
    \E 26
    (- (int c) (int \a))))

(defn parse
  [input]
  (mapgrid/lists->MapGrid2D (map (partial map translate) input)))

;;;; Puzzle logic

(defn find-matches
  "Given the `grid`, find the coordinates of the location with `value`"
  [{:keys [grid]} value]
  (map first (filter #(= value (second %)) grid)))

(defn transitions
  "Given the `grid` and a position `pos`, return a map of all the
   allowed moves, with the allowed move positions as keys and the value
   of 1 as the values (signifying the distance of the move)"
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
  (-> (graph/dijkstra g s (u/equals? e)) count dec))

(defn shortest-path-from-start
  "Find the fewest number of steps from the start to the finish given the map"
  [input]
  (let [start (first (find-matches input -1))
        end   (first (find-matches input 26))
        graph (grid->graph input)]
    (shortest-path-length graph start end)))

(defn shortest-path-from-any-a
  "Find the fewest number of steps from any grid cell labeled `a` to the finish 
   given the map"
  [input]
  (let [starts  (concat (find-matches input -1)
                        (find-matches input 0))
        end     (first (find-matches input 26))
        graph   (grid->graph input)
        lengths (remove neg? (map #(shortest-path-length graph % end) starts))]
    (apply min lengths)))

;;;; Puzzle solutions

(defn day12-part1-soln
  "What is the fewest steps required to move from your current position to the 
   location that should get the best signal?"
  [input]
  (shortest-path-from-start input))

(defn day12-part2-soln
  "What is the fewest steps required to move starting from any square with 
   elevation `a` to the location that should get the best signal?"
  [input]
  (shortest-path-from-any-a input))
