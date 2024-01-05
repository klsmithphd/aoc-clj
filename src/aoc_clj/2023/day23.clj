(ns aoc-clj.2023.day23
  (:require [aoc-clj.utils.graph :as graph :refer [vertex]]
            [aoc-clj.utils.grid :as grid :refer [width height in-grid?]]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.utils.maze :as maze]
            [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (vg/ascii->VecGrid2D identity input :down true))

(defn open?
  "Returns true if the cell is a valid neighbor to reach in the maze"
  [grid {:keys [pos val bearing]}]
  (and (in-grid? grid pos)
       (not= \# val)
       (not= :backward bearing)))

(defn upslope
  "The rules essentially disallow going in a direction opposite
   to one of these arrows"
  [{:keys [val heading]}]
  (or
   (and (= \> val) (= :w heading))
   (and (= \< val) (= :e heading))
   (and (= \v val) (= :s heading))
   (and (= \^ val) (= :n heading))))

(defn next-node
  [grid start]
  (loop [next-cell (maze/one-step start) path-length 1]
    (let [neighbors (maze/next-cells grid (partial open? grid) next-cell)]
      (if (or (> (count neighbors) 1) (empty? neighbors))
        [(map #(assoc % :pos (:pos next-cell)) neighbors)
         {(:pos next-cell) path-length}]
        (recur (first neighbors) (inc path-length))))))

(defn trace-maze
  [grid start]
  (loop [queue (u/queue [start])
         junctions {}
         visited #{start}]
    (if (not (seq queue))
      junctions
      (let [node           (peek queue)
            [new-nodes jn] (next-node grid node)
            trimmed-new-nodes (->> (remove upslope new-nodes)
                                   (map #(select-keys % [:pos :heading])))]
        (recur (into (pop queue) (remove visited trimmed-new-nodes))
               (update junctions (:pos node) merge jn)
               (into visited node))))))

(defn grid->graph
  "Construct a map of the edges between nodes in the maze, where
   a node is either the start/finish point or a junction where 
   multiple options are possible"
  [grid]
  (trace-maze grid {:pos [1 0] :heading :n}))

(defn finish
  "The finish cell is always in the the bottom right corner, two cells
   in from the edge"
  [grid]
  [(- (width grid) 2) (- (height grid) 1)])

(defn dfs-path-length
  "Helper function for performing a depth-first search (DFS) of a `graph`
   to find all the path lengths"
  [graph finish? node visited]
  (if (finish? node)
    [0]
    (for [[next_vertex distance] (->> (vertex graph node)
                                      (remove (comp visited key)))
          path_len (dfs-path-length graph finish? next_vertex (conj visited next_vertex))]
      (+ path_len distance))))

(defn longest-path
  "Of all possible paths from start to finish, compute the longest
   possible path"
  [graph start finish]
  (let [g (graph/->MapGraph graph)]
    (->> (dfs-path-length g (u/equals? finish) start #{start})
         (apply max))))

(defn longest-downslope-path
  "Find the longest path through the graph when disallowing 
   going uphill against a slope"
  [grid]
  (longest-path (grid->graph grid) [1 0] (finish grid)))

(defn full-graph
  "Take a directed graph and make it effectively an undirected
   graph by adding edges in the reverse direction"
  [graph]
  (merge-with merge graph (graph/reverse-graph graph)))

(defn longest-full-path
  "Returns the longest path for the full (bidirectional graph)"
  [grid]
  (longest-path (full-graph (grid->graph grid)) [1 0] (finish grid)))

(defn part1
  "Find the longest hike you can take through the hiking trails listed on 
   our map. How many steps long is the longest hike?"
  [input]
  (longest-downslope-path input))

(defn part2
  "Find the longest hike you can take through the surprisingly dry hiking 
   trails listed on your map. How many steps long is the longest hike?"
  [input]
  (longest-full-path input))