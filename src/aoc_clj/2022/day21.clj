(ns aoc-clj.2022.day21
  "Solution to https://adventofcode.com/2022/day/21"
  (:require [clojure.string :as str]))

;;;; Input parsing

(defn parse-line
  [line]
  (let [[l r] (str/split line #": ")
        args  (str/split r #" ")]
    [l (if (= 1 (count args))
         (read-string (first args))
         args)]))

(defn parse
  [input]
  (into {} (map parse-line input)))

;;;; Puzzle logic

(defn equation
  "Given the input equations in `jobs`, return an equation in prefix
   notation for the given `monkey` node"
  [jobs monkey]
  (let [value (jobs monkey)]
    (if (vector? value)
      (let [[a op b] value]
        (list (read-string op) (equation jobs a) (equation jobs b)))
      value)))

(defn root-yell
  "What value will the monkey named root yell given `jobs`"
  [jobs]
  (eval (equation jobs "root")))

(defn update-jobs
  "Update `jobs` given the new information in part 2.
   
   The correct operation for monkey root should be =, which means that it 
   still listens for two numbers (from the same two monkeys as before), but 
   now checks that the two numbers match.
   
   Second, you got the wrong monkey for the job starting with humn:. It isn't 
   a monkey - it's you. Actually, you got the job wrong, too: you need to 
   figure out what number you need to yell so that root's equality check passes.
   (The number that appears after humn: in your input is now irrelevant.)"
  [jobs]
  (-> jobs
      (assoc-in ["root" 1] "=")
      (assoc-in ["humn"] :x)))

(defn knowable?
  "Is the given (sub-expression) computable (i.e. does not contain the 
   unknown variable"
  [expr]
  (try
    (eval expr)
    (not (keyword? expr))
    (catch Exception _ false)))

(def inverse-op
  "Invert operation map: addition <-> subtraction, multiplication <-> division"
  {'* '/
   '/ '*
   '+ '-
   '- '+})

(defn inverted
  [new-eqn op arg knowns]
  [new-eqn (list op arg knowns)])

(defn invert
  "Given a binary arithmetic expression its equivalent value in `knowns`,
   invert one layer of the arithmetic expression, applying the inverted
   operation to the knowns"
  [[[op left right] knowns]]
  ;; Subtraction and division need to be handled carefully.
  ;; x - a = b inverts to x = a + b, but
  ;; a - x = b inverts to x = a - b (and similarly for division)
  (if (or (= op '/) (= op '-))
    ;; If division or subtraction
    (if (knowable? left)
      (inverted right op left knowns)
      (inverted left (inverse-op op) right knowns))
    ;; Else multiplication or addition
    (if (knowable? left)
      (inverted right (inverse-op op) knowns left)
      (inverted left  (inverse-op op) knowns right))))

(defn handle-equality
  [[_ a b]]
  (if (knowable? a)
    [b (eval a)]
    [a (eval b)]))

(defn solve-for-x
  "Find the expression that solves for x"
  [equation]
  (loop [[eqn knowns] (handle-equality equation)]
    (if (keyword? eqn)
      knowns
      (recur (invert [eqn knowns])))))

(defn humn-value
  "Given the jobs and the updated information in part 2, find the
   value that the human should yell to satisfy the equality"
  [jobs]
  (-> jobs
      update-jobs
      (equation "root")
      solve-for-x
      eval))

;;;; Puzzle solutions

(defn day21-part1-soln
  "What number will the monkey named root yell?"
  [input]
  (root-yell input))

(defn day21-part2-soln
  "What number do you yell to pass root's equality test?"
  [input]
  (humn-value input))