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

(def start
  "Initial starting state in the maze"
  {:pos :start
   :visited #{:start}})

;; Input parsing
(defn stops
  "Returns a mapping from the locations of interest to their coordinates
   in the grid"
  [{:keys [maze]}]
  (->> maze
       (remove #(#{:wall :space} (val %)))
       (map (comp vec reverse))
       (into {})))

(defn pair-distances
  "Computes the distance between any pair of points of interest.
   Returns a seq of two vecs, with the pair order reversed in the second vec"
  [maze stops [v1 v2]]
  (let [dist (dec (count (g/dijkstra maze (stops v1) (partial = (stops v2)) :limit 10000)))]
    [[v1 v2 dist]
     [v2 v1 dist]]))

(defn stop-graph
  "Given a maze with non-space points of interest, constructs a mapping
   that can be used as a directed graph between each point of interest
   and adjacent points"
  [maze]
  (let [stops (stops maze)
        pairs (combo/combinations (keys stops) 2)]
    (->> (mapcat #(pair-distances maze stops %) pairs)
         (group-by first)
         (u/fmap #(into {} (map (comp vec rest) %))))))

(defn parse
  [input]
  (-> (mg/ascii->MapGrid2D charmap input :down true)
      :grid
      (maze/->Maze #(not= :wall %))
      stop-graph))

;; Puzzle logic
(defn new-state
  "Given a possible neighbor to move to, construct a new state"
  [state pos]
  (-> state
      (update :visited conj pos)
      (assoc :pos pos)))

(defrecord MazeStateGraph [gr]
  Graph
  (edges
    [_ v]
    (let [es (keys (gr (:pos v)))]
      (map #(new-state v %) es)))

  (distance
    [_ v1 v2]
    (get-in gr [(:pos v1) (:pos v2)])))

(defn shortest-path
  "Find the shortest path to navigate the maze and visit each point of 
   interest. If part is set to `:part1`, returns the length of the shortest
   path to visit each POI. If set to `:part2`, returns the length of the
   shortest path to visit each POI and return to the start."
  [graph part]
  (let [move-graph (->MazeStateGraph graph)
        finish? (case part
                  :part1 #(= (count graph) (count (:visited %)))
                  :part2 #(and (= (count graph) (count (:visited %)))
                               (= :start (:pos %))))]
    (->> (g/dijkstra move-graph start finish? :limit 100000)
         (g/path-distance move-graph))))

;; Puzzle solutions
(defn part1
  "Length of shortest path to visit each location of interest"
  [input]
  (shortest-path input :part1))

(defn part2
  "Length of shortest path to visit each location of interest and return
   to the start"
  [input]
  (shortest-path input :part2))