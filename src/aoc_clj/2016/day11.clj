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
  "Return a new state map with the given `objects` removed from the `from` floor
   and added to the `to` floor"
  [state from to objects]
  (assoc state
         :e to
         from (apply disj (state from) objects)
         to   (apply conj (state to) objects)))

(defn get-typed-items
  [item-type]
  (fn [items]
    (->> items
         (filter #(= item-type (first %)))
         (map second)
         set)))

(def microchips (get-typed-items :m))
(def generators (get-typed-items :g))


(defn legal-items?
  "A combination of items on a given floor is legal if it doesn't result in any
   microchips getting fried, which means that any microchip, if present, must either
   not be next to any other generators or that every microchip is paired with its
   corresponding generator."
  [items]
  (let [chips (microchips items)
        gens  (generators items)]
    (or (empty? gens)
        (set/subset? chips gens))))

(defn legal?
  "A state is deemed legal if, on every floor, the combination of items is legal"
  [state]
  (every? legal-items? (map (partial get state) [1 2 3 4])))

(defn legal-moves
  "Given the current state, return a seq of the possible next states that are legal
   (i.e. won't result in a microchip getting fried)"
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
    (legal-moves v))

  (distance
    [_ _ _]
    1))

(defn endstate
  "Given the initial state, return the desired endstate with all items and the elevator
   on the fourth floor"
  [state]
  {:e 4
   1 #{}
   2 #{}
   3 #{}
   4 (->> (select-keys state [1 2 3 4])
         vals
         (apply set/union))})

(defn move-count
  "Counts the minimum number of moves required to get all items moved up to the fourth floor"
  [input]
  (->> (g/dijkstra
        (->MoveGraph)
        input
        (u/equals? (endstate input))
        :limit 10000000)
       count
       dec))

(defn extra-items
  "Update the initial state to account for additional items that weren't in the puzzle input"
  [state]
  (update state 1 conj [:m "elerium"] [:g "elerium"] [:m "dilithium"] [:g "dilithium"]))

;; Puzzle solutions
(defn part1
  "Minium number of moves to get all items to the fourth floor"
  [input]
  (move-count input))

(defn part2
  "Minium number of moves to get all items to the fourth floor, including the extra items
   discovered on the first floor"
  [input]
  (move-count (extra-items input)))

