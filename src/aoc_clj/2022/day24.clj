(ns aoc-clj.2022.day24
  "Solution to https://adventofcode.com/2022/day/24"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.graph :as graph :refer [Graph]]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]
            [aoc-clj.utils.math :as math]))

(def charmap {\. :open \# :wall \> :r \^ :u \< :l \v :d})

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

(def d24-s01
  (parse
   ["#.#####"
    "#.....#"
    "#>....#"
    "#.....#"
    "#...v.#"
    "#.....#"
    "#####.#"]))

(def d24-s02
  (parse
   ["#.######"
    "#>>.<^<#"
    "#.<..<<#"
    "#>v.><>#"
    "#<^v^^>#"
    "######.#"]))

(def day24-input (parse (u/puzzle-input "2022/day24-input.txt")))

(defn blizzard-update
  [{:keys [x-bound y-bound]} [[x y] dir]]
  (let [newpos (case dir
                 :u (if (= 1 y)       [x y-bound] [x (dec y)])
                 :d (if (= y-bound y) [x 1]       [x (inc y)])
                 :l (if (= 1 x)       [x-bound y] [(dec x) y])
                 :r (if (= x-bound x) [1 y]       [(inc x) y]))]
    [newpos dir]))

(defn step
  [{:keys [blizzards] :as state}]
  (assoc state :blizzards (map #(blizzard-update state %) blizzards)))

(defn blizzard-positions
  [{:keys [blizzards]}]
  (into #{} (map first blizzards)))

(defn blizzard-sim
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
  [state]
  (-> state
      (dissoc :blizzards)
      (assoc  :sim (blizzard-sim state))))

(defn next-possible-states
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

(defn entrance?
  [_ [_ pos]]
  (= pos [1 1]))

(defn entrance-heuristic
  [_ [_ pos]]
  (math/manhattan pos [1 1]))

(defn exit?
  [{:keys [x-bound y-bound]} [_ pos]]
  (= pos [x-bound y-bound]))

(defn exit-heuristic
  [{:keys [x-bound y-bound]} [_ pos]]
  (math/manhattan pos [x-bound y-bound]))

(defn destination?
  [location]
  (fn [[_ pos]]
    (= location pos)))

(defn heuristic
  [location]
  (fn [[_ pos]]
    (math/manhattan pos location)))

(defn find-path
  [graph start dest]
  (graph/a-star
   graph
   start
   (destination? dest)
   (heuristic dest)))

(defn path-to-exit
  [{:keys [x-bound y-bound] :as state} t]
  (let [g (->BlizzardGraph (augment state))]
    (find-path g [t [1 0]] [x-bound y-bound])))

(defn path-to-start
  [{:keys [x-bound y-bound] :as state} t]
  (let [g (->BlizzardGraph (augment state))]
    (find-path g [t [x-bound (inc y-bound)]] [1 1])))

(defn move-to-exit
  [path]
  (let [[t [x y]] (last path)]
    [(inc t) [x (inc y)]]))

(defn move-to-start
  [path]
  (let [[t [x y]] (last path)]
    [(inc t) [x (dec y)]]))

(defn shortest-time-to-exit
  [input]
  (count (path-to-exit input 0)))

(defn shortest-roundtrip-to-exit
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

(defn day24-part1-soln
  []
  (shortest-time-to-exit day24-input))

(defn day24-part2-soln
  []
  (shortest-roundtrip-to-exit day24-input))