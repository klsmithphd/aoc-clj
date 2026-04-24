(ns aoc-clj.year-2021.day15
  "Solution to https://adventofcode.com/2021/day/15"
  (:require [aoc-clj.util.interface :as u]
            [aoc-clj.grid.interface :as mapgrid]
            [aoc-clj.graph.interface :as g]
            [aoc-clj.year-2021.gridgraph :refer [->GridGraph]]))

(defn parse-line
  [line]
  (map (comp read-string str) line))

(defn parse
  [lines]
  (mapgrid/lists->MapGrid2D (map parse-line lines)))

(defn find-path-vals
  [{:keys [width height grid-map] :as input}]
  (let [start  [0 0]
        end    (u/equals? [(dec height) (dec width)])
        path (g/shortest-path (->GridGraph input) start end)]
    (map grid-map path)))

(defn path-risk
  [input]
  (reduce + (rest (find-path-vals input))))

(defn tiled-value
  [{:keys [width height grid-map]} [row col]]
  (let [tilerow (quot row height)
        posrow  (mod  row height)
        tilecol (quot col width)
        poscol  (mod  col width)
        raw   (get grid-map [posrow poscol])
        to-add (mod (+ tilerow tilecol) 9)
        adj   (+ raw to-add)]
    (if (>= adj 10) (mod adj 9) adj)))

(defn tile
  [{:keys [width height] :as input} count]
  (let [new-width  (* count width)
        new-height (* count height)
        coords (for [row (range new-height)
                     col (range new-width)]
                 [row col])]
    {:width new-width
     :height new-height
     :grid-map
     (zipmap coords (map (partial tiled-value input) coords))}))

(defn part1
  [input]
  (path-risk input))

(defn part2
  [input]
  (path-risk (tile input 5)))