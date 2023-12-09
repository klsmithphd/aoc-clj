(ns aoc-clj.2023.day08
  (:require [aoc-clj.utils.core :as u]
            [clojure.string :as str]))

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

(defn start-nodes
  [nodes]
  (filter #(str/ends-with? % "A") (keys nodes)))

(defn finished?
  [nodes]
  (every? #(str/ends-with? % "Z") nodes))

(defn ghost-steps-to-zzz
  [{:keys [instructions nodes]}]
  (loop [locs (start-nodes nodes) steps 0 insts (cycle instructions)]
    (if (finished? locs)
      steps
      (recur (doall (map #(get-in nodes [% (first insts)]) locs))
             (inc steps)
             (next insts)))))

(defn next-locations
  [nodes locs inst]
  (map #(get-in nodes [% inst]) locs))

(defn day08-part1-soln
  "Starting at AAA, follow the left/right instructions.
   How many steps are required to reach ZZZ?"
  [input]
  (steps-to-zzz input))

(defn day08-part2-soln
  "Simultaneously start on every node that ends with A.
   How many steps does it take before you're only on nodes that end with Z?"
  [input]
  (ghost-steps-to-zzz input))