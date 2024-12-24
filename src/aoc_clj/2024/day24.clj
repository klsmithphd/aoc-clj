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
    [w1 w2 (keyword (str/lower-case op)) w3]))

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
    (filter #(= 2 (count (set/intersection wireset (set (take 2 %))))) gates)))

(defn wires-starting-with
  [letter wires]
  (->> wires
       (filter #(str/starts-with? (key %) letter))))

(def x-wires (partial wires-starting-with "x"))
(def y-wires (partial wires-starting-with "y"))
(def z-wires (partial wires-starting-with "z"))

(defn output-bits
  "Returns the bits from wires whose name starts with a `z`, in descending
   order (from highest order bit to lowest)"
  [{:keys [wires]}]
  (->> (z-wires wires)
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
  [{:keys [wires] :as state} [w1 w2 op out]]
  (assoc-in state [:wires out] ((gate-map op) (wires w1) (wires w2))))

(defn step
  "Updates the state of the circuit with all ready gates"
  [state]
  (let [ready (ready-gates state)]
    (-> (reduce apply-gate state ready)
        (update :gates #(remove (set ready) %)))))

(defn circuit-output
  "Returns the decimal value output by the circuit after all gates have been
   applied"
  [state]
  (->> (u/converge step state)
       last
       output-bits
       output-val))

(def half-adder
  "Returns a collection of half-adder logic gates for the first bits
   
   See https://en.wikipedia.org/wiki/Adder_(electronics)#Half_adder"
  [["x00" "y00" :xor "z00"]
   ["x00" "y00" :and "c00"]])

(defn- var-name
  "Helper fn to return a formatted string name"
  [prefix num]
  (str prefix (format "%02d" num)))

(defn full-adder
  "Returns a collection of logic gates implementing a full adder for bit
   `n` when summing up to maximum input bits `maxn`.
   
   See https://en.wikipedia.org/wiki/Adder_(electronics)#Full_adder"
  [maxn n]
  [[(var-name "x" n) (var-name "y" n)       :xor (var-name "s" n)]
   [(var-name "s" n) (var-name "c" (dec n)) :xor (var-name "z" n)]
   [(var-name "s" n) (var-name "c" (dec n)) :and (var-name "a" n)]
   [(var-name "x" n) (var-name "y" n)       :and (var-name "b" n)]
   [(var-name "a" n) (var-name "b" n)       :or (if (= n maxn)
                                                  (var-name "z" (inc n))
                                                  (var-name "c" n))]])

(defn correct-gates
  "Returns a correct collection of logic gates to perform bitwise
   addition for up to `maxn` input bits."
  [maxn]
  (concat half-adder (->> (range 1 (inc maxn))
                          (mapcat #(full-adder maxn %)))))

;; Given the correct circuit, we need to figure out how our
;; incorrect circuit should map to it.

;; xNN yNN :xor uniquely defines sNN
;; xNN yNN :and uniquely defines bNN
;; When sNN is determined, zNN, sNN and :xOR identifies cNN-1
;; cNN-1 sNN :and identifies aNN
;; aNN bNN should identify cNN or zNN+1

;; Puzzle solutions
(defn part1
  "What decimal number does it output on the wires starting with z?"
  [input]
  (circuit-output input))

