(ns aoc-clj.2018.day12
  "Solution to https://adventofcode.com/2018/day/12"
  (:require [aoc-clj.utils.core :as u]
            [clojure.string :as str]))

;; Constants
(def charmap {\# 1 \. 0})
(def part1-generations 20)
(def part2-generations 50000000000)

;; Input parsing
(defn parse-state
  [[state]]
  (mapv charmap (subs state 15)))

(defn parse-rule
  [rule]
  (let [[l [r]] (str/split rule #" => ")]
    [(mapv charmap l) (charmap r)]))

(defn parse-rules
  [rules]
  (into {} (map parse-rule rules)))

(defn parse
  [input]
  (let [[pots rules] (u/split-at-blankline input)]
    {:pots  (parse-state pots)
     :rules (parse-rules rules)
     :left  0}))

;; Puzzle logic
(defn step
  "Evolves the pot state forward by one iteration"
  [{:keys [pots rules] :as state}]
  (let [slices (->> (concat [0 0 0] pots [0 0 0])
                    (partition 5 1))]
    (-> state
        (assoc :pots (mapv #(get rules % 0) slices))
        (update :left dec))))

(defn pots-with-plants
  "Returns the ids of the pots that contain a plant"
  [{:keys [pots left]}]
  (->> (map-indexed vector pots)
       (filter (comp pos? second))
       (map #(+ left (first %)))))

(defn pot-sum-at-n
  "Returns the sum of all the pot ids that contain a plant after n
   generations"
  [n state]
  (->> state
       (iterate step)
       (drop n)
       first
       pots-with-plants
       (reduce +)))

(defn n-until-linear
  "Finds the n value at which the evolution gets into a linearly evolving
   pattern, where the number of new pots each generation becomes a constant."
  [state]
  (let [pot-seq    (->> (range 200)
                        (map #(pot-sum-at-n % state)))
        deltas-seq (->> pot-seq
                        (partition 2 1)
                        (map #(apply - (reverse %)))
                        (partition 5 1)
                        (take-while #(apply not= %)))
        skip-to    (count deltas-seq)
        pot-count  (nth pot-seq skip-to)]
    [skip-to pot-count (last (last deltas-seq))]))

(defn pot-sum-at-large-n
  "Returns the sum of the occupied pots after n generations by
   finding when the growth becomes linear and then extrapolating."
  [n state]
  (let [[repeat-n count-at-repeat delta] (n-until-linear state)]
    (+ count-at-repeat (* (- n repeat-n) delta))))

;; Puzzle solutions
(defn part1
  "After 20 generations, what is the sum of the numbers of all pots
   which contain a plant?"
  [input]
  (pot-sum-at-n part1-generations input))

(defn part2
  "After fifty billion (50000000000) generations, 
   what is the sum of the numbers of all pots which contain a plant?"
  [input]
  (pot-sum-at-large-n part2-generations input))