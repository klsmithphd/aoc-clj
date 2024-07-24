(ns aoc-clj.utils.graph
  (:require [clojure.set :as set]
            [clojure.data.priority-map :refer [priority-map]]
            [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.core :as u]))

(defprotocol Graph
  (vertices [this] "A collection of all the vertices in the graph")
  (vertex [this v] "Any data/information/label associated with the given vertex in the graph")
  (edges [this v] "A collection of the edges for the given vertex in the graph")
  (distance [this v1 v2] "The distance (or edge weight) between two vertices")
  (without-vertex [this v] "Produces a new graph with the vertex removed")
  (rewired-without-vertex [this v] "Produces a new graph, re-wired to preserve the transitive edges through the removed vertex"))

(defrecord MapGraph [graph]
  Graph
  (vertices
    [_]
    (keys graph))

  (vertex
    [_ v]
    (graph v))

  (edges
    [_ v]
    (keys (graph v)))

  (distance
    [_ v1 v2]
    (get-in graph [v1 v2]))

  (without-vertex
    [g v]
    (let [neighbors (edges g v)
          newgraph (-> (reduce #(update %1 %2 dissoc v) graph neighbors)
                       (dissoc v))]
      (assoc g :graph newgraph)))

  (rewired-without-vertex
    [g v]
    (let [neighbors (edges g v)
          all-pairs (combo/permuted-combinations neighbors 2)
          newedge-fn (fn [g [v1 v2]]
                       (update-in g [:graph v1] assoc v2 (+ (distance g v1 v)
                                                            (distance g v v2))))]
      (without-vertex (reduce newedge-fn g all-pairs) v))))

(defrecord LabeledMapGraph [graph]
  Graph
  (vertices
    [_]
    (keys graph))

  (vertex
    [_ v]
    (graph v))

  (edges
    [_ v]
    (keys (:edges (graph v))))

  (distance
    [_ v1 v2]
    (get-in graph [:edges v1 v2]))

  (without-vertex
    [_ v]
    (let [neighbors (keys (:edges graph v))
          newgraph (-> (reduce #(update-in %1 [:edges %2] dissoc v) graph neighbors)
                       (dissoc v))]
      (->LabeledMapGraph newgraph))))

(defn degree
  "The degree of a vertex is the number of edges it has"
  [g v]
  (count (edges g v)))

(defn leaf?
  "Whether a vertex is a leaf vertex (meaning that it has at most one edge)"
  [g v]
  (= 1 (degree g v)))

(defn junction?
  "Whether a vertex is a junction (meaning that it has more than two edges)"
  [g v]
  (> (degree g v) 2))

(defn path-distance
  "Computes the distance along a path (an ordered collection of vertices)"
  [g path]
  (reduce + (map #(apply (partial distance g) %) (partition 2 1 path))))

;; (defn entries-in-set
;;   [s m]
;;   (filter (fn [[k _]] (s k)) m))

(defn entries-not-in-set
  [s m]
  (filter (fn [[k _]] ((complement s) k)) m))

(defn single-path
  "Return the only possible path traversal from the start vertex (presumed to be a leaf vertex)
   until reaching another leaf vertex or a vertex with more than one un-traversed edge"
  ([g v & {:keys [exclude]}]
   (loop [visited (if exclude [exclude v] [v])
          neighbors (if exclude (filter (complement #{exclude}) (edges g v)) (edges g v))]
     (if (or (> (count neighbors) 1) (= (count neighbors) 0))
       visited
       (recur (conj visited (first neighbors))
              (filter (complement (set visited)) (edges g (first neighbors))))))))

(defn single-path-2
  "Return the only possible path traversal from the start vertex (presumed to be a leaf vertex)
   until reaching another leaf vertex or a vertex with more than one un-traversed edge"
  ([g v s stop-at]
   (loop [visited [s v]
          neighbors (filter (complement #{s}) (edges g v))]
     (if (or (> (count neighbors) 1)
             (= (count neighbors) 0)
             (some? (stop-at (last visited))))
       visited
       (recur (conj visited (first neighbors))
              (filter (complement (set visited)) (edges g (first neighbors))))))))

(defn all-paths
  "Find all the paths from a vertex reaching a leaf vertex or a vertex with more than one
  untraversed edges"
  [g v & {:keys [excludes]}]
  (let [neighbors (edges g v)
        stop-at (if excludes (set excludes) #{})]
    (map #(single-path-2 g % v stop-at) neighbors)))

(defn dijkstra-update
  [graph vertex {:keys [dist prev queue] :as state} neighbor]
  (let [alt (+ (dist vertex) (distance graph vertex neighbor))]
    (if (or (nil? (dist neighbor)) (< alt (dist neighbor)))
      {:dist  (assoc dist neighbor alt)
       :queue (assoc queue neighbor alt)
       :prev  (assoc prev neighbor vertex)}
      state)))

(defn path-retrace
  [prev-steps finish]
  (reverse (take-while some? (iterate prev-steps finish))))

(defn dijkstra
  "Executes Dijkstra's algorithm to identify the shortest path in `graph`
   starting at `start`. The predicate `finish?` should return true when
   the destination vertex has been reached or false otherwise. A limit to the
   number of vertices considered can be optionally supplied using the `:limit`
   argument."
  [graph start finish? & {:keys [limit]}]
  (let [max-search (or limit (count (vertices graph)))
        init-state {:dist {start 0} :prev {} :queue (priority-map start 0)}]
    (loop [visited #{}
           visited-count 1
           vertex start
           state init-state]
      (if (or (= max-search visited-count) (finish? vertex))
        (path-retrace (state :prev) vertex)
        (let [neighbors (remove visited (edges graph vertex))
              new-state (-> (reduce (partial dijkstra-update graph vertex) state neighbors)
                            (update :queue dissoc vertex))]
          (recur
           (conj visited vertex)
           (if (visited vertex) visited-count (inc visited-count))
           (ffirst (:queue new-state))
           new-state))))))

(defn a-star-update
  [graph vertex h {:keys [dist prev queue] :as state} neighbor]
  (let [alt (+ (dist vertex) (distance graph vertex neighbor))]
    (if (or (nil? (dist neighbor)) (< alt (dist neighbor)))
      {:dist  (assoc dist neighbor alt)
       :queue (assoc queue neighbor (+ alt (h neighbor)))
       :prev  (assoc prev neighbor vertex)}
      state)))

(defn a-star
  "Executes the A* algorithm to identify the shortest path in `graph`
   starting at `start`. The predicate `finish?` should return true when the
   destination vertex has been reached or false otherwise. The heuristic
   function `h` should be a function of each vertex and should estimate
   the cost of reaching the target destination."
  [graph start finish? h]
  (let [init-state {:dist {start 0} :prev {} :queue (priority-map start (h start))}]
    (loop [vertex start state init-state]
      (if (or (finish? vertex) (empty? (:queue state)))
        (if (finish? vertex)
          (path-retrace (:prev state) vertex)
          ["No path was found"])
        (let [neighbors (edges graph vertex)
              new-state (-> (reduce #(a-star-update graph vertex h %1 %2) state neighbors)
                            (update :queue dissoc vertex))]
          (recur
           (ffirst (:queue new-state))
           new-state))))))

(defn shortest-distance
  [graph start finish?]
  (path-distance graph (dijkstra graph start finish?)))

(defn pruned
  "Prunes the single branches from a graph, excluding any vertices in the exclude-set"
  [graph exclude-set]
  (loop [newgraph graph]
    (let [dead-end-pred (every-pred (partial leaf? newgraph) (complement exclude-set))
          dead-ends (filter dead-end-pred (vertices newgraph))]
      (if (= 0 (count dead-ends))
        newgraph
        (recur (reduce without-vertex newgraph dead-ends))))))

(defn summarize-path
  [g path]
  [(first path) {(last path) (path-distance g path)}])

(defn adjacencies
  [graph]
  (let [leaves (filter (partial leaf? graph) (vertices graph))
        junctions (filter (partial junction? graph) (vertices graph))
        nodes (concat leaves junctions)]
    (->> (mapcat (partial all-paths graph) nodes)
         (map (partial summarize-path graph))
         (group-by first)
         (u/fmap #(apply merge (map second %))))))

(defn reachable
  [graph start stop-cond]
  (loop [visited #{start} explore (edges graph start)]
    (let [next-neighbors (filter (complement visited) explore)]
      (if (zero? (count next-neighbors))
        (disj visited start)
        (let [node (first next-neighbors)]
          (recur (conj visited node)
                 (if (stop-cond node) explore (concat explore (edges graph node)))))))))


(defn- dfs
  "Helper function for performing a depth-first search (DFS) of a `graph`"
  [graph finish? path visited]
  (let [node (peek path)]
    (if (finish? node)
      [path]
      (->> (edges graph node)
           (remove visited)
           (mapcat #(dfs graph finish? (conj path %) (conj visited %)))))))

(defn all-paths-dfs
  "Return a seq of all paths (if any) in a `graph` from `start` until 
   reaching a vertex satisfying the `finish?` predicate by using a 
   Depth-First Search (DFS)"
  [graph start finish?]
  (dfs graph finish? [start] #{start}))

(defn- unroll
  [[start nodes]]
  (map #(into [start] %) nodes))

(defn adjacency-list
  "Takes a graph represented as a map and converts it into an
   adjacency list representation"
  [graph]
  (mapcat unroll graph))

(defn- reverse-graph-helper
  [coll]
  (into {} (map (fn [[a _ b]] [a b]) coll)))

(defn reverse-graph
  "Takes a directed graph and returns a graph with all the edges
   going in the opposite direction"
  [graph]
  (->> (adjacency-list graph)
       (group-by second)
       (u/fmap reverse-graph-helper)))

(defn- advance-front
  "For a given _front_ of vertices in the `graph`, and already
   visited vertices `visited`, return the set of newly discovered
   vertices reachable in one edge traversal (i.e. nearest neighbors)"
  [graph front visited]
  (set/difference (set (mapcat #(edges graph %) front)) visited))

(defn flood-fill
  "For a given `graph`, starting at vertex `start`, return a set of
   distinct vertices that are reachable through traversing the graph
   edges. 
   
   If the optional `:limit` is provided, the traversal will only go
   up to `limit` edge traversals from the start before terminating."
  [graph start & {:keys [limit]}]
  (loop [front #{start} cnt 0 visited #{start}]
    (if (or (empty? front) (= cnt limit))
      visited
      (let [new-front (advance-front graph front visited)]
        (recur new-front (inc cnt) (set/union visited new-front))))))
