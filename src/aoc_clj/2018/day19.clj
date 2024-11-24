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
  "Returns an updated set of registers based on provided program and
   the current register values"
  [{:keys [ip insts]} regs]
  (let [pos         (get regs ip)
        [op & args] (get insts pos)]
    (-> ((d16/opcodes op) regs args)
        (update ip inc))))

(defn in-bounds?
  "Returns true if the instruction pointer's value is within the 
   bounds of the instructions"
  [{:keys [ip insts]} regs]
  (let [pos (get regs ip)]
    (< -1 pos (count insts))))

(defn execute
  "Given an initial set of registers and the program, executes the
   program until the instruction pointer is out of bounds of the
   provided commands"
  [regs program]
  (let [stepper  (partial step program)
        running? (partial in-bounds? program)]
    (->> regs
         (iterate stepper)
         (drop-while running?)
         first)))

(defn reg0-after-execution
  "Returns the value in register 0 after the program terminates"
  [regs program]
  (first (execute regs program)))

;; Puzzle solution
(defn part1
  "What value is left in register 0 when the background process halts?"
  [input]
  (reg0-after-execution init-regs input))

(defn part2
  "What value is left in register 0 when this new background process halts?"
  [input]
  (reg0-after-execution (assoc init-regs 0 1) input))