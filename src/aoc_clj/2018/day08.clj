(ns aoc-clj.2018.day08
  "Solution to https://adventofcode.com/2018/day/8"
  (:require [aoc-clj.utils.core :as u]))

;; Input parsing
(def parse u/firstv)

;; Puzzle logic
(defn node-size
  "Computes the number of data elements the node occupies
   including its children, recursively"
  [{:keys [children metadata]}]
  (if (empty? children)
    (+ 2 (count metadata))
    (+ 2 (count metadata) (reduce + (map node-size children)))))

(defn parse-node
  "Process the next element of data to add a node to the tree, 
   recursively"
  [[n-children n-metadata & remaining]]
  (if (zero? n-children)
    {:children [] :metadata (take n-metadata remaining)}
    (loop [children [] shift 0]
      (if (= n-children (count children))
        {:children children
         :metadata (take n-metadata (drop shift remaining))}
        (let [next-child (parse-node (drop shift remaining))
              size (node-size next-child)]
          (recur (conj children next-child)
                 (+ shift size)))))))

(defn metadata-sum
  "Computes the sum of all the node's metadata and the metadata of
   it children, recursively"
  [{:keys [children metadata]}]
  (if (empty? children)
    (reduce + metadata)
    (+ (reduce + metadata)
       (reduce + (map metadata-sum children)))))

(defn node-value
  "Computes the value of a node in the tree. If the node has no children,
   the value is the sum of the metadata values. If the node has children,
   the metadata values are interpreted as 1-based indices into the
   list of children, and the node value is the the sum of those indexed
   children's node values."
  [{:keys [children metadata]}]
  (if (empty? children)
    (reduce + metadata)
    (->> (map #(get children (dec %) {:children [] :metadata []}) metadata)
         (map node-value)
         (reduce +))))

;; Puzzle solutions
(defn part1
  "What is the sum of all metadata entries?"
  [input]
  (metadata-sum (parse-node input)))

(defn part2
  "What is the value of the root node?"
  [input]
  (node-value (parse-node input)))