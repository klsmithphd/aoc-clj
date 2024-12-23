(ns aoc-clj.2016.day17
  "Solution to https://adventofcode.com/2016/day/17"
  (:require [aoc-clj.utils.digest :as dig]
            [aoc-clj.utils.graph :as g :refer [Graph]]
            [aoc-clj.utils.vectors :as v]))

;; Constants
(def directions
  {"U" [0 -1]
   "D" [0 1]
   "R" [1 0]
   "L" [-1 0]})

;; Input parsing
(def parse first)

;; Puzzle logic
(defn init-state
  "Construct the initial state structure from the passcode"
  [passcode]
  {:passcode passcode :pos [0 0] :path ""})

(defn in-grid?
  "Whether the position lies within the open rooms of the 4x4 grid"
  [[x y]]
  (and (<= 0 x 3) (<= 0 y 3)))

(defn is-open?
  "Determines whether the MD5 hash character corresponds to an open door"
  [ch]
  (#{\b \c \d \e \f} ch))

(defn new-state
  "Given the current state, and a direction to move in, return the
   new state after having moved"
  [{:keys [pos path] :as state} dir]
  (assoc state
         :pos (v/vec-add pos (directions dir))
         :path (str path dir)))

(defn move-options
  "Given the current state, determine the collection of all possible
   next moves"
  [{:keys [passcode path] :as state}]
  (->> (zipmap ["U" "D" "L" "R"] (dig/md5-str (str passcode path)))
       (filter #(is-open? (val %)))
       (map #(new-state state (key %)))
       (filter #(in-grid? (:pos %)))))

(defrecord VaultGraph []
  Graph
  (edges
    [_ v]
    (move-options v))

  (distance
    [_ _ _]
    1))

(defn shortest-path
  "Given the passcode, determine the shortest path from [0 0] to [3 3]"
  [passcode]
  (let [init   (init-state passcode)
        final? #(= [3 3] (:pos %))]
    (->> (g/shortest-path (->VaultGraph) init final?)
         last
         :path)))

(defn longest-path-length
  "Given the passcode, determine the length of the longest path from 
   [0 0] to [3 3]"
  [passcode]
  (let [init (init-state passcode)
        final? #(= [3 3] (:pos %))]
    (->> (g/all-paths-dfs (->VaultGraph) init final?)
         (map last)
         (map #(count (:path %)))
         (apply max))))

;; Puzzle solutions
(defn part1
  "What is the shortest path to reach the vault"
  [input]
  (shortest-path input))

(defn part2
  "What is the length of the longest path to reach the vault"
  [input]
  (longest-path-length input))

