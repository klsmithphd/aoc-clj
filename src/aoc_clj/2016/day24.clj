(ns aoc-clj.2016.day24
  "Solution to https://adventofcode.com/2016/day/24"
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.graph :as g :refer [Graph]]
            [aoc-clj.utils.maze :as maze]))

;; Constants
(def charmap
  {\# :wall
   \. :space
   \0 :start
   \1 :stop1
   \2 :stop2
   \3 :stop3
   \4 :stop4
   \5 :stop5
   \6 :stop6
   \7 :stop7})

;; Input parsing
(defn grid->Maze
  [{:keys [grid]}]
  (maze/->Maze grid #(not= :wall %)))

(defn stops
  [{:keys [maze]}]
  (->> maze
       (remove #(#{:wall :space} (val %)))
       (map (comp vec reverse))
       (into {})))

(defn find-distance
  [maze stops [v1 v2]]
  (let [dist (dec (count (g/dijkstra maze (stops v1) (partial = (stops v2)) :limit 10000)))]
    [[v1 v2 dist]
     [v2 v1 dist]]))

(defn stop-graph
  [maze]
  (let [stops (stops maze)
        pairs (combo/combinations (keys stops) 2)]
    (->> (mapcat #(find-distance maze stops %) pairs)
         (group-by first)
         (u/fmap #(into {} (map (comp vec rest) %))))))

(defn parse
  [input]
  (->> (mg/ascii->MapGrid2D charmap input :down true)
       grid->Maze
       stop-graph))

;; Puzzle logic
(def start {:pos :start
            :visited #{:start}})

(defn newnode
  [state pos]
  (-> state
      (update :visited conj pos)
      (assoc :pos pos)))

(defrecord MoveGraph [gr]
  Graph
  (edges
    [_ v]
    (let [es (keys (gr (:pos v)))]
      (map #(newnode v %) es)))

  (distance
    [_ v1 v2]
    (get-in gr [(:pos v1) (:pos v2)])))

(defn shortest-path
  [graph part]
  (let [move-graph (->MoveGraph graph)
        finish? (case part
                  :part1 #(= (count graph) (count (:visited %)))
                  :part2 #(and (= (count graph) (count (:visited %)))
                               (= :start (:pos %))))]
    (->> (g/dijkstra move-graph start finish? :limit 100000)
         (g/path-distance move-graph))))

;; Puzzle solutions
(defn part1
  [input]
  (shortest-path input :part1))

(defn part2
  [input]
  (shortest-path input :part2))


