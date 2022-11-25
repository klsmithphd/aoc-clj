(ns aoc-clj.utils.intcode.ops
  (:require [manifold.stream :as s]))

(defn read-parameter
  "Right now, your ship computer already understands parameter mode 0, 
   position mode, which causes the parameter to be interpreted as a position -
   if the parameter is 50, its value is the value stored at address 50 in memory.
   
   Now, your ship computer will also need to handle parameters in mode 1, 
   immediate mode. In immediate mode, a parameter is interpreted as a value - 
   if the parameter is 50, its value is simply 50.
   
   Parameters in mode 2, relative mode, behave very similarly to parameters in 
   position mode: the parameter is interpreted as a position. Like position 
   mode, parameters in relative mode can be read from or written to. The address 
   a relative mode parameter refers to is itself plus the current relative base."
  [{:keys [intcode base]} mode value]
  (case mode
    :immediate value
    :position (get intcode value 0)
    :relative (get intcode (+ base value) 0)))

(defn read-args
  [state modes params]
  (map (partial read-parameter state) modes params))

(defn write-value
  [{:keys [intcode base]} mode pos value]
  (let [location (case mode
                   :position pos
                   :relative (+ base pos))
        overflow (- location (count intcode))]
    (assoc (into intcode (repeat overflow 0)) location value)))

(into [0 1 2] (repeat 0 :a))

(defn add
  "Opcode 1 adds together numbers read from two positions and stores the result in a third position. 
   The three integers immediately after the opcode tell you these three positions - 
   the first two indicate the positions from which you should read the input values, 
   and the third indicates the position at which the output should be stored."
  [{:keys [iptr]
    [m1 m2 m3] :modes
    [p1 p2 p3] :params
    :as state}]
  (let [sum (apply + (read-args state [m1 m2] [p1 p2]))]
    (assoc state
           :intcode (write-value state m3 p3 sum)
           :iptr    (+ iptr 4))))

(defn multiply
  "Opcode 2 works exactly like opcode 1, except it multiplies the two inputs instead of adding them."
  [{:keys [iptr]
    [m1 m2 m3] :modes
    [p1 p2 p3] :params
    :as state}]
  (let [product (apply * (read-args state [m1 m2] [p1 p2]))]
    (assoc state
           :intcode (write-value state m3 p3 product)
           :iptr    (+ iptr 4))))

(defn input
  "Opcode 3 takes a single integer as input and saves it to the position given by its only parameter. 
   For example, the instruction 3,50 would take an input value and store it at address 50."
  [{:keys [iptr in]
    [m1] :modes
    [p1] :params
    :as state}]
  (assoc state
         :intcode (write-value state m1 p1 @(s/take! in))
         :iptr    (+ iptr 2)))

(defn output
  "Opcode 4 outputs the value of its only parameter. 
   For example, the instruction 4,50 would output the value at address 50."
  [{:keys [iptr out]
    [m1] :modes
    [p1] :params
    :as state}]
  (s/put! out (read-parameter state m1 p1))
  (assoc state :iptr (+ iptr 2)))

(defn jump-if-true
  "Opcode 5 is jump-if-true: if the first parameter is non-zero, it sets 
   the instruction pointer to the value from the second parameter. 
   Otherwise, it does nothing."
  [{:keys [iptr]
    [m1 m2] :modes
    [p1 p2] :params
    :as state}]
  (assoc state :iptr (if (zero? (read-parameter state m1 p1))
                       (+ iptr 3)
                       (read-parameter state m2 p2))))

(defn jump-if-false
  "Opcode 6 is jump-if-false: if the first parameter is zero, it sets 
   the instruction pointer to the value from the second parameter. 
   Otherwise, it does nothing."
  [{:keys [iptr]
    [m1 m2] :modes
    [p1 p2] :params
    :as state}]
  (assoc state :iptr (if (zero? (read-parameter state m1 p1))
                       (read-parameter state m2 p2)
                       (+ iptr 3))))

(defn less-than
  "Opcode 7 is less than: if the first parameter is less than the second 
   parameter, it stores 1 in the position given by the third parameter.
   Otherwise, it stores 0."
  [{:keys [iptr]
    [m1 m2 m3] :modes
    [p1 p2 p3] :params
    :as state}]
  (let [lessthan? (apply < (read-args state [m1 m2] [p1 p2]))]
    (assoc state
           :intcode (write-value state m3 p3 (if lessthan? 1 0))
           :iptr    (+ iptr 4))))

(defn equals
  "Opcode 8 is equals: if the first parameter is equal to the second 
   parameter, it stores 1 in the position given by the third parameter. 
   Otherwise, it stores 0."
  [{:keys [iptr]
    [m1 m2 m3] :modes
    [p1 p2 p3] :params
    :as state}]
  (let [equals? (apply = (read-args state [m1 m2] [p1 p2]))]
    (assoc state
           :intcode (write-value state m3 p3 (if equals? 1 0))
           :iptr    (+ iptr 4))))

(defn offset
  "Opcode 9 adjusts the relative base by the value of its only parameter. 
   The relative base increases (or decreases, if the value is negative) 
   by the value of the parameter."
  [{:keys [iptr base]
    [m1] :modes
    [p1] :params
    :as state}]
  (assoc state
         :base (+ base (read-parameter state m1 p1))
         :iptr (+ iptr 2)))

(defn halt
  [state]
  state)