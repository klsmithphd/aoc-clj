(ns aoc-clj.2015.day23
  "Solution to https://adventofcode.com/2015/day/23"
  (:require [clojure.string :as str]))

;; Constants
(def init-state {:a 0 :b 0 :next-inst 0})

;; Input parsing
(defn parse-line
  [line]
  (let [args       (str/split line #", ")
        [inst val] (str/split (first args) #" ")
        base       {:inst (keyword inst)
                    :arg1 (if (number? (read-string val))
                            (read-string val)
                            (keyword val))}]
    (if (> (count args) 1)
      (assoc base :arg2 (read-string (second args)))
      base)))

(defn parse
  [input]
  (mapv parse-line input))

;; Puzzle logic
(defn apply-inst
  "Updates the registers' state given the next instruction from 
   `instructions`"
  [instructions {:keys [next-inst] :as state}]
  (let [{:keys [inst arg1 arg2]} (nth instructions next-inst)]
    (case inst
      :hlf (-> state
               (update arg1 #(quot % 2))
               (update :next-inst inc))
      :tpl (-> state
               (update arg1 * 3)
               (update :next-inst inc))
      :inc (-> state
               (update arg1 inc)
               (update :next-inst inc))
      :jmp (update state :next-inst + arg1)
      :jie (if (even? (get state arg1))
             (update state :next-inst + arg2)
             (update state :next-inst inc))
      :jio (if (= 1 (get state arg1))
             (update state :next-inst + arg2)
             (update state :next-inst inc)))))

(defn not-done?
  "The program is not complete while the next instruction is still
  within the bounds of the ones provided"
  [max-inst next-inst]
  (< -1 next-inst max-inst))

(defn run-program
  "Execute the logic in `instructions` against the starting `state`"
  [instructions state]
  (let [max-inst (count instructions)]
    (->> (iterate #(apply-inst instructions %) state)
         (drop-while #(not-done? max-inst (:next-inst %)))
         first)))

;; Puzzle solutions
(defn part1
  "The value in register b after executing the program"
  [input]
  (:b (run-program input init-state)))

(defn part2
  "The value in register b after executing the program with register a
   starting as 1"
  [input]
  (:b (run-program input (assoc init-state :a 1))))