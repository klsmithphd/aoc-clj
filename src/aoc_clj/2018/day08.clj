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

(defn add-node
  "Process the next element of data to add a node to the tree, 
   recursively"
  [[n-children n-metadata & remaining]]
  (if (zero? n-children)
    {:children [] :metadata (take n-metadata remaining)}
    (loop [children [] shift 0]
      (if (= n-children (count children))
        {:children children
         :metadata (take n-metadata (drop shift remaining))}
        (let [next-child (add-node (drop shift remaining))
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

;; Puzzle solutions
(defn part1
  "What is the sum of all metadata entries?"
  [input]
  (metadata-sum (add-node input)))