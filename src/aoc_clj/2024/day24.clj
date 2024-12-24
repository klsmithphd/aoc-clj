(ns aoc-clj.2024.day24
  "Solution to https://adventofcode.com/2024/day/24"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]
            [clojure.set :as set]))

;; Input parsing
(defn parse-inputs
  [input]
  [(subs input 0 3) (read-string (subs input 4))])

(defn parse-gates
  [gates]
  (let [[w1 op w2 _ w3] (str/split gates #" ")]
    [#{w1 w2} [(keyword (str/lower-case op)) w3]]))

(defn parse
  [input]
  (let [[inputs logic] (u/split-at-blankline input)]
    {:wires (into {} (map parse-inputs inputs))
     :gates (mapv parse-gates logic)}))

;; Puzzle logic
(defn ready-gates
  "Returns the gates whose inputs are ready"
  [{:keys [wires gates]}]
  (let [wireset (set (keys wires))]
    (filter #(= 2 (count (set/intersection wireset (first %)))) gates)))

(defn output-bits
  "Returns the bits from wires whose name starts with a `z`, in descending
   order (from highest order bit to lowest)"
  [{:keys [wires]}]
  (->> wires
       (filter #(str/starts-with? (key %) "z"))
       sort
       vals
       reverse))

(defn output-val
  "Returns the output value as a decimal integer"
  [bits]
  (->> bits
       (apply str)
       (str "2r")
       read-string))

(def gate-map
  "Mapping between the op keywords and the actual function"
  {:and bit-and
   :or  bit-or
   :xor bit-xor})

(defn apply-gate
  "Updates the state of the circuit with the result of a ready gate"
  [{:keys [wires] :as state} [wireset [op out]]]
  (let [[w1 w2] (seq wireset)]
    (assoc-in state [:wires out] ((gate-map op) (wires w1) (wires w2)))))

(defn step
  "Updates the state of the circuit with all ready gates"
  [state]
  (let [ready (ready-gates state)]
    (->
     (reduce apply-gate state ready)
     (update :gates #(remove (set ready) %)))))

(defn circuit-output
  "Returns the decimal value output by the circuit after all gates have been
   applied"
  [state]
  (->> (u/converge step state)
       last
       output-bits
       output-val))

;; Puzzle solutions
(defn part1
  "What decimal number does it output on the wires starting with z?"
  [input]
  (circuit-output input))

;; x00: 1
;; x01: 1
;; x02: 0
;; x03: 1

;; y00: 1
;; y01: 0
;; y02: 1
;; y03: 1

;; z00: 0
;; z01: 0
;; z02: 0
;; z03: 1
;; z04: 1

;; carry-over bit is AND
;; sum bit is XOR

(defn bitwise-addition
  [a b]
  [(bit-and a b) (bit-xor a b)])

(bitwise-addition 1 1)

;; 44 OR gates
;; 89 = 44*2 + 1 XOR gates
;; 89 = 44*2 + 1 AND gates

;; A full adder can be constructed five gates: 1 OR, 2 XOR, 2 AND
;; The first XOR takes the "sum" bit of inputs A and B
;; The output of that XOR and the carry-in bit is XOR'd to the sum bit
;; 
;; AND the intermediate sum and the carry-in bit
;; AND the original two values A and B
;; OR these two to get the carry-over bit

;; So the gist here is to wire up the full network that
;; would be necessary to correctly perform addition.
;;
;; There are a bunch of intermediate nodes in that graph, to
;; which we can give names.
;;
;; The puzzle input gives different names to the intermediate
;; outputs, but we can try to figure out where they don't match

