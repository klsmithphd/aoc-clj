(ns aoc-clj.2022.day16
  "Solution to https://adventofcode.com/2022/day/16"
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.graph :as graph :refer [->MapGraph]]
            [aoc-clj.utils.core :as u]))

;;;; Input parsing

(defn parse-line
  [line]
  (let [valves (re-seq #"[A-Z]{2}" line)
        flow   (read-string (re-find #"\d+" line))]
    {:valve (first valves)
     :flow  flow
     :tunnels (rest valves)}))

(defn parse
  [input]
  (map parse-line input))

;;;; Puzzle logic

(defn edges
  "Every adjacent tunnel is one minute away"
  [{:keys [valve tunnels]}]
  [valve (zipmap tunnels (repeat 1))])

(defn raw-graph
  "A Graph representation of all rooms"
  [input]
  (->MapGraph (into {} (map edges input))))

(defn keeper?
  "Only the starting room `AA` and rooms with valves that have positive
   flow rates are worth considering"
  [{:keys [valve flow]}]
  (or (= "AA" valve) (pos? flow)))

(defn valves
  "Constructs a map from valves to their corresponding flow rate,
   just for the valves with non-zero flow rates"
  [input]
  (let [nodes (filter keeper? input)]
    (into {} (map #(vector (:valve %) (:flow %)) nodes))))

(defn simpler-graph
  "Reduce the graph of all possible rooms to just those that have
   non-zero valve values"
  [input]
  (let [g      (raw-graph input)
        valves (valves input)
        pairs  (combo/combinations (keys valves) 2)
        all-pairs (concat pairs (map reverse pairs))
        args   (map (fn [[a b]] [a (u/equals? b)]) all-pairs)
        dists  (map #(dec (count (apply graph/dijkstra g %))) args)]
    (->> (map #(conj (vec %1) %2) all-pairs dists)
         (group-by first)
         (u/fmap #(into {} (map (comp vec rest)) %))
         ->MapGraph)))

(defn elapsed-time
  [h]
  (nth h 1))

(defn pressure
  [h]
  (nth h 2))

(defn outcome
  "Computes the outcome of moving from location `n1` to location `n2` and
   opening the valve at that location, given current countdown timer value
   `t` and cumulative pressure of all previously opened valves `v`
   
   Returns a vec of `n2`, the new value of the countdown timer, and the
   new cumulative pressure"
  [graph valves [n1 t p] n2]
  (let [dist      (inc (get-in graph [n1 n2]))
        newt      (- t dist)
        pressure  (+ p (* newt (valves n2)))]
    [n2 newt pressure]))

(defn available-nodes [graph history]
  (remove (set (map first history)) (keys graph)))

(defn best-subpath
  "Finds the optimal subpath of moves to open valves sequentially resulting
   in the maximum cumulative pressure released over time, based on
   previously visiting the locations in `history`.
   
   `history` represents visiting a node as a vec of [room, time, pressure]"
  [graph valves history]
  (let [candidates (available-nodes graph history)]
    (if (empty? candidates)
      history
      (let [last-move (first history)
            subpaths (->> candidates
                          (map #(outcome graph valves last-move %))
                          (filter #(pos? (elapsed-time %)))
                          (map #(best-subpath graph valves (cons % history))))]
        (if (empty? subpaths)
          history
          (first (sort-by (comp pressure first) > subpaths)))))))

(defn available-nodes2 [graph history]
  (remove (set (mapcat #(map first %) history)) (keys graph)))

(defn outcome2
  [graph valves [[_ t1 :as player1] [_ t2 :as player2]] n2]
  (if (>= t1 t2)
    [(outcome graph valves player1 n2) player2]
    [player1 (outcome graph valves player2 n2)]))

(defn valid-time?
  [[[_ t1] [_ t2]]]
  (and (pos? t1) (pos? t2)))

(defn total-pressure
  [[[_ _ p1] [_ _ p2]]]
  (+ p1 p2))

(defn best-subpath-2
  "History is now a vec of [[room, time, pressure] [room, time, pressure]]
   representing two players"
  [graph valves history]
  (let [candidates (available-nodes2 graph history)]
    (if (empty? candidates)
      history
      (let [last-move (first history)
            subpaths (->> candidates
                          (map #(outcome2 graph valves last-move %))
                          (filter valid-time?)
                          (map #(best-subpath-2 graph valves (cons % history))))]
        (if (empty? subpaths)
          history
          (first (sort-by (comp total-pressure first) > subpaths)))))))

(defn best-pressure
  "Returns the maximum amount of pressure that can be released in 30 minutes"
  [input]
  (let [g (:graph (simpler-graph input))
        v (valves input)]
    (pressure (first (best-subpath g v [["AA" 30 0]])))))

(defn best-pressure-2
  "Returns the maximum amount of pressure that can be released in 26 minutes
   by two agents working in parallel"
  [input]
  (let [g (:graph (simpler-graph input))
        v (valves input)]
    (total-pressure (first (best-subpath-2 g v [[["AA" 26 0] ["AA" 26 0]]])))))

;;;; Puzzle solutions

(defn day16-part1-soln
  "Work out the steps to release the most pressure in 30 minutes. 
   What is the most pressure you can release?"
  [input]
  (best-pressure input))

(defn day16-part2-soln
  "With you and an elephant working together for 26 minutes, what is the 
   most pressure you could release?"
  [input]
  (best-pressure-2 input))