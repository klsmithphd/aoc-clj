(ns aoc-clj.utils.intcode.ops
  (:require [manifold.stream :as s]))

(defn read-parameter
  "Right now, your ship computer already understands parameter mode 0, 
   position mode, which causes the parameter to be interpreted as a position -
   if the parameter is 50, its value is the value stored at address 50 in memory.
   
   Now, your ship computer will also need to handle parameters in mode 1, 
   immediate mode. In immediate mode, a parameter is interpreted as a value - 
   if the parameter is 50, its value is simply 50."
  [intcode mode value]
  (case mode
    :immediate value
    :position (intcode value)))

(defn read-args
  [intcode modes params]
  (map (partial read-parameter intcode) modes params))

(defn add
  "Opcode 1 adds together numbers read from two positions and stores the result in a third position. 
   The three integers immediately after the opcode tell you these three positions - 
   the first two indicate the positions from which you should read the input values, 
   and the third indicates the position at which the output should be stored."
  [{:keys [intcode iptr]
    [m1 m2 _]  :modes
    [p1 p2 p3] :params
    :as state}]
  (assoc state
         :intcode (assoc intcode p3 (apply + (read-args intcode [m1 m2] [p1 p2])))
         :iptr    (+ iptr 4)))

(defn multiply
  "Opcode 2 works exactly like opcode 1, except it multiplies the two inputs instead of adding them."
  [{:keys [intcode iptr]
    [m1 m2 _]  :modes
    [p1 p2 p3] :params
    :as state}]
  (assoc state
         :intcode (assoc intcode p3 (apply * (read-args intcode [m1 m2] [p1 p2])))
         :iptr    (+ iptr 4)))

(defn input
  "Opcode 3 takes a single integer as input and saves it to the position given by its only parameter. 
   For example, the instruction 3,50 would take an input value and store it at address 50."
  [{:keys [intcode iptr in]
    [p1] :params
    :as state}]
  (assoc state
         :intcode (assoc intcode p1 @(s/take! in))
         :iptr    (+ iptr 2)))

(defn output
  "Opcode 4 outputs the value of its only parameter. 
   For example, the instruction 4,50 would output the value at address 50."
  [{:keys [intcode iptr out]
    [m1] :modes
    [p1] :params
    :as state}]
  (s/put! out (read-parameter intcode m1 p1))
  (assoc state :iptr (+ iptr 2)))

(defn jump-if-true
  "Opcode 5 is jump-if-true: if the first parameter is non-zero, it sets 
   the instruction pointer to the value from the second parameter. 
   Otherwise, it does nothing."
  [{:keys [intcode iptr]
    [m1 m2] :modes
    [p1 p2] :params
    :as state}]
  (assoc state :iptr (if (zero? (read-parameter intcode m1 p1))
                       (+ iptr 3)
                       (read-parameter intcode m2 p2))))

(defn jump-if-false
  "Opcode 6 is jump-if-false: if the first parameter is zero, it sets 
   the instruction pointer to the value from the second parameter. 
   Otherwise, it does nothing."
  [{:keys [intcode iptr]
    [m1 m2] :modes
    [p1 p2] :params
    :as state}]
  (assoc state :iptr (if (zero? (read-parameter intcode m1 p1))
                       (read-parameter intcode m2 p2)
                       (+ iptr 3))))

(defn less-than
  "Opcode 7 is less than: if the first parameter is less than the second 
   parameter, it stores 1 in the position given by the third parameter.
   Otherwise, it stores 0."
  [{:keys [intcode iptr]
    [m1 m2 _] :modes
    [p1 p2 p3] :params
    :as state}]
  (assoc state
         :intcode (assoc intcode p3 (if (apply < (read-args intcode [m1 m2] [p1 p2])) 1 0))
         :iptr    (+ iptr 4)))

(defn equals
  "Opcode 8 is equals: if the first parameter is equal to the second 
   parameter, it stores 1 in the position given by the third parameter. 
   Otherwise, it stores 0."
  [{:keys [intcode iptr]
    [m1 m2 _] :modes
    [p1 p2 p3] :params
    :as state}]
  (assoc state
         :intcode (assoc intcode p3 (if (apply = (read-args intcode [m1 m2] [p1 p2])) 1 0))
         :iptr    (+ iptr 4)))

(defn halt
  [state]
  state)