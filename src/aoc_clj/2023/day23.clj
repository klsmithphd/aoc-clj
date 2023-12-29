(ns aoc-clj.2023.day23
  (:require [aoc-clj.utils.graph :as graph]
            [aoc-clj.utils.grid :as grid :refer [width height in-grid?]]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.utils.maze :as maze]
            [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (vg/ascii->VecGrid2D identity input :down true))

(defn open?
  [grid {:keys [pos val heading bearing]}]
  (and (in-grid? grid pos)
       (not= \# val)
       (not= :backward bearing)
       (not (and (= \> val) (= :w heading)))
       (not (and (= \v val) (= :s heading)))))

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
         junctions {}]
    (if (not (seq queue))
      junctions
      (let [node           (peek queue)
            [new-nodes jn] (next-node grid node)]
        (recur (into (pop queue) new-nodes)
               (update junctions (:pos node) merge jn))))))

(defn possible-paths
  [grid]
  (let [start [1 0]
        grid-start {:pos start :heading :n}
        finish [(- (width grid) 2) (- (height grid) 1)]
        graph (graph/->MapGraph (trace-maze grid grid-start))]
    graph))