(ns aoc-clj.2022.day19
  "Solution to https://adventofcode.com/2022/day/19"
  (:require [clojure.set :as set]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [[a b c d e f g] (map read-string (re-seq #"\d+" line))]
    ;; The blueprint requirements are ordered ore, clay, obsidian, geode
    [a [[b 0 0 0]
        [c 0 0 0]
        [d e 0 0]
        [f 0 g 0]]]))

(defn parse
  [input]
  (into {} (mapv parse-line input)))

(def d19-s01
  (parse
   ["Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian."
    "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian."]))

(def day19-input (parse (u/puzzle-input "2022/day19-input.txt")))
(def init-inventory
  "The initial inventory is one ore robot.
   Everything follows the order: ore, clay, obsidian, geode
   The first vec is the number of robots of each type.
   The second vec is the amount of each type of material available"
  [[1 0 0 0] [0 0 0 0]])

(defn consume-materials
  "Use up the materials in `ingredients` in order to make a new robot"
  [[robots materials] ingredients]
  [robots (mapv - materials ingredients)])

(defn gather-materials
  "Have each robot gather the materials they collect and increase the materials
   inventory"
  [[robots materials]]
  [robots (mapv + materials robots)])

(defn add-robot
  "Add a new `robot` of given type 0,1,2,3 (corresponding to ore, clay, obsidian
   or geode) to the inventory"
  [[robots materials] robot]
  [(update robots robot inc) materials])

(defn step
  "Each robot can collect 1 of its resource type per minute. 
   It also takes one minute for the robot factory (also conveniently 
   from your pack) to construct any type of robot, although it consumes the 
   necessary resources available when construction begins.
   
   In every step (1 minute), 
   (a) if building a robot, instantly consuming those resources
   (b) let each active robot gather 1 unit of their resource type
   (c) add the newly built robots into the inventory"
  [blueprint inventory choice]
  (if (= :noop choice)
    (gather-materials inventory)
    (-> inventory
        (consume-materials (blueprint choice))
        gather-materials
        (add-robot choice))))

(defn inventory-score
  [[robots materials]]
  (-> (interleave materials robots) reverse vec))

(def desc-compare
  "Compare descending instead of ascending"
  (comp - compare))

(defn keep-best
  [states]
  (->> states
       (sort-by inventory-score desc-compare)
       (take 500)))

(defn can-build?
  [[_ materials] spec]
  (every? true? (map >= materials spec)))

(defn options
  [inventory blueprint]
  (->> blueprint
       (map-indexed (fn [i spec] [i (can-build? inventory spec)]))
       (filter second)
       (mapv first)
       (cons :noop)))

(defn next-states
  [blueprint state]
  (->> (options state blueprint)
       (map #(step blueprint state %))
       set))

(defn all-next-states
  [blueprint states]
  (->> states
       (map #(next-states blueprint %))
       (apply clojure.set/union)
       keep-best))

(defn best-outcomes
  [blueprint time-limit]
  (->> (iterate #(all-next-states blueprint %) #{init-inventory})
       (drop time-limit)
       first))

(defn geode-count
  [[_ [_ _ _ geodes]]]
  geodes)

(defn max-geodes
  [blueprint time-limit]
  (->> (best-outcomes blueprint time-limit)
       (map geode-count)
       (apply max)))

(defn quality-level
  [[id blueprint]]
  (* id (max-geodes blueprint 24)))

(defn quality-level-sum
  [blueprints]
  (reduce + (map #(quality-level %) blueprints)))

(defn max-geode-product
  [blueprints]
  (->> [(get blueprints 1) (get blueprints 2) (get blueprints 3)]
       (map #(max-geodes % 32))
       (reduce *)))

(defn day19-part1-soln
  []
  (quality-level-sum day19-input))

(defn day19-part2-soln
  []
  (max-geode-product day19-input))