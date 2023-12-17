(ns aoc-clj.2023.day17
  (:require [clojure.string :as str]
            [aoc-clj.utils.graph :as g :refer [Graph]]
            [aoc-clj.utils.grid :as grid :refer [width height value]]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (u/str->vec (str/join " " line)))

(defn parse
  [input]
  (vg/->VecGrid2D (mapv parse-line input)))

(def allowed-headings
  {:R [:R :U :D]
   :L [:L :U :D]
   :U [:U :L :R]
   :D [:D :L :R]})

;; TODO - Copied from Day 16 -- should be a library fn
(defn next-cell
  "Returns the next cell from the position along the `heading` direction"
  [[x y] heading]
  (case heading
    :U [x (dec y)]
    :R [(inc x) y]
    :D [x (inc y)]
    :L [(dec x) y]))

;; TODO - Copied from Day 16 -- should be a library fn
(defn in-grid?
  "Returns true if the position is contained within the `grid`"
  [grid [x y]]
  (let [h (dec (height grid))
        w (dec (width grid))]
    (and (<= 0 x w) (<= 0 y h))))

(defn new-vertex
  [pos heading count]
  {:pos pos :heading heading :count count})

(defn next-vertices
  [grid {:keys [pos heading count]}]
  (let [headings  (allowed-headings heading)
        positions (map #(next-cell pos %) headings)
        counts    [(inc count) 0 0]
        vertices  (map new-vertex positions headings counts)]
    (->> vertices
         (filter #(in-grid? grid (:pos %)))
         (remove #(= 3 (:count %))))))

(defrecord CityGrid [grid]
  Graph
  (edges
    [_ v]
    (next-vertices grid v))
  (distance
    [_ _ {:keys [pos]}]
    (value grid pos)))

(defn min-heat-loss
  [grid]
  (let [ex (dec (width grid))
        ey (dec (height grid))
        graph (->CityGrid grid)]
    (->>
     (g/dijkstra graph
                 {:pos [0 0] :heading :R :count 0}
                 #(= [ex ey] (:pos %))
                 :limit 10000000)
     (drop 1)
     (map :pos)
     (map #(value grid %))
     (reduce +))))

(defn day17-part1-soln
  [input]
  (min-heat-loss input))
