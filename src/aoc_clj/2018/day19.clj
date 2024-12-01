(ns aoc-clj.2018.day19
  "Solution to https://adventofcode.com/2018/day/19"
  (:require [clojure.math :as math]
            [aoc-clj.2018.day16 :as d16]))

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

;; (defn reg0-after-execution
;;   "Returns the value in register 0 after the program terminates"
;;   [regs program]
;;   (first (execute regs program)))

(defn run-till-initialize
  "Executes the program until it has completed its initialization steps
   and is ready to begin computation on line 1.
   
   I determined by walking through my opcode instructions that the
   program jumps to initialization logic, dependent upon the value in
   register 0 before setting the instruction pointer to line 1."
  [regs {:keys [ip] :as program}]
  (let [stepper  (partial step program)
        running? #(not= 1 (get % ip 0))]
    (->> regs
         (iterate stepper)
         (drop-while running?)
         first)))

(defn factors
  "Returns a set of all the integer factors of a given integer"
  [num]
  (->> (range 1 (math/sqrt num))
       (filter #(zero? (mod num %)))
       (map #(vector % (quot num %)))
       flatten
       set))

(defn sum-of-factors
  "Given the initial registers, runs the program through its initialization
   steps, then takes whatever value is in register 4, and then returns
   the sum of the factors of that number.
   
   I determined by reading through the opcode instructions that this
   is what the program is attempting to do very inefficiently O(N^2): It
   iterates registers 3 and 5 through the range 1..N and then adds one of
   register 3's value to reg 0 if their product equals what's in register 4.
   
   I can't guarantee all programs are structured the same way."
  [regs program]
  (->> (run-till-initialize regs program)
       (drop 4)
       first ;; In my program, the relevant value is in register 4
       factors
       (reduce +)))

;; Puzzle solution
(defn part1
  "What value is left in register 0 when the background process halts?"
  [input]
  (sum-of-factors init-regs input))

(defn part2
  "What value is left in register 0 when this new background process halts?"
  [input]
  (sum-of-factors (assoc init-regs 0 1) input))