(ns aoc-clj.2024.day23
  "Solution to https://adventofcode.com/2024/day/23"
  (:require [clojure.set :as set]
            [clojure.math.combinatorics :as combo]
            [clojure.string :as str]))

;; Input parsing
(defn parse-line
  [line]
  {(subs line 0 2) #{(subs line 3)}})

(defn parse
  [input]
  (->> (map parse-line input)
       (apply merge-with into)))

;; Puzzle logic
(defn symmetrize
  "Update the graph such that all the nodes connected to node k
   also have node k contained among their connections"
  [graph [k nodes]]
  (reduce #(update %1 %2 conj k) graph nodes))

(defn symmetric
  "Update the graph such that all node connections appear in
   both directions"
  [graph]
  (reduce symmetrize graph graph))

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
       symmetric
       three-node-networks
       (filter contains-t?)
       count))

;; Puzzle solutions
(defn part1
  "How many contain at least one computer with a name that starts with t?"
  [input]
  (three-node-networks-with-t input))