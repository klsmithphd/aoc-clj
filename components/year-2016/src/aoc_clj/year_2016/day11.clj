(ns aoc-clj.year-2016.day11
  "Solution to https://adventofcode.com/2016/day/11"
  (:require [clojure.math.combinatorics :as combo]
            [clojure.set :as set]
            [aoc-clj.util.interface :as u]
            [aoc-clj.graph.interface :as g :refer [Graph]]
            [aoc-clj.vectors.interface :as v]))

;; Constants
(def elevator-options
  "A map of the viable floor moves that the elevator can undertake."
  {0 [1]
   1 [1 -1]
   2 [1 -1]
   3 [-1]})

;; Input parsing
(defn get-generators
  "Map of element-name to `{:g floor}` for each generator named on `s`."
  [floor s]
  (into {} (map (comp #(vector % {:g floor}) second) (re-seq #"(\w+) generator" s))))

(defn get-microchips
  "Map of element-name to `{:m floor}` for each microchip named on `s`."
  [floor s]
  (into {} (map (comp #(vector % {:m floor}) second) (re-seq #"(\w+)-compatible microchip" s))))

(defn parse-line
  "Map of element-name to `{:m chip-floor :g gen-floor}` for one input line."
  [floor line]
  (merge-with into (get-generators floor line)
              (get-microchips floor line)))

;; State representation: a vector `[elev pairs]`, where `elev` is the
;; current elevator floor (0-3) and `pairs` is a frequency map keyed by
;; `[chip-floor gen-floor]` tuples. Each tuple represents one anonymous
;; element-pair: tracking only where the chip and generator sit, not
;; which element it is. 
(defn parse
  "Parses puzzle input into the canonical state vector `[elev pairs]`,
   where `pairs` is a frequency map of `[chip-floor gen-floor]` tuples
   keyed structurally rather than by element identity."
  [input]
  (let [items (->> input
                   (map-indexed parse-line)
                   (apply merge-with into))]
    [0 (frequencies (map (fn [{:keys [m g]}] [m g]) (vals items)))]))

;; Puzzle logic
;; Rules:
;; - The elevator carries one or two items, moves up or down one floor.
;; - The elevator can only move items currently on its floor.
;; - Possible payload shapes (the only ones that yield legal source AND
;;   destination states): one chip, one generator, two chips, two
;;   generators, or a matched chip+generator pair.
;; - A chip on a floor without its own generator is fried by any other
;;   generator on that floor.

(defn pair-count
  "Counts the total number of microchip-generator pairs"
  [[_ pairs]]
  (reduce + (vals pairs)))

(defn endstate
  "Given the initial state, return the desired endstate with all items and the elevator
   on the fourth floor"
  [state]
  [3 {[3 3] (pair-count state)}])

(defn rm-pair
  "Decrement the count of pair-tuple `p`, dissoc'ing the entry when it
   would otherwise reach zero."
  [pairs p]
  (if (= 1 (pairs p))
    (dissoc pairs p)
    (update pairs p dec)))

(defn add-pair
  "Increment the count of pair-tuple `p`, defaulting to 1 if absent."
  [pairs p]
  (update pairs p (fnil inc 0)))

(defn move-pair
  "Move one occurrence of pair-tuple `from` to pair-tuple `to`."
  [pairs from to]
  (-> pairs (rm-pair from) (add-pair to)))

(defn chip-candidate
  "New pair-tuple after shifting the chip-floor of `p` by `delta`."
  [delta p]
  (v/vec-add [delta 0] p))

(defn generator-candidate
  "New pair-tuple after shifting the gen-floor of `p` by `delta`."
  [delta p]
  (v/vec-add [0 delta] p))

(defn pairs-candidate
  "New pair-tuple after shifting both chip-floor and gen-floor by `delta`
   (used for a matched chip+generator move)."
  [delta p]
  (v/vec-add [delta delta] p))

(defn chips-on
  "Pair-tuples in `pairs` whose chip is on floor `elev`. Includes
   tuples whose generator is also on `elev` (matched pairs)."
  [pairs elev]
  (filter #(= elev (first %)) (keys pairs)))

(defn gens-on
  "Pair-tuples in `pairs` whose generator is on floor `elev`. Includes
   matched pairs."
  [pairs elev]
  (filter #(= elev (second %)) (keys pairs)))

(defn matched-on
  "Pair-tuples in `pairs` whose chip and generator are both on `elev`."
  [pairs elev]
  (filter #(= elev (first %) (second %)) (keys pairs)))

(defn pair-singles
  "All single-item moves: one [old new] update each."
  [select candidate pairs elev delta]
  (for [t (select pairs elev)]
    [[t (candidate delta t)]]))

(defn pair-doubles
  "All two-item moves: two [old new] updates each. Includes same-tuple
   pairs when their multiplicity is at least 2."
  [select candidate pairs elev delta]
  (let [ts        (select pairs elev)
        same-key  (for [t ts :when (>= (pairs t) 2)] [t t])
        diff-keys (combo/combinations ts 2)]
    (for [[t1 t2] (concat same-key diff-keys)]
      [[t1 (candidate delta t1)]
       [t2 (candidate delta t2)]])))

(defn apply-updates
  "Apply a sequence of `[old-tuple new-tuple]` rewrites to `pairs`."
  [pairs updates]
  (reduce (fn [p [old new]] (move-pair p old new)) pairs updates))

(defn candidate-moves
  "Candidate next-states reachable from `[elev pairs]` by moving the
   elevator `delta` floors. No matched-pair `pair-doubles` line because
   carrying two matched pairs would exceed the two-item elevator limit.
   Results are not yet legality-filtered — call `legal?` downstream."
  [[elev pairs] delta]
  (for [updates (concat
                 (pair-singles chips-on   chip-candidate      pairs elev delta)
                 (pair-singles gens-on    generator-candidate pairs elev delta)
                 (pair-singles matched-on pairs-candidate     pairs elev delta)
                 (pair-doubles chips-on   chip-candidate      pairs elev delta)
                 (pair-doubles gens-on    generator-candidate pairs elev delta))]
    [(+ elev delta) (apply-updates pairs updates)]))

(defn all-candidates
  "Set of all candidate next-states across both elevator directions."
  [[elev :as state]]
  (set (mapcat #(candidate-moves state %) (elevator-options elev))))

(defn legal?
  "A state is legal when no floor has both a lone chip and any generator
   present. A paired chip+gen on a floor counts as a generator from the
   perspective of any *other* lone chip on that floor."
  [[_ pairs]]
  (let [chips (->> (keys pairs)
                   (filter #(not= (first %) (second %)))
                   (map first)
                   set)
        gens  (->> (keys pairs)
                   (map second)
                   set)]
    (empty? (clojure.set/intersection chips gens))))

(defn legal-moves
  "Given the current state, return a seq of the possible next states that are legal
   (i.e. won't result in a microchip getting fried)"
  [state]
  (->> (all-candidates state)
       (filter legal?)))

(defrecord MoveGraph []
  Graph
  (edges
    [_ v]
    (legal-moves v))

  (distance
    [_ _ _]
    1))

(defn move-count
  "Counts the minimum number of moves required to get all items moved up to the fourth floor"
  [input]
  (let [finish? (u/equals? (endstate input))]
    (->> (g/shortest-path (->MoveGraph) input finish?)
         count
         dec)))

(defn extra-items
  "Update the initial state to account for additional items that weren't in the puzzle input"
  [[elev pairs]]
  [elev (-> pairs (add-pair [0 0]) (add-pair [0 0]))])

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