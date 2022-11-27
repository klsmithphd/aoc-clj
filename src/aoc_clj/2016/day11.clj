(ns aoc-clj.2016.day11
  (:require [clojure.math.combinatorics :as combo]
            [clojure.set :as set]
            [aoc-clj.utils.graph :as g :refer [Graph]]))

"The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip."
"The second floor contains a hydrogen generator."
"The third floor contains a lithium generator."
"The fourth floor contains nothing relevant."
(def day11-sample
  {:e 1
  ;;  :move 0
   1 #{[:m "H"] [:m "Li"]}
   2 #{[:g "H"]}
   3 #{[:g "Li"]}
   4 #{}})

"The first floor contains a thulium generator, a thulium-compatible microchip, a plutonium generator, and a strontium generator.
The second floor contains a plutonium-compatible microchip and a strontium-compatible microchip.
The third floor contains a promethium generator, a promethium-compatible microchip, a ruthenium generator, and a ruthenium-compatible microchip.
The fourth floor contains nothing relevant."
(def day11-input
  {:e 1
  ;;  :move 0
   1 #{[:m "Tm"] [:g "Tm"] [:g "Pu"] [:g "Sr"]}
   2 #{[:m "Pu"] [:m "Sr"]}
   3 #{[:m "Pm"] [:m "Ru"] [:g "Pm"] [:g "Ru"]}
   4 #{}})

(defn endstate
  [state]
  {:e 4
   1 #{}
   2 #{}
   3 #{}
   4 (set/union (state 1) (state 2) (state 3) (state 4))})

(def elevator-options
  {1 [2]
   2 [1 3]
   3 [2 4]
   4 [3]})

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

(defn move-count
  [input]
  (->> (g/dijkstra (->MoveGraph) input (endstate input) :limit 10000000)
       count
       dec))

(defn day11-part1-soln
  []
  (move-count day11-input))

(defn extra-items
  [state]
  (update state 1 conj [:m "El"] [:g "El"] [:m "Li2"] [:g "Li2"]))

(defn day11-part2-soln
  []
  (move-count (extra-items day11-input)))