(ns aoc-clj.2022.day16
  "Solution to https://adventofcode.com/2022/day/16"
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.graph :as graph :refer [->MapGraph]]
            [aoc-clj.utils.core :as u]))

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

(def d16-s01
  (parse
   ["Valve AA has flow rate=0; tunnels lead to valves DD, II, BB"
    "Valve BB has flow rate=13; tunnels lead to valves CC, AA"
    "Valve CC has flow rate=2; tunnels lead to valves DD, BB"
    "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE"
    "Valve EE has flow rate=3; tunnels lead to valves FF, DD"
    "Valve FF has flow rate=0; tunnels lead to valves EE, GG"
    "Valve GG has flow rate=0; tunnels lead to valves FF, HH"
    "Valve HH has flow rate=22; tunnel leads to valve GG"
    "Valve II has flow rate=0; tunnels lead to valves AA, JJ"
    "Valve JJ has flow rate=21; tunnel leads to valve II"]))

(def day16-input (parse (sort (u/puzzle-input "2022/day16-input.txt"))))

(defn edges
  [{:keys [valve tunnels]}]
  [valve (zipmap tunnels (repeat 1))])

(defn raw-graph
  [input]
  (->MapGraph (into {} (map edges input))))

(defn keeper?
  [{:keys [valve flow]}]
  (or (= "AA" valve) (pos? flow)))

(defn valves
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
        dists  (map #(dec (count (apply graph/dijkstra g %))) pairs)
        pairs2 (concat pairs (map reverse pairs))]
    (->> (map #(conj (vec %1) %2) pairs2 (cycle dists))
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
  ;; (println n1 t p n2)
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
  ;; (println "In OUTCOME 2" player1 player2)
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
  ;; (println "HISTORY " history)
  (let [candidates (available-nodes2 graph history)]
    (if (empty? candidates)
      history
      (let [last-move (first history)
            ;; _ (println "LAST MOVE" last-move)
            trials (->> candidates
                        (map #(outcome2 graph valves last-move %))
                        (filter valid-time?))
            ;; _ (println "TRIALS" trials)
            ;; _ (println "CONS'D" (first (map #(cons % history) trials)))
            subpaths (map #(best-subpath-2 graph valves (cons % history)) trials)]
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

(defn day16-part1-soln
  "Work out the steps to release the most pressure in 30 minutes. 
   What is the most pressure you can release?"
  []
  (best-pressure day16-input))

(defn day16-part2-soln
  "With you and an elephant working together for 26 minutes, what is the 
   most pressure you could release?"
  []
  (best-pressure-2 day16-input))

(comment
  (def g {"AA" {"BB" 1 "CC" 2}
          "BB" {"AA" 1 "CC" 1}
          "CC" {"AA" 2 "BB" 1}})
  (def v {"AA" 0 "BB" 13 "CC" 2})
  (available-nodes2 g [[["BB" 24 312] ["AA" 26 0]] [["AA" 30 0] ["AA" 30 0]]])
  (valid-time? [["BB" 24 312] ["AA" 26 0]])
  (cons (first (filter valid-time? (map #(outcome2 g v [["AA" 26 0] ["AA" 26 0]] %) ["BB" "CC"])))
        [[["AA" 30 0] ["AA" 30 0]]])
  (best-subpath-2 g v [[["AA" 26 0] ["AA" 26 0]]])

  (best-subpath-2 (:graph (simpler-graph day16-input))
                  (valves day16-input)
                  [[["AA" 26 0] ["AA" 26 0]]]))