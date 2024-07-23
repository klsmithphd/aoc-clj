(ns aoc-clj.2016.day11
  "Solution to https://adventofcode.com/2016/day/11"
  (:require [clojure.math.combinatorics :as combo]
            [clojure.set :as set]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.graph :as g :refer [Graph]]))

;; Constants
(def elevator-options
  {1 [2]
   2 [1 3]
   3 [2 4]
   4 [3]})

;; Input parsing
(defn get-generators 
  [s]
  (map (comp #(vector :g %) second) (re-seq #"(\w+) generator" s)))

(defn get-microchips
  [s]
  (map (comp #(vector :m %) second) (re-seq #"(\w+)-compatible microchip" s)))

(defn parse-line
  [line]
  (into #{} (concat (get-generators line) (get-microchips line))))

(defn parse
  [input]
  (merge (zipmap [1 2 3 4] (map parse-line input)) {:e 1}))

;; Puzzle logic
(defn move
  [state from to objects]
  (assoc state
         :e to
        ;;  :move (inc (get state :move))
         from (apply disj (state from) objects)
         to (apply conj (state to) objects)))

(defn microchips
  [items]
  (->> items
       (filter #(= :m (first %)))
       (map second)
       set))

(defn generators
  [items]
  (->> items
       (filter #(= :g (first %)))
       (map second)
       set))

(defn legal-items?
  [items]
  (let [chips (microchips items)
        gens  (generators items)]
    (or (empty? gens)
        (set/subset? chips gens))))

(defn legal?
  [state]
  (every? legal-items? (map (partial get state) [1 2 3 4])))

(defn adjacents
  [state]
  (let [elev (get state :e)
        floor (seq (get state elev))
        moves (elevator-options elev)
        objects (->> (concat (combo/combinations floor 1)
                             (combo/combinations floor 2))
                     (filter legal-items?))]
    (->> (for [m moves o objects]
           (move state elev m o))
         (filter legal?))))

(defrecord MoveGraph []
  Graph
  (edges
    [_ v]
    (adjacents v))

  (distance
    [_ _ _]
    1))

(defn endstate
  [state]
  {:e 4
   1 #{}
   2 #{}
   3 #{}
   4 (->> (select-keys state [1 2 3 4])
         vals
         (apply set/union))})

(defn move-count
  [input]
  (->> (g/dijkstra
        (->MoveGraph)
        input
        (u/equals? (endstate input))
        :limit 10000000)
       count
       dec))

(defn extra-items
  [state]
  (update state 1 conj [:m "elerium"] [:g "elerium"] [:m "dilithium"] [:g "dilithium"]))

;; Puzzle solutions
(defn part1
  [input]
  (move-count input))

(defn part2
  [input]
  (move-count (extra-items input)))