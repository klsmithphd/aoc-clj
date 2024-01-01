(ns aoc-clj.2015.day23
  "Solution to https://adventofcode.com/2015/day/23"
  (:require [clojure.string :as str]))

(defn parse-line
  [line]
  (let [args       (str/split line #", ")
        [inst val] (str/split (first args) #" ")
        base       {:inst (keyword inst)
                    :arg1 (if (number? (read-string val)) (read-string val) val)}]
    (if (> (count args) 1)
      (assoc base :arg2 (read-string (second args)))
      base)))

(defn parse
  [input]
  (mapv parse-line input))

(defn apply-inst
  [instructions {:keys [next-inst] :as state}]
  (let [{:keys [inst arg1 arg2]} (nth instructions next-inst)
        reg (keyword arg1)]
    (case inst
      :hlf (-> state
               (update reg #(int (/ % 2)))
               (update :next-inst inc))
      :tpl (-> state
               (update reg * 3)
               (update :next-inst inc))
      :inc (-> state
               (update reg inc)
               (update :next-inst inc))
      :jmp (update state :next-inst + arg1)
      :jie (if (even? (get state reg))
             (update state :next-inst + arg2)
             (update state :next-inst inc))
      :jio (if (= 1 (get state reg))
             (update state :next-inst + arg2)
             (update state :next-inst inc)))))

(defn not-done?
  [max-inst next-inst]
  (and (>= next-inst 0)
       (< next-inst max-inst)))

(defn run-program
  [instructions init]
  (let [max-inst (count instructions)]
    (first (drop-while #(not-done? max-inst (:next-inst %)) (iterate (partial apply-inst instructions) init)))))

(defn day23-part1-soln
  [input]
  (:b (run-program input {:a 0 :b 0 :next-inst 0})))

(defn day23-part2-soln
  [input]
  (:b (run-program input {:a 1 :b 0 :next-inst 0})))