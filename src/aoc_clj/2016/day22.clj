(ns aoc-clj.2016.day22
  "Solution to https://adventofcode.com/2016/day/22"
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.graph :as g :refer [Graph]]
            [aoc-clj.utils.grid :as grid]))

;; Input parsing
(defn parse-line
  [line]
  (let [[x y size used avail usepct] (map read-string (re-seq #"\d+" line))]
    {:pos [x y]
     :size size
     :used used
     :avail avail
     :usepct usepct}))

(defn parse
  [input]
  (map parse-line (drop 2 input)))

;; Puzzle logic
(defn viable-pair?
  "Is a node pair viable?"
  [[a b]]
  (and
   ;; Node A and B are not the same node
   (not= (:pos a) (:pos b))
   ;; Node A is not empty (its Used is not zero)
   (pos? (:used a))
   ;; The data on Node A (its Used) would fit on node B (its Avail)
   (<= (:used a) (:avail b))))

(defn viable-pair-count
  "Counts the number of viable pairs there are"
  [nodes]
  (->> (combo/permuted-combinations nodes 2)
       (filter viable-pair?)
       count))

(defn max-x-pos
  "Find the maximum x position value in the grid"
  [nodes]
  (->> (map (comp first :pos) nodes)
       (apply max)))

(defn goal-data-move-count
  "Total number of data moves required to shift the goal
   data to the starting node presuming that the empty node has reached
   the goal node and there are no obstacle nodes in the first two
   rows of the grid"
  [nodes]
  (->> (max-x-pos nodes)
       dec
       (* 5)))

(defn start-node
  "Finds the starting node (the empty cell)"
  [nodes]
  (->> nodes
       (filter (comp zero? :used))
       (first)
       :pos))

(defn moveable-nodes
  "Finds all the grid positions that can be moved"
  [nodes unmoveable-size]
  (->> nodes
       (filter #(<= (:used %) unmoveable-size))
       (map :pos)
       set))

(defrecord MoveGraph [moveable]
  Graph
  (edges [_ v]
    (filter moveable (grid/adj-coords-2d v)))
  (distance [_ _ _] 1))

(defn distance-to-goal-data
  "Total number of moves required to get the empty cell moved
   up to the goal data position"
  [nodes unmoveable-size]
  (let [start (start-node nodes)
        max-x (max-x-pos nodes)
        finish? (partial = [max-x 0])
        graph (->MoveGraph (moveable-nodes nodes unmoveable-size))]
    (->> (g/dijkstra graph start finish? :limit 10000)
         count
         dec)))

(defn fewest-moves-to-goal-data
  "Finds the fewest number of steps that will get the goal data moved to the
   origin. Requires a number for the size of nodes that are deemed immovable
   (determined by visually inspecting the data to see what the outliers are)."
  [nodes unmoveable-size]
  ;; Find shortest allowed path from empty node to upper-right
  ;; (Say, use Dijkstra's). This will need to navigate past the wall of
  ;; immovable data.
  ;; Then, presuming there are no vertical obstacles of immovable data,
  ;; it takes a sequence of five moves to shift the target data left by one,
  ;; so the remainder is just five times the number of cells to move.
  (+ (distance-to-goal-data nodes unmoveable-size)
     (goal-data-move-count nodes)))

;; Puzzle solutions
(defn part1
  "How many viable pairs of nodes are there?"
  [input]
  (viable-pair-count input))

(defn part2
  "What is the fewest number of steps required to move your goal
   data to node-x0-y0?"
  [input]
  (fewest-moves-to-goal-data input 400))