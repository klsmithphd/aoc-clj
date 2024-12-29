(ns aoc-clj.2021.day15
  "Solution to https://adventofcode.com/2021/day/15"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]
            [aoc-clj.utils.graph :as g]
            [aoc-clj.2021.gridgraph :refer [->GridGraph]]))

(defn parse-line
  [line]
  (map (comp read-string str) line))

(defn parse
  [lines]
  (mapgrid/lists->MapGrid2D (map parse-line lines)))

(defn find-path-vals
  [{:keys [width height grid] :as input}]
  (let [start  [0 0]
        end    (u/equals? [(dec width) (dec height)])
        path (g/shortest-path (->GridGraph input) start end)]
    (map grid path)))

(defn path-risk
  [input]
  (reduce + (rest (find-path-vals input))))

(defn tiled-value
  [{:keys [width height grid]} [x y]]
  (let [tilex (quot x width)
        posx  (mod  x width)
        tiley (quot y height)
        posy  (mod  y height)
        raw   (get grid [posx posy])
        to-add (mod (+ tilex tiley) 9)
        adj   (+ raw to-add)]
    (if (>= adj 10) (mod adj 9) adj)))

(defn tile
  [{:keys [width height] :as input} count]
  (let [new-width  (* count width)
        new-height (* count height)
        coords (for [y (range new-height)
                     x (range new-width)]
                 [x y])]
    {:width new-width
     :height new-height
     :grid
     (zipmap coords (map (partial tiled-value input) coords))}))

(defn part1
  [input]
  (path-risk input))

(defn part2
  [input]
  (path-risk (tile input 5)))