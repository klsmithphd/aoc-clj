(ns aoc-clj.2019.day18
  (:require [clojure.data.priority-map :refer [priority-map]]
            [clojure.set :as set]
            [clojure.string :as str]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.graph :as g :refer [Graph
                                               map->MapGraph
                                               edges
                                               vertices]]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]
            [aoc-clj.utils.maze :as maze :refer [map->Maze]]))
;; FIXME: This implementation is too complex and not sufficiently documented
;; https://github.com/Ken-2scientists/aoc-clj/issues/19

(def day18-input (vec (u/puzzle-input "2019/day18-input.txt")))

(defn maze-map
  [char]
  (let [s (str char)]
    (case s
      "." :open
      "#" :wall
      "@" :entrance
      s)))

; TODO - think about when to cull the dead ends from the maze
(defn load-maze
  [maze]
  (let [themaze (:grid (mapgrid/ascii->MapGrid2D maze-map maze :down true))
        entrances (map first (filter #(= :entrance (val %)) themaze))
        specials (into {} (filter #(not (keyword? (val %))) themaze))
        keys (map first (filter #(re-find #"[a-z]" (val %)) specials))
        doors (map first (filter #(re-find #"[A-Z]" (val %)) specials))
        nodes (concat entrances keys doors)]
    (map->Maze
     {:maze themaze
      :open? (partial not= :wall)
      :entrances (zipmap entrances (map str (range)))
      :keys (map themaze keys)
      :doors (map themaze doors)
      :nodes nodes})))

(defn unique-label
  [{:keys [maze entrances]} [x y]]
  (let [foo (maze [x y])]
    (if (keyword? foo)
      (if (= :entrance foo)
        (entrances [x y])
        (str "_" x "," y))
      foo)))

(defn summarize-path
  [path]
  [(first path) {(last path) (dec (count path))}])

; Consider merging this back into the graph namespace
(defn adjacencies
  [{:keys [nodes] :as maze}]
  (let [leaves    (filter (partial g/leaf? maze) (vertices maze))
        junctions (filter (partial g/junction? maze) (vertices maze))
        vs (concat leaves junctions nodes)]
    (->> (mapcat #(g/all-paths maze % :excludes nodes) vs)
         (map #(map (partial unique-label maze) %))
         (map summarize-path)
         (group-by first)
         (u/fmap #(apply merge (map second %))))))

(defn load-graph
  [{:keys [entrances keys doors] :as maze}]
  (let [ents  (vals entrances)
        graph (map->MapGraph {:graph (adjacencies maze)
                              :keys keys
                              :doors doors
                              :entrances ents})
        excludes (set (concat keys doors ents))]
    (g/pruned graph excludes)))

(defn door?
  [{:keys [doors]} vertex]
  ((set doors) vertex))

(defn key?
  [{:keys [keys]} vertex]
  ((set keys) vertex))

(defn door-or-key?
  [graph vertex]
  (or (door? graph vertex) (key? graph vertex)))

(defn all-routes-for-node
  [graph vertices node]
  (let [others (filter #(not= node %) vertices)]
    (zipmap others (map (partial g/shortest-distance graph node)
                        (map u/equals? others)))))

(defn fully-connected-keys-single
  [{:keys [keys entrances] :as graph}]
  (let [vertices (concat keys entrances)]
    (zipmap vertices (map (partial all-routes-for-node graph vertices) vertices))))

(defn path-needs
  [graph node1 node2]
  (let [path (g/dijkstra graph node1 (u/equals? node2))]
    (set (map str/lower-case (filter (partial door-or-key? graph) (butlast path))))))

(defn subgraph-needs
  [{:keys [keys entrances] :as graph}]
  (let [needs (zipmap keys
                      (map (partial path-needs graph (first entrances)) keys))]
    needs))

(defn subgraph
  [{:keys [keys doors] :as graph} entrance]
  (let [reachable (set (g/reachable graph entrance (constantly false)))]
    (assoc graph
           :graph (select-keys (:graph graph) (concat [entrance] reachable))
           :keys  (filter reachable keys)
           :doors (filter reachable doors)
           :entrances (list entrance))))

(defn new-location
  [key-index locations node]
  (assoc locations (key-index node) node))

(defrecord LockedGraph [graph key-index needs]
  Graph
  (vertices
    [_]
    (mapcat keys graph))

  (edges
    [this v]
    (let [[collected-keys locations] v
          all-available (into collected-keys locations)
          reachable (set (mapcat second (filter #(set/superset? all-available (first %)) needs)))
          not-yet-visited (set/difference (set (vertices this)) all-available)
          destinations (map (partial new-location key-index locations) (set/intersection not-yet-visited reachable))]
      (map (partial vector all-available) destinations)))

  (distance
    [_ [_ v1] [_ v2]]
    (let [[n1 n2] (first (filter (fn [[a b]] (not= a b)) (map vector v1 v2)))
          d (get-in graph [(key-index n1) n1 n2])]
      (if (nil? d) 1000000 d))))

(defn fully-connected-keys
  [{:keys [entrances] :as graph}]
  (let [subgraphs (map (partial subgraph graph) entrances)
        newgraph (mapv fully-connected-keys-single subgraphs)
        needs (apply merge (map subgraph-needs subgraphs))
        key-index (into {} (mapcat (fn [x i] (map vector (keys x) (repeat i))) newgraph (range)))]
    (map->LockedGraph
     (assoc graph
            :graph newgraph
            :key-index key-index
            :needs (group-by needs (keys needs))))))

(defn locked-dijkstra
  [{:keys [entrances] :as graph}]
  (let [max-keys (count (vertices graph))
        start [(set entrances) (vec entrances)]
        init-state {:dist (priority-map start 0) :prev {}}]
    (loop [visited #{}
           vertex start
           state init-state]
      (if (= max-keys (inc (count (first vertex))))
        (g/path-retrace (:prev state) vertex)
        (let [neighbors (filter (complement visited) (edges graph vertex))
              new-state (reduce (partial g/dijkstra-update graph vertex) state neighbors)]
          (recur (conj visited vertex)
                 (ffirst (g/entries-not-in-set visited (state :dist)))
                 new-state))))))

(defn shortest-path
  [input]
  (let [graph (fully-connected-keys input)
        path (locked-dijkstra graph)]
    (g/path-distance graph path)))

(defn day18-part1-soln
  []
  (shortest-path (load-graph (load-maze day18-input))))

(defn fix-maze
  [{:keys [entrances maze nodes] :as input}]
  (let [[x y] (first (keys entrances))
        newents [[(dec x) (dec y)]
                 [(dec x) (inc y)]
                 [(inc x) (dec y)]
                 [(inc x) (inc y)]]
        fixedmaze (assoc maze
                         [x y] :wall
                         [(dec x) y] :wall
                         [(inc x) y] :wall
                         [x (dec y)] :wall
                         [x (inc y)] :wall
                         [(dec x) (dec y)] :entrance
                         [(dec x) (inc y)] :entrance
                         [(inc x) (dec y)] :entrance
                         [(inc x) (inc y)] :entrance)]
    (assoc input
           :maze fixedmaze
           :nodes (into (filter (partial not= [x y]) nodes) newents)
           :entrances (zipmap newents (map str (range))))))

(defn day18-part2-soln
  []
  (shortest-path (load-graph (fix-maze (load-maze day18-input)))))