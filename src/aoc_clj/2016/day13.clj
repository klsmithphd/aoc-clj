(ns aoc-clj.2016.day13
  (:require [aoc-clj.utils.binary :as b]
            [aoc-clj.utils.graph :as g]
            [aoc-clj.utils.grid.mapgrid :as mapgrid :refer [->MapGrid2D]]
            [aoc-clj.utils.maze :as maze :refer [->Maze]]))

(def charmap
  {\. :open
   \# :wall})

(defn wall-calc
  [fav [x y]]
  (+ fav (* x x) (* 3 x) (* 2 x y) y (* y y)))

(defn cell
  [fav pos]
  (let [ones (-> (wall-calc fav pos)
                 b/int->bitstr
                 frequencies
                 (get \1))]
    (if (even? ones)
      :open
      :wall)))

(defn construct-grid
  [fav width height]
  (->MapGrid2D
   width
   height
   (into {}
         (for [y (range height)
               x (range width)]
           [[x y] (cell fav [x y])]))))

(defn path-distance
  [fav finish]
  (let [start [1 1]
        grid  (construct-grid fav 10 7)
        maze  (->Maze (:grid grid) #(= :open %))
        graph (-> maze maze/Maze->Graph (g/pruned #{start finish}))
        path  (g/dijkstra graph start finish)]
    (println graph)
    (g/path-distance graph path)))

;; (path-distance 10 [7 4])