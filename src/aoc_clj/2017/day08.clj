(ns aoc-clj.2017.day08
  "Solution to https://adventofcode.com/2017/day/8")

;; Input parsing
(def ineq-map
  {"<" <
   ">" >
   "<=" <=
   ">=" >=
   "==" =
   "!=" not=})

(defn nil+
  [a b]
  (if (nil? a)
    b
    (+ a b)))

(defn nil-
  [a b]
  (if (nil? a)
    (- b)
    (- a b)))

(def op-map
  {"inc" nil+
   "dec" nil-})

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
  [state [reg op amt if-reg ineq val]]
  (if (ineq (get state if-reg 0) val)
    (update state reg op amt)
    state))

(defn largest-value
  [insts]
  (->> (reduce step {} insts)
       vals
       (apply max)))

(defn largest-value-ever
  [insts]
  (->> (reductions step {} insts)
       (mapcat vals)
       (apply max)))

;; Puzzle solutions
(defn part1
  [input]
  (largest-value input))

(defn part2
  [input]
  (largest-value-ever input))