(ns aoc-clj.2017.day08
  "Solution to https://adventofcode.com/2017/day/8")

;; Input parsing
(defn nil+
  "Same as `+`, but if the first arg is nil, treats it as 0"
  [a b]
  (if (nil? a)
    b
    (+ a b)))

(defn nil-
  "Same as `-`, but if the first arg is nil, treats it as 0"
  [a b]
  (if (nil? a)
    (- b)
    (- a b)))

(def op-map
  {"inc" nil+
   "dec" nil-})

(def ineq-map
  {"<" <
   ">" >
   "<=" <=
   ">=" >=
   "==" =
   "!=" not=})

(def pattern #"(\w+) (\w+) (-?\d+) if (\w+) (\S+) (-?\d+)")

(defn parse-line
  [line]
  (let [[reg op amt if-reg ineq val] (rest (re-find pattern line))]
    [reg (op-map op) (read-string amt) if-reg (ineq-map ineq) (read-string val)]))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn step
  "Apply the instruction to the state and returns the new state"
  [state [reg op amt if-reg ineq val]]
  (if (ineq (get state if-reg 0) val)
    (update state reg op amt)
    state))

(defn largest-value
  "Executes all instructions and returns the largest value in any register"
  [insts]
  (->> (reduce step {} insts)
       vals
       (apply max)))

(defn largest-value-ever
  "Executes all instructions and returns the largest value that any register
   held during processing"
  [insts]
  (->> (reductions step {} insts)
       (mapcat vals)
       (apply max)))

;; Puzzle solutions
(defn part1
  "What is the largest value in any register after completing the instructions?"
  [input]
  (largest-value input))

(defn part2
  "What is the largest value held in any register during the process?"
  [input]
  (largest-value-ever input))