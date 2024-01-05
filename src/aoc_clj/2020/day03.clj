(ns aoc-clj.2020.day03
  "Solution to https://adventofcode.com/2020/day/3"
  (:require [aoc-clj.utils.grid.mapgrid :as mapgrid]))

(def parse identity)

(defn forest-basemap
  [ascii-lines]
  (let [forest-mapping {\. :space
                        \# :tree}]
    (mapgrid/ascii->MapGrid2D forest-mapping ascii-lines :down true)))

(defn get-position
  [{:keys [height width grid]} [x y]]
  (let [realx (mod x width)
        realy (mod y height)]
    (get grid [realx realy])))

(defn items-along-slope
  [{:keys [height] :as basemap} [right down]]
  (let [positions (for [step (range 0 (/ height down))]
                    [(* step right) (* step down)])]
    (map (partial get-position basemap) positions)))

(defn trees-along-slope
  [basemap slope]
  (->> (items-along-slope basemap slope)
       (filter #(= :tree %))
       count))

(defn trees-along-slopes
  [basemap slopes]
  (map (partial trees-along-slope basemap) slopes))

(defn part1
  [input]
  (let [basemap (forest-basemap input)
        slope   [3 1]]
    (trees-along-slope basemap slope)))

(defn part2
  [input]
  (let [basemap (forest-basemap input)
        slopes  [[1 1] [3 1] [5 1] [7 1] [1 2]]]
    (reduce * (trees-along-slopes basemap slopes))))