(ns aoc-clj.2023.day08
  (:require [aoc-clj.utils.core :as u]))

(defn parse-node
  [s]
  (let [[node left right] (re-seq #"\w{3}" s)]
    [node {:left left :right right}]))

(defn parse-nodes
  [nodes]
  (into {} (map parse-node nodes)))

(defn parse
  [input]
  (let [chunks (u/split-at-blankline input)]
    {:instructions (ffirst chunks)
     :nodes (parse-nodes (first (rest chunks)))}))