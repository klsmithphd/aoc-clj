(ns aoc-clj.2024.day23
  "Solution to https://adventofcode.com/2024/day/23"
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str]
            [clojure.set :as set]))

;; Input parsing
(defn parse-line
  [line]
  (let [n1 (subs line 0 2)
        n2 (subs line 3)]
    ;; Because the graph is undirected, we emit the edges
    ;; in both directions
    [{n1 #{n2}} {n2 #{n1}}]))

(defn parse
  [input]
  (->> (mapcat parse-line input)
       (apply merge-with into)))

;; Puzzle logic
(defn node-triples
  "For two nodes and their respective sets of connected nodes,
   return all the possible mutually connected node triples"
  [[[n1 s1] [n2 s2]]]
  (if (s2 n1)
    (->> (set/intersection s1 s2)
         (remove #{n1 n2})
         (map #(set (list n1 n2 %))))
    #{}))

(defn three-node-networks
  "Returns a set of all the three-node (sub-)networks"
  [graph]
  (->> (combo/combinations graph 2)
       (mapcat node-triples)
       set))

(defn contains-t?
  "Returns true if any node in the network starts with a `t`"
  [networks]
  (some #(str/starts-with? % "t") networks))

(defn three-node-networks-with-t
  "Returns the count of all the three-node networks where any
   node starts with the letter `t`"
  [graph]
  (->> graph
       three-node-networks
       (filter contains-t?)
       count))

(defn maximal-intersection
  "Returns the neighbor and the set of mutually adjacent neighbors
   with the largest amount of overlap"
  [graph neighbors]
  (->> neighbors
       ;; We look for the set intersection of the current set of
       ;; neighbors, and the neighbors of each node within that set
       (map #(vector % (set/intersection (disj neighbors %) (graph %))))
       ;; Then pick the node-intersection combo that has the most elements
       ;; That's the largest addition to our candidate clique we can find.
       (apply max-key (comp count second))))

(defn clique-for-node
  "Finds the largest clique that includes the given node"
  [graph node]
  ;; We add the node itself to our candidate clique first,
  ;; and then look at its neighbors
  (loop [clique    #{node}
         neighbors (graph node)]
    (if (empty? neighbors)
      clique
      ;; We expand the clique by the neighbor with the greatest
      ;; number of mutually reachable next-neighbors
      ;; Those mutually reachable next-neighbors then become the
      ;; next group to consider for further expansion
      (let [[nxt others] (maximal-intersection graph neighbors)]
        (recur (conj clique nxt) others)))))

(defn cliques
  "Returns a set of the possible cliques discovered within the graph"
  [graph]
  (loop [explore-set (set (keys graph))
         cliques     #{}]
    (if (empty? explore-set)
      cliques
      (let [clique (clique-for-node graph (first explore-set))]
        (recur (set/difference explore-set clique)
               (conj cliques clique))))))

(defn largest-clique
  "Returns the largest clique (mutually interconnected subgraph)"
  [graph]
  (apply max-key count (cliques graph)))

(defn password
  "Returns the password: nodes sorted alphabetically and joined by commas"
  [nodes]
  (str/join "," (sort nodes)))

;; Puzzle solutions
(defn part1
  "How many contain at least one computer with a name that starts with t?"
  [input]
  (three-node-networks-with-t input))

(defn part2
  "What is the password to get into the LAN party?"
  [input]
  (password (largest-clique input)))