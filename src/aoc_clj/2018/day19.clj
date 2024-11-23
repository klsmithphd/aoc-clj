(ns aoc-clj.2018.day19
  "Solution to https://adventofcode.com/2018/day/19"
  (:require [aoc-clj.2018.day16 :as d16]))

;; Constants
(def init-regs [0 0 0 0 0 0])

;; Input parsing
(defn parse-ops
  [op]
  (let [opcode (subs op 0 4)
        nums   (mapv read-string (re-seq #"\d+" op))]
    (cons (keyword opcode) nums)))

(defn parse
  [[line1 & lines]]
  {:ip (read-string (re-find #"\d" line1))
   :insts (mapv parse-ops lines)})

;; Puzzle logic
(defn step
  [{:keys [ip insts]} regs]
  (let [pos         (get regs ip)
        [op & args] (get insts pos)]
    (-> ((d16/opcodes op) regs args)
        (update ip inc))))

(defn running?
  [{:keys [ip insts]} regs]
  (let [pos (get regs ip)]
    (< -1 pos (count insts))))

(defn execute
  [input regs]
  (let [stepper (partial step input)
        going?  (partial running? input)]
    (->> regs
         (iterate stepper)
         (drop-while going?)
         first)))

;; Puzzle solution
(defn part1
  [input]
  (first (execute input init-regs)))