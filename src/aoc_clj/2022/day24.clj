(ns aoc-clj.2022.day24
  "Solution to https://adventofcode.com/2022/day/24"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.graph :as graph :refer [Graph]]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]
            [aoc-clj.utils.math :as math]))

;;;; Constants

(def charmap {\. :open \# :wall \> :r \^ :u \< :l \v :d})

;;;; Input parsing

(defn blizzards
  [grid]
  (filter #(#{:r :u :l :d} (val %)) grid))

(defn parse
  [input]
  (let [{:keys [width height grid]}
        (mapgrid/ascii->MapGrid2D charmap input :down true)]
    {:x-bound   (- width 2)
     :y-bound   (- height 2)
     :blizzards (blizzards grid)}))

(def day24-input (u/parse-puzzle-input parse 2022 24))

;;;; Puzzle logic

(defn blizzard-update
  "Evolve a blizzards one step forward in time, implementing
   wrap-around logic."
  [{:keys [x-bound y-bound]} [[x y] dir]]
  (let [newpos (case dir
                 :u (if (= 1 y)       [x y-bound] [x (dec y)])
                 :d (if (= y-bound y) [x 1]       [x (inc y)])
                 :l (if (= 1 x)       [x-bound y] [(dec x) y])
                 :r (if (= x-bound x) [1 y]       [(inc x) y]))]
    [newpos dir]))

(defn step
  "Evolve all blizzards one step forward in time"
  [{:keys [blizzards] :as state}]
  (assoc state :blizzards (map #(blizzard-update state %) blizzards)))

(defn blizzard-positions
  [{:keys [blizzards]}]
  (into #{} (map first blizzards)))

(defn blizzard-sim
  "Given the initial state of blizzards, return a function that
   will return all blizzard locations at any time in the future"
  [{:keys [x-bound y-bound] :as state}]
  (let [recurrence (math/lcm x-bound y-bound)
        states (->> (iterate step state)
                    (take recurrence)
                    (mapv blizzard-positions))]
    (fn [time]
      (nth states (mod time recurrence)))))

(defn in-bounds?
  [{:keys [x-bound y-bound]} [x y]]
  (or (= [x y] [1 0])
      (= [x y] [x-bound (inc y-bound)])
      (and (<= 1 x x-bound)
           (<= 1 y y-bound))))

(defn augment
  "Remove the list of blizzard locations and add a blizzard
   simulator function that will return the list of blizzards
   at any time t"
  [state]
  (-> state
      (dissoc :blizzards)
      (assoc  :sim (blizzard-sim state))))

(defn next-possible-states
  "Compute the set of possible next valid states to move to,
   from a given position at time `time`"
  [{:keys [sim] :as state} [time pos]]
  (let [blizzards (sim (inc time))
        moves     (->> (grid/adj-coords-2d pos)
                       (cons pos)
                       (remove blizzards)
                       (filter #(in-bounds? state %)))]
    (map #(vector (inc time) %) moves)))

(defrecord BlizzardGraph [state]
  Graph
  (edges [_ v] (next-possible-states state v))
  (distance [_ _ _] 1))

(defn destination?
  [location]
  (fn [[_ pos]]
    (= location pos)))

(defn heuristic
  [location]
  (fn [[_ pos]]
    (math/manhattan pos location)))

(defn find-path
  "Use the A* algorithm to find the shortest path from the start to
   destination, using the heuristic of the manhattan distance from
   any grid position to the destination"
  [graph start dest]
  (graph/a-star
   graph
   start
   (destination? dest)
   (heuristic dest)))

(defn path-to-exit
  "Find the path from the start to the cell immediately before the exit,
   beginning at time `t`"
  [{:keys [x-bound y-bound] :as state} t]
  (let [g (->BlizzardGraph (augment state))]
    (find-path g [t [1 0]] [x-bound y-bound])))

(defn path-to-start
  "Find the path from the exit to the cell immediately before the start,
   beginning at time `t`"
  [{:keys [x-bound y-bound] :as state} t]
  (let [g (->BlizzardGraph (augment state))]
    (find-path g [t [x-bound (inc y-bound)]] [1 1])))

(defn shortest-time-to-exit
  "Compute the shortest path from the start to the exit"
  [input]
  (count (path-to-exit input 0)))

(defn shortest-roundtrip-to-exit
  "Compute the shortest path from the start to the exit,
   back to the start, and then back to the exit again."
  [input]
  (->> (path-to-exit input 0)
       last
       first
       inc
       (path-to-start input)
       last
       first
       inc
       (path-to-exit input)
       last
       first
       inc))

;;;; Puzzle solutions

(defn day24-part1-soln
  "What is the fewest number of minutes required to avoid the blizzards and
   reach the goal?"
  []
  (shortest-time-to-exit day24-input))

(defn day24-part2-soln
  "What is the fewest number of minutes required to reach the goal, 
   go back to the start, then reach the goal again?"
  []
  (shortest-roundtrip-to-exit day24-input))