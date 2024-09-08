(ns aoc-clj.2017.day07
  "Solution to https://adventofcode.com/2017/day/7"
  (:require [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-line
  [line]
  (let [prgrms (re-seq #"[a-z]+" line)
        weight (read-string (re-find #"\d+" line))]
    [(first prgrms) {:weight weight :children (rest prgrms)}]))

(defn parse
  [input]
  (into {} (map parse-line input)))

;; Puzzle logic
(defn root-node
  "Given a tree, find the root node (a node which has no parent)"
  [tree]
  (let [parents (filter #(seq (:children (val %))) tree)
        children (into #{} (mapcat :children (vals parents)))]
    (first (remove children (keys parents)))))

(def node-weight
  "Compute the total weight of a node, which is the sum of its own
   weight and the combined weight of all of its descendants."
  (memoize
   (fn [graph node]
     (let [{:keys [weight children]} (graph node)]
       (->> (map #(node-weight graph %) children)
            (reduce +)
            (+ weight))))))

(defn odd-duck
  "Given a collection of node weight and id pairs, determine which node
   is the _odd duck_, i.e., the node whose weight doesn't match its siblings.
   
   Return a vec of the odd node and what its effective weight would need
   to be to match its siblings."
  [weights]
  (let [foo (->> (group-by first weights)
                 (u/fmap #(map second %)))
        [anomaly [node]] (first (filter #(= 1 (count (val %))) foo))
        target (first (keys (u/without-keys foo [anomaly])))]
    [node (- target anomaly)]))

(defn corrected-weight
  "Given a imbalanced tree, find the corrected weight of the single node
   that, if changed to the returned value, would balance the tree."
  ([tree]
   (corrected-weight tree (root-node tree) 0))
  ([tree root delta]
   (let [{:keys [weight children]} (tree root)
         child-weights (map #(vector (node-weight tree %) %) children)]
     (if (apply = (map first child-weights))
       (+ delta weight)
       (let [[child new-delta] (odd-duck child-weights)]
         (corrected-weight tree child new-delta))))))

;; Puzzle solutions
(defn part1
  "What is the name of the program at the bottom of the tower?"
  [input]
  (root-node input))

(defn part2
  "Given exactly one program has the wrong weight, what would its weight be
   to balance the tower?"
  [input]
  (corrected-weight input))