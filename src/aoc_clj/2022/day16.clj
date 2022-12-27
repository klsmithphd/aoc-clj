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

;; (defn greedy-next-move
;;   [g valves pos time open]
;;   (let [candidates (->> (remove (into #{pos} (keys open)) (keys valves))
;;                         (filter #(< (get-in g [:graph pos %]) time)))
;;         values     (zipmap candidates
;;                            (map #(/ (valves %)
;;                                     (get-in g [:graph pos %])) candidates))
;;         choice     (key (apply max-key val values))
;;         dist       (get-in g [:graph pos choice])
;;         now-time   (- time dist 1)]
;;     [choice now-time (assoc open choice now-time)]))

;; (defn greedy-solve
;;   [input]
;;   (let [valves (valves input)
;;         g      (simpler-graph input)]
;;     (loop [pos "AA" time 30 open {}]
;;       (if (or (zero? time)
;;               (= (count open) (count valves)))
;;         [pos time open]
;;         (let [[p t o] (greedy-next-move g valves pos time open)]
;;           (recur p t o))))))

;; (defn visit-times
;;   [{:keys [graph]} nodes]
;;   (let [steps (partition 2 1 nodes)]
;;     (->> (map #(inc (get-in graph %)) steps)
;;          (reductions +)
;;          (map #(- 30 %))
;;          (zipmap (rest nodes)))))

;; (defn pressure-released
;;   [valves open-times]
;;   (reduce + (map (fn [[k v]] (* (valves k) v)) open-times)))

;; (defn brute-force-best-path
;;   [input]
;;   (let [g (simpler-graph input)
;;         v (valves input)
;;         nodes (keys (:graph g))
;;         scenarios (map #(concat ["AA"] %)
;;                        (combo/permutations (remove #{"AA"} nodes)))]
;;     (->> scenarios
;;          (map #(visit-times g %))
;;          (map #(pressure-released v %))
;;          (apply max))))

(defn helper
  [graph valves n1 t p n2]
  (let [dist      (inc (get-in graph [n1 n2]))
        newt      (- t dist)
        pressure  (+ p (* newt (valves n2)))]
    [n2 newt pressure]))

(defn elapsed-time
  [h]
  (nth h 1))

(defn pressure
  [h]
  (nth h 2))

(defn best-pressure-subpath
  [graph valves history]
  (let [candidates (remove (set (map first history)) (keys graph))]
    ;; (println history)
    (if (empty? candidates)
      history
      (let [[n t p] (first history)
            trials (->> candidates
                        (map #(helper graph valves n t p %))
                        (filter #(pos? (elapsed-time %))))
            ;; _ (println n t p trials)
            subpaths (map
                      #(best-pressure-subpath graph valves (cons % history))
                      trials)
            ;; _ (println n subpaths)
            ]
        ;; Return the best subpath
        (if (empty? subpaths)
          history
          (first (sort-by (comp pressure first) > subpaths)))))))

(def g {"AA" {"BB" 1 "CC" 2 "DD" 1}
        "BB" {"AA" 1 "CC" 1 "DD" 2}
        "CC" {"AA" 2 "BB" 1 "DD" 1}
        "DD" {"AA" 1 "BB" 2 "CC" 1}})
(def v {"AA" 0 "BB" 13 "CC" 2 "DD" 20})
(best-pressure-subpath g v [["AA" 30 0]])


(map #(nth (first %) 2) [[["CC" 26 416] ["BB" 28 364] ["AA" 30 0]] [["BB" 25 379] ["CC" 27 54] ["AA" 30 0]]])

(nth (first [["CC" 26 416] ["BB" 28 364] ["AA" 30 0]]) 2)

(defn best-pressure
  [input]
  (let [g (:graph (simpler-graph input))
        v (valves input)]
    (pressure (first (best-pressure-subpath g v [["AA" 30 0]])))))

(best-pressure d16-s01)

(defn day16-part1-soln
  []
  (best-pressure day16-input))
