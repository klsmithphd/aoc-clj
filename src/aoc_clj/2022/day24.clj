(ns aoc-clj.2022.day24
  "Solution to https://adventofcode.com/2022/day/24"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.graph :as graph :refer [Graph]]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]))

(def charmap {\. :open \# :wall \> :r \^ :u \< :l \v :d})

(defn blizzards
  [grid]
  (filter #(#{:r :u :l :d} (val %)) grid))

(defn parse
  [input]
  (let [{:keys [width height grid]}
        (mapgrid/ascii->MapGrid2D charmap input :down true)]
    {:time      0
     :x-bound   (- width 2)
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
  (-> state
      (assoc :blizzards (map #(blizzard-update state %) blizzards))
      (update :time inc)))

(defn init-state
  [input]
  (assoc input :pos [1 0]))

(defn in-bounds-and-open?
  [{:keys [x-bound y-bound blizzards]} [x y :as pos]]
  (and (<= 1 x x-bound)
       (<= 1 y y-bound)
       (nil? ((set (map first blizzards)) pos))))

(defn next-possible-states
  [{:keys [pos] :as state}]
  (let [future (step state)
        opts (filter #(in-bounds-and-open? future %)
                     (cons pos (grid/adj-coords-2d pos)))]
    (map #(assoc future :pos %) opts)))

(defrecord BlizzardGraph []
  Graph
  (edges [_ v] (next-possible-states v))
  (distance [_ _ _] 1))

(defn finish?
  [{:keys [x-bound y-bound pos]}]
  (= pos [x-bound y-bound]))

(defn explore-until-destination
  [state]
  (graph/dijkstra (->BlizzardGraph) (init-state state) finish? :limit 20000))

(defn shortest-time-to-navigate-blizzards
  [input]
  (count (explore-until-destination input)))

(defn day24-part1-soln
  []
  (shortest-time-to-navigate-blizzards day24-input))