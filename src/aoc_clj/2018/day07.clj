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
(defn next-nodes
  "Returns the next setp of steps that are ready to be worked on,
   in alphabetical order if more than one step is ready"
  [graph]
  (->> (filter #(empty? (val %)) graph)
       (map key)
       sort))

(defn finish-step
  "Updates the dependency graph to reflect finishing a step. 
   This returns a new dependency graph with the specified step removed
   as a key and among the value sets"
  [graph step]
  (->> (dissoc graph step)
       (u/fmap #(disj % step))))

(defn step-time
  "Computes the amount of time it will take to complete a step,
   accounting for a default base step duration"
  [base step]
  (+ 1 base (- (int (first step)) ascii-A)))

(defn parallel-assembly-step
  "Given the current state, jump forward to the next time a step
   can either be started or completed"
  [base n-workers {:keys [graph WIP time steps]}]
  (let [can-assign       (- n-workers (count WIP))
        available        (->> (next-nodes graph)
                              (remove (set (keys WIP)))
                              (take can-assign))
        times            (map #(step-time base %) available)
        new-WIP          (into WIP (zipmap available times))
        [step jump-time] (first (sort-by val new-WIP))]
    {:graph (finish-step graph step)
     :WIP   (->> (dissoc new-WIP step)
                 (u/fmap #(- % jump-time)))
     :time  (+ time jump-time)
     :steps (conj steps step)}))

(defn parallel-assembly
  "Return the final state after all assembly steps have been completed"
  [base n-workers graph]
  (->> {:graph graph :WIP {} :time 0 :steps []}
       (iterate #(parallel-assembly-step base n-workers %))
       (drop-while (comp seq :graph))
       first))

(defn assembly-time
  "Compute the time it takes to complete the assembly"
  [base n-workers graph]
  (:time (parallel-assembly base n-workers graph)))

(defn assembly-steps
  "Return a string representing the assembly steps in the order in
   which they were completed"
  [base n-workers graph]
  (apply str (:steps (parallel-assembly base n-workers graph))))

;; Puzzle solutions
(defn part1
  "In what order should the steps in your instructions be completed?"
  [input]
  (assembly-steps 0 1 input))

(defn part2
  "With 5 workers and the 60+ second step durations described above, 
   how long will it take to complete all of the steps?"
  [input]
  (assembly-time step-duration n-workers input))