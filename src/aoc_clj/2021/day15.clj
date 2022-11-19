(ns aoc-clj.2021.day15
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]
            [aoc-clj.utils.graph :as g]
            [aoc-clj.2021.gridgraph :refer [->GridGraph]]))

(defn parse-line
  [line]
  (map (comp read-string str) line))

(defn parse
  [lines]
  (map parse-line lines))

(def day15-sample
  (mapgrid/lists->MapGrid2D
   (parse
    ["1163751742"
     "1381373672"
     "2136511328"
     "3694931569"
     "7463417111"
     "1319128137"
     "1359912421"
     "3125421639"
     "1293138521"
     "2311944581"])))

(def day15-input (mapgrid/lists->MapGrid2D (parse (u/puzzle-input "2021/day15-input.txt"))))

(defn find-path-vals
  [{:keys [width height grid] :as input}]
  (let [start  [0 0]
        end    [(dec width) (dec height)]
        path (g/dijkstra (->GridGraph input) start end)]
    (map grid path)))

(defn path-risk
  [input]
  (reduce + (rest (find-path-vals input))))

(defn day15-part1-soln
  []
  (path-risk day15-input))

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

(defn day15-part2-soln
  []
  (path-risk (tile day15-input 5)))