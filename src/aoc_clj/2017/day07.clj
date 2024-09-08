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
  [nodes]
  (let [parents (filter #(seq (:children (val %))) nodes)
        children (into #{} (mapcat :children (vals parents)))]
    (first (remove children (keys parents)))))

(def node-weight
  (memoize
   (fn [graph node]
     (let [{:keys [weight children]} (graph node)]
       (->> (map #(node-weight graph %) children)
            (reduce +)
            (+ weight))))))

(defn odd-duck
  [weights]
  (let [foo (->> (group-by first weights)
                 (u/fmap #(map second %)))
        [anomaly [node]] (first (filter #(= 1 (count (val %))) foo))
        target (first (keys (u/without-keys foo [anomaly])))]
    [node (- target anomaly)]))

(defn corrected-weight
  ([graph]
   (corrected-weight graph (root-node graph) 0))
  ([graph root delta]
   (let [{:keys [weight children]} (graph root)
         child-weights (map #(vector (node-weight graph %) %) children)]
     (if (apply = (map first child-weights))
       (+ delta weight)
       (let [[child new-delta] (odd-duck child-weights)]
         (corrected-weight graph child new-delta))))))

;; Puzzle solutions
(defn part1
  [input]
  (root-node input))

(defn part2
  [input]
  (corrected-weight input))