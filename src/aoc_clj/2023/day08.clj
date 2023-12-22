(ns aoc-clj.2023.day08
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.math :as math]))

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

(defn start-nodes
  "Find all the nodes in the input data that end with 'A'"
  [nodes]
  (filter #(str/ends-with? % "A") (keys nodes)))

(defn finished?
  "The end target is a node that ends in 'Z'"
  [node]
  (str/ends-with? node "Z"))

(defn steps-to-zzz
  "Apply each of the successive L/R instructions to navigate the map
   from the provided starting node until reaching a node ending in 'Z'. "
  [{:keys [instructions nodes]} start]
  (loop [node start steps 0 insts (cycle instructions)]
    (if (finished? node)
      steps
      (recur (get-in nodes [node (first insts)])
             (inc steps)
             (rest insts)))))

(defn ghost-steps-to-zzz
  "Finds the number of steps it takes for each starting position
   to reach its end and then finds the least common multiple of all
   those step counts"
  [{:keys [nodes] :as input}]
  (->> (map #(steps-to-zzz input %) (start-nodes nodes))
       (apply math/lcm)))

(defn day08-part1-soln
  "Starting at AAA, follow the left/right instructions.
   How many steps are required to reach ZZZ?"
  [input]
  (steps-to-zzz input "AAA"))

(defn day08-part2-soln
  "Simultaneously start on every node that ends with A.
   How many steps does it take before you're only on nodes that end with Z?"
  [input]
  (ghost-steps-to-zzz input))