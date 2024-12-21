(ns aoc-clj.2024.day17
  "Solution to https://adventofcode.com/2024/day/17"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn get-nums
  [s]
  (map read-string (re-seq #"\d+" s)))

(defn parse
  [input]
  (let [[reg-str prog-str] (u/split-at-blankline input)]
    {:regs (zipmap [:a :b :c] (mapcat get-nums reg-str))
     :prog (vec (get-nums (first prog-str)))}))

;; Puzzle logic
(def combo-map
  {4 :a
   5 :b
   6 :c})

(defn combo-operand
  "Returns the combo operand value.
   Combo operands 0-3 represent the literal values 0 through 3
   Combo operands 4, 5, and 6 map to register A, B, and C, respectively."
  [regs operand]
  (if (<= operand 3)
    operand
    (regs (combo-map operand))))

(defn inc-ip
  "Increases the instruction pointer by 2"
  [state]
  (update state :ip + 2))

(defn division
  "Computes a truncated division operation, with register A's value in the
   numerator and saving the result in the register given by `store`.
   
   The divisor is 2 to the power of the combo operand"
  [store {:keys [regs] :as state} operand]
  (let [two-pow (bit-shift-left 1 (combo-operand regs operand))]
    (-> state
        (assoc-in [:regs store] (quot (regs :a) two-pow))
        inc-ip)))

(def adv (partial division :a))
(def bdv (partial division :b))
(def cdv (partial division :c))

(defn bxl
  "Bitwise XOR of register B and literal operand, stored in B"
  [{:keys [regs] :as state} operand]
  (-> state
      (assoc-in [:regs :b] (bit-xor (regs :b) operand))
      inc-ip))

(defn bst
  "Combo operand mod 8, stored in B"
  [{:keys [regs] :as state} operand]
  (-> state
      (assoc-in [:regs :b] (mod (combo-operand regs operand) 8))
      inc-ip))

(defn jnz
  "Do nothing if register A is zero, else set instruction pointer to
   value of literal operand"
  [{:keys [regs] :as state} operand]
  (if (zero? (regs :a))
    (inc-ip state)
    (assoc state :ip operand)))

(defn bxc
  "Bitwise XOR of register B and register C, stored in B"
  [{:keys [regs] :as state} _]
  (-> state
      (assoc-in [:regs :b] (bit-xor (regs :b) (regs :c)))
      inc-ip))

(defn out
  "Combo operand mod 8, appended to output"
  [{:keys [regs] :as state} operand]
  (-> state
      (update :out conj (mod (combo-operand regs operand) 8))
      inc-ip))

(def opcode-map
  {0 adv
   1 bxl
   2 bst
   3 jnz
   4 bxc
   5 out
   6 bdv
   7 cdv})

(defn step
  "Increments the program state by one step, based on the current
   instruction pointer `ip`"
  [{:keys [ip prog] :as state}]
  (let [[opcode operand] (take 2 (drop ip prog))]
    ((opcode-map opcode) state operand)))

(defn running?
  "Returns true while the instruction pointer is in bounds of the program"
  [{:keys [ip prog]}]
  (< -1 ip (count prog)))

(defn init-state
  "Returns the initial state of the program"
  [input]
  (into input {:ip 0 :out []}))

(defn execute
  "Executes the instructions in the program until the instruction pointer
   is out of bounds of the instructions and returns the final state"
  [input]
  (->> (init-state input)
       (iterate step)
       (drop-while running?)
       first))

;; Puzzle solutions
(defn part1
  "Once it halts, what do you get if you use commas to join the values it
   output into a single string?"
  [input]
  (str/join "," (:out (execute input))))