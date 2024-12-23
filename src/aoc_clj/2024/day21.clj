(ns aoc-clj.2024.day21
  "Solution to https://adventofcode.com/2024/day/20"
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.graph :as graph :refer [Graph]]
            [aoc-clj.utils.core :as u]))

;; Constants
(def part1-layers 2)
(def part2-layers 25)

(def numeric-keypad
  {\A {\0 \<
       \3 \^}
   \0 {\A \>
       \2 \^}
   \1 {\4 \^
       \2 \>}
   \2 {\0 \v
       \1 \<
       \3 \>
       \5 \^}
   \3 {\A \v
       \2 \<
       \6 \^}
   \4 {\1 \v
       \5 \>
       \7 \^}
   \5 {\2 \v
       \4 \<
       \6 \>
       \8 \^}
   \6 {\3 \v
       \5 \<
       \9 \^}
   \7 {\4 \v
       \8 \>}
   \8 {\5 \v
       \7 \<
       \9 \>}
   \9 {\6 \v
       \8 \<}})

(def directional-keypad
  {\< {\v \>}
   \v {\< \<
       \^ \^
       \> \>}
   \^ {\A \>
       \v \v}
   \> {\v \<
       \A \^}
   \A {\^ \<
       \> \v}})

;; Records
(defrecord MoveGraph [keypad]
  Graph
  (vertices [_]     (keys keypad))
  (edges    [_ v]   (keys (keypad v)))
  (distance [_ _ _] 1))

;; Input parsing
(def parse identity)

;; Puzzle logic
(defn buttons->moves
  "For the given `keypad` graph, and a `path` of buttons to move through,
   returns the directional instructions required."
  [keypad path]
  (let [moves (->> (partition 2 1 path)
                   (map #(get-in keypad %))
                   (filter some?))]
    (apply str (concat moves [\A]))))

(defn button-paths
  "For a given `keypad`, the `graph` representation of indivudal moves,
   and any two given buttons `a` and `b`, return the set of moves to
   get from `a` to `b`"
  [keypad graph [a b]]
  (->> (graph/all-shortest-paths true graph a (u/equals? b))
       (mapv #(buttons->moves keypad %))))

(defn keypad-paths
  "For a given `keypad`, constructs a lookup map of all possible moves
   to get from one button to another."
  [keypad]
  (let [graph (->MoveGraph keypad)
        pairs (->> (combo/cartesian-product (keys keypad) (keys keypad))
                   (map vec))]
    (->> (map #(vector % (button-paths keypad graph %)) pairs)
         (into {}))))

(defn all-alternatives
  [moves]
  (loop [options (first moves) rst (next moves)]
    (if (nil? rst)
      options
      (recur (mapcat #(map (fn [x] (str % x)) (first rst)) options)
             (next rst)))))

(defn directions
  [move-map coll]
  (->> (concat [\A] coll)
       (partition 2 1)
       (map move-map)
       all-alternatives))

(def robot-dirs  (partial directions (keypad-paths numeric-keypad)))
(def remote-dirs (partial directions (keypad-paths directional-keypad)))

(defn cull
  [coll]
  (let [min-size (apply min (map count coll))]
    (remove #(> (count %) min-size) coll)))

(defn remote-layer
  [input]
  (->> (mapcat remote-dirs input)
       cull))

(defn remote-layers
  [layers input]
  (->> input
       (iterate remote-layer)
       (drop layers)
       first))

(defn all-presses
  [layers code]
  (->> code
       robot-dirs
       cull
       (remote-layers layers)
       (apply min-key count)))

(defn seq-length
  [layers code]
  (count (all-presses layers code)))

(defn numeric-part
  [code]
  (Integer/parseInt (subs code 0 3)))

(defn complexity
  [layers code]
  (* (seq-length layers code) (numeric-part code)))

(defn complexity-sum
  [layers codes]
  (->> codes
       (map #(complexity layers %))
       (reduce +)))

;; Puzzle solutions
(defn part1
  [input]
  (complexity-sum part1-layers input))

(defn part2
  [input]
  (complexity-sum part2-layers input))