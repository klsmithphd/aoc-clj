(ns aoc-clj.2017.day20
  "Solution to https://adventofcode.com/2017/day/20"
  (:require [aoc-clj.utils.vectors :as vec]))

;; Constants
(def n-ticks
  "The number of ticks it seems to take given my puzzle input before
   there aren't going to be any more collisions"
  50)

;; Input parsing
(defn parse-line
  [line]
  (->> (re-seq #"-?\d+" line)
       (map read-string)
       (partition 3)
       (zipmap [:p :v :a])))

(defn parse
  [input]
  (mapv parse-line input))

;; Puzzle logic
(defn closest-to-origin
  "Return the id of the particle that will stay closest to the origin.
   
   Over an infinite time, this will be the particle that has the lowest
   acceleration, regardless of its initial position or velocity"
  [particles]
  (->> particles
       (map-indexed (fn [idx p] [idx (vec/l2-norm (:a p))]))
       (apply min-key second)
       first))

(defn tick
  "Returns the next state of a particle when one clock tick has elapsed"
  [{:keys [p v a]}]
  (let [next-v (vec/vec-add v a)
        next-p (vec/vec-add p next-v)]
    {:p next-p
     :v next-v
     :a a}))

(defn update-without-collisions
  "Returns the new state of all particles after one tick, with
   any particles occupying the same position (collisions) removed"
  [particles]
  (let [new-state (map tick particles)
        collisions (->> (map :p new-state)
                        frequencies
                        (filter #(> (val %) 1))
                        keys
                        set)]
    (remove #(collisions (:p %)) new-state)))

(defn final-count
  "After `n` ticks, returns the number of simulated particles remaining,
   removing any intermediate collisions as they happen"
  [n particles]
  (loop [i 0 state particles]
    (if (or (< (count state) 2) (> i n))
      (count state)
      (recur (inc i) (update-without-collisions state)))))

;; Puzzle solutions
(defn part1
  "Which particle will stay cloests to position <0,0,0> in the long run?"
  [input]
  (closest-to-origin input))

(defn part2
  "How many particles are left after all collisions are resolved?"
  [input]
  (final-count n-ticks input))