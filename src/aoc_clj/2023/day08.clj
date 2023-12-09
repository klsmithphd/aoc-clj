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
    {:instructions (map {\L :left \R :right} (ffirst chunks))
     :nodes (parse-nodes (first (rest chunks)))}))

(defn steps-to-zzz
  [{:keys [instructions nodes]}]
  (loop [node "AAA" steps 0 insts (cycle instructions)]
    (if (= "ZZZ" node)
      steps
      (recur (get-in nodes [node (first insts)])
             (inc steps)
             (rest insts)))))

(defn day08-part1-soln
  [input]
  (steps-to-zzz input))