(ns aoc-clj.2016.day24
  "Solution to https://adventofcode.com/2016/day/24"
  (:require [aoc-clj.utils.maze :as maze]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.graph :as g :refer [Graph edges distance]]))

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
(defn parse
  [input]
  (mg/ascii->MapGrid2D charmap input :down true))

(defn grid->Maze
  [{:keys [grid]}]
  (maze/->Maze grid #(not= :wall %)))

(def d24-s00-raw
  ["###########"
   "#0.1.....2#"
   "#.#######.#"
   "#4.......3#"
   "###########"])


(def thegraph
  {:start {:stop1 2 :stop4 2}
   :stop1 {:start 2 :stop2 6}
   :stop2 {:stop1 6 :stop3 2}
   :stop3 {:stop2 2 :stop4 8}
   :stop4 {:stop3 8 :start 2}})

(def start {:pos :start
            :visited #{:start}})

(defn finish?
  [{:keys [visited]}]
  (= (count visited) 5))

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
  [graph]
  (let [move-graph (->MoveGraph graph)
        finish? #(= (count graph) (count (:visited %)))]
    (->> (g/dijkstra move-graph start finish? :limit 100000)
         (g/path-distance move-graph))))

(shortest-path thegraph)



(def foo (->MoveGraph thegraph))

(newnode start :stop1)

(distance foo start (second (edges foo start)))






