(ns aoc-clj.2018.day07
  "Solution to https://adventofcode.com/2018/day/7"
  (:require [clojure.set :as set]
            [aoc-clj.utils.core :as u]))

;; Constants
(def ascii-A 65)
(def n-workers 5)
(def step-duration 60)

;; Input parsing
(defn parse-line
  [line]
  (reverse (map second (re-seq #"tep ([A-Z])" line))))

(defn parse
  [input]
  (let [deps (->> (map parse-line input)
                  (group-by first)
                  (u/fmap #(set (map second %))))
        upstream  (set (apply concat (vals deps)))
        to-add    (->> (set/difference upstream (set (keys deps)))
                       (map #(vector % #{})))]
    (into deps to-add)))

;; Puzzle logic
(defn ready
  [graph]
  (->> (filter #(empty? (val %)) graph)
       (map key)))

(defn next-nodes
  [graph]
  (sort (ready graph)))

(defn finish-step
  [graph step]
  (->> (dissoc graph step)
       (u/fmap #(disj % step))))

(defn assembly-step
  [{:keys [graph steps]}]
  (let [step (first (next-nodes graph))]
    {:graph (finish-step graph step)
     :steps (conj steps step)}))

(defn assembly
  [graph]
  (->> {:graph graph :steps []}
       (iterate assembly-step)
       (drop-while (comp seq :graph))
       first
       :steps
       (apply str)))

(defn step-time
  [base step]
  (+ 1 base (- (int (first step)) ascii-A)))

(defn parallel-assembly-step
  [base n-workers {:keys [graph WIP time]}]
  (let [can-assign       (- n-workers (count WIP))
        _ (println can-assign)
        available        (->> (next-nodes graph)
                              (remove (set (keys WIP)))
                              (take can-assign))
        _ (println available)
        times            (map #(step-time base %) available)
        _ (println times)
        new-WIP          (into WIP (zipmap available times))
        _ (println new-WIP)
        [step jump-time] (first (sort-by val new-WIP))
        _ (println step jump-time)]
    {:graph (finish-step graph step)
     :WIP   (->> (dissoc new-WIP step)
                 (u/fmap #(- % jump-time)))
     :time  (+ time jump-time)}))

(defn parallel-assembly
  [base n-workers graph]
  (->> {:graph graph :WIP {} :time 0}
       (iterate #(parallel-assembly-step base n-workers %))
       (drop-while (comp seq :graph))
       first
       :time))

;; Puzzle solutions
(defn part1
  [input]
  (assembly input))

(defn part2
  [input]
  (parallel-assembly step-duration n-workers input))