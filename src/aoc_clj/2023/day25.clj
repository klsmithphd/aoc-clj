(ns aoc-clj.2023.day25
  (:require [clojure.string :as str]
            [aoc-clj.utils.graph :as graph]
            [aoc-clj.utils.core :as u]
            [clojure.set :as set]))

(defn parse-line
  [line]
  (let [[l r] (str/split line #": ")]
    [l (str/split r #" ")]))

(defn parse
  [input]
  (into {} (map parse-line input)))

(defn flatten-graph-line
  [[a coll]]
  (map #(set [a %]) coll))

(defn flatten-graph
  [input]
  (mapcat flatten-graph-line input))

(defn graph-without-nodes
  [graph without-edges]
  (remove (set without-edges) graph))

(defn neighbors
  [graph node]
  (let [matches (->> (filter #(seq (set/intersection #{node} %)) graph)
                     (apply set/union))]
    (disj matches node)))

(defn connected-nodes
  [graph node]
  (loop [queue (u/queue [node])
         visited #{node}]
    (if (not (seq queue))
      (count visited)
      (recur (into (pop queue) (remove visited (neighbors graph (peek queue))))
             (conj visited (peek queue))))))

(defn clique-sizes
  [input to-remove]
  (let [graph (-> (flatten-graph input)
                  (graph-without-nodes to-remove))]
    (map (partial connected-nodes graph) (first to-remove))))

;; (defn graphviz-line
;;   [[a coll]]
;;   (map #(str a " -- " %) coll))

;; (defn- graphviz-rep
;;   [input]
;;   (str/join "\n" (mapcat graphviz-line input)))

;; Determined by plotting the graph with graphviz and seeing what links crossed
;; between the two clusters
(def day25-input-cuts
  [#{"mhb" "zqg"}
   #{"sjr" "jlt"}
   #{"fjn" "mzb"}])

(defn day25-part1-soln
  "Find the three wires you need to disconnect in order to divide the 
   components into two separate groups. What do you get if you multiply the 
   sizes of these two groups together?"
  [input]
  (reduce * (clique-sizes input day25-input-cuts)))
