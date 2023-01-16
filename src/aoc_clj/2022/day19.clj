(ns aoc-clj.2022.day19
  "Solution to https://adventofcode.com/2022/day/19"
  (:require [clojure.set :as set]
            [aoc-clj.utils.core :as u]))

;;;; Constants

(def init-inventory
  "The initial inventory is one ore robot.
   Everything follows the order: ore, clay, obsidian, geode
   The first vec is the number of robots of each type.
   The second vec is the amount of each type of material available"
  [[1 0 0 0] [0 0 0 0]])

;;;; Input parsing

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

(def day19-input (u/parse-puzzle-input parse 2022 19))

;;;; Puzzle logic

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
  "Computes an _inventory score_, which is actually just a vector
   of the geode robot count, the geode count, the obsidian robot count,
   the obsidian count, etc. The logic is that it's more valuable to have
   a higher count of the more valuable robots firstmost and then secondmost
   to have a higher count of the gathered materials. 
   
   This vector then can work as a lexigraphic sort in terms of expected
   value"
  [[robots materials]]
  (-> (interleave materials robots) reverse vec))

(def desc-compare
  "Compare descending instead of ascending"
  (comp - compare))

(defn keep-best
  "Of the possible states currently being tracked, keep only the top
   500 best (as assessed by `inventory-score`)"
  [states]
  (->> states
       (sort-by inventory-score desc-compare)
       (take 500)))

(defn can-build?
  "Do we have sufficient raw materials to build the robot according to `spec`"
  [[_ materials] spec]
  (every? true? (map >= materials spec)))

(defn options
  "Given our current inventory and the blueprint, what choices do we have?
   Options always include the possibility of doing nothing, represented
   by `:noop`"
  [inventory blueprint]
  (->> blueprint
       (map-indexed (fn [i spec] [i (can-build? inventory spec)]))
       (filter second)
       (mapv first)
       (cons :noop)))

(defn next-states
  "Compute a set of the possible future states given the `blueprint`
   and `state`"
  [blueprint state]
  (->> (options state blueprint)
       (map #(step blueprint state %))
       set))

(defn all-next-states
  "Given the set of currently tracked best states, return the set
   of the top best next possible states"
  [blueprint states]
  (->> states
       (map #(next-states blueprint %))
       (apply clojure.set/union)
       keep-best))

(defn best-outcomes
  "Up to `time-limit`, for a given blueprint, determine the best
   possible outcome via a search of the state space"
  [blueprint time-limit]
  (->> (iterate #(all-next-states blueprint %) #{init-inventory})
       (drop time-limit)
       first))

(defn geode-count
  "Given a state, return the number of geodes"
  [[_ [_ _ _ geodes]]]
  geodes)

(defn max-geodes
  "Return the maximum number of geodes that can be gathered in 
   `time-limit` based on `blueprint`"
  [blueprint time-limit]
  (->> (best-outcomes blueprint time-limit)
       (map geode-count)
       (apply max)))

(defn quality-level
  "Determine the quality level of each blueprint by multiplying that 
   blueprint's ID number with the largest number of geodes that can be opened 
   in 24 minutes using that blueprint."
  [[id blueprint]]
  (* id (max-geodes blueprint 24)))

(defn quality-level-sum
  "Add up all the quality levels for the given blueprints"
  [blueprints]
  (reduce + (map #(quality-level %) blueprints)))

(defn max-geode-product
  "Multiply the number of geodes recoverable from the top three 
   blueprints in 32 minutes"
  [blueprints]
  (->> [(get blueprints 1) (get blueprints 2) (get blueprints 3)]
       (map #(max-geodes % 32))
       (reduce *)))

;;;; Puzzle solutions

(defn day19-part1-soln
  "Determine the quality level of each blueprint using the largest number of 
   geodes it could produce in 24 minutes. What do you get if you add up 
   the quality level of all of the blueprints in your list?"
  []
  (quality-level-sum day19-input))

(defn day19-part2-soln
  "You no longer have enough blueprints to worry about quality levels. 
   Instead, for each of the first three blueprints, determine the largest 
   number of geodes you could open; then, multiply these three values together.

   Don't worry about quality levels; instead, just determine the largest number
   of geodes you could open using each of the first three blueprints. 
   What do you get if you multiply these numbers together?"
  []
  (max-geode-product day19-input))