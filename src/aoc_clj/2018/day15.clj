(ns aoc-clj.2018.day15
  "Solution to https://adventofcode.com/2018/day/15"
  (:require [aoc-clj.utils.grid.mapgrid :as mg]))

;; Constants
(def init-hp 200)
(def attack-power 3)

;; Input parsing
(def charmap
  {\# :wall
   \. :open
   \E :elf
   \G :goblin})

(defn unit?
  [[_ v]]
  (or (= :elf v) (= :goblin v)))

(defn init-unit
  [[k v]]
  {:pos k :type v :hp init-hp})

(defn reading-compare
  [[x1 y1] [x2 y2]]
  (if (zero? (compare y1 y2))
    (compare x1 x2)
    (compare y1 y2)))

(defn reading-order
  [units]
  (sort-by :pos reading-compare units))

(defn parse
  [input]
  (let [grid (:grid (mg/ascii->MapGrid2D charmap input :down true))]
    {:walls (->> (filter #(= :wall (val %)) grid)
                 keys
                 set)
     :units (->> (filter unit? grid)
                 (map init-unit)
                 reading-order)}))

;; Puzzle logic
