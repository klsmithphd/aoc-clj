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
    (-> state
        (assoc-in [:wires out] ((gate-map op) (wires w1) (wires w2)))
        ;; (update :gates dissoc wireset)
        )))

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