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
  (let [[i1 op i2 _ w3] (str/split gates #" ")
        [w1 w2]         (sort [i1 i2])]
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
  "Returns the wires whos name starts with the given letter"
  [letter wires]
  (->> wires
       (filter #(str/starts-with? (key %) letter))))

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

(defn adder-circuit
  "Returns a correct collection of logic gates to perform bitwise
   addition for up to `maxn` input bits."
  [maxn]
  (concat half-adder (->> (range 1 (inc maxn))
                          (mapcat #(full-adder maxn %)))))

(defn max-z-bit
  "Returns the number of the last z wire in the circuit"
  [{:keys [gates]}]
  (let [last-z (->> gates (map last) sort last)]
    (Integer/parseInt (subs last-z 1))))

(defn op-match
  "Returns the gates that either do or do not satisfy the `op-pred` predicate
   applied to the operation value, based on whether `in-out` is
   set to `:include` or `:exclude`."
  [in-out op-pred gates]
  ((case in-out
     :include filter
     :exclude remove) #(op-pred (nth % 2)) gates))

(defn input-wire-match
  "Returns the gates that either do or do not satisfy the `wire-pred` predicate
   applied to the input wires, based on whether `in-out` is
   set to `:include` or `:exclude`."
  [in-out wire-pred gates]
  ((case in-out
     :include filter
     :exclude remove) #(or (wire-pred (nth % 0))
                           (wire-pred (nth % 1))) gates))

(defn output-wire-match
  "Returns the gates that either do or do not satisfy the `wire-pred` predicate
   applied to the output wire, based on whether `in-out` is
   set to `:include` or `:exclude`."
  [in-out wire-pred gates]
  ((case in-out
     :include filter
     :exclude remove) #(wire-pred (nth % 3)) gates))

(defn zs-not-output-from-xor
  "Any of the z wires should only be attached to the output of an XOR
   gate, so any z wires appearing as the output from an AND or an OR 
   gate must be swapped."
  [gates]
  (->> (op-match :include #{:and :or} gates)
       (output-wire-match :include #(str/starts-with? % "z"))
       (map last)
       sort
       butlast))

(defn wrong-xor-outs
  "The outputs of an XOR gate that has the xNN and yNN wires as inputs
   should itself be an input to another XOR gate and an AND gate.
   This function returns the wires that don't meet that criteria and thus
   should be swapped."
  [gates]
  (let [op-finder (fn [wire]
                    (->> gates
                         (input-wire-match :include #(= wire %))
                         (map #(nth % 2))
                         set))
        xor-outs (->> gates
                      (op-match :include #{:xor})
                      (input-wire-match :include #(str/starts-with? % "x"))
                      (map last)
                      (filter #(not= #{:xor :and} (op-finder %))))]
    (remove #{"z00"} xor-outs)))

(defn xy-xor-not-zs
  "When a non-x and non-y wire are both inputs to an XOR, the output should
   be a z-wire. When the output isn't a z-wire, that wire must be swapped."
  [gates]
  (->> gates
       (op-match :include #{:xor})
       (input-wire-match :exclude #(str/starts-with? % "x"))
       (output-wire-match :exclude #(str/starts-with? % "z"))
       (map last)))

(defn or-inputs-not-and-outputs
  "All of the inputs to the OR gates ought to be outputs from the AND gates.
   Any wires that are inputs to the OR gates not coming from an AND must
   be swapped."
  [gates]
  ;; The output of the AND gate with x00 and y00 as inputs is a special
  ;; case from the half-adder circuit. 
  (let [x00-y00-out (->> gates
                         (op-match :include #{:and})
                         (input-wire-match :include #{"x00"})
                         first
                         last)
        and-outputs (->> gates
                         (op-match :include #{:and})
                         (map last)
                         set)
        or-inputs   (->> gates
                         (op-match :include #{:or})
                         (mapcat #(take 2 %))
                         set)]
    (->> (remove or-inputs and-outputs)
         (remove #{x00-y00-out}))))

(defn swapped-wires
  "Returns a sorted, comma-separated string of the names of the output wires
   that have been swapped in the adder circuit."
  [{:keys [gates]}]
  (->> (concat (zs-not-output-from-xor gates)
               (xy-xor-not-zs gates)
               (or-inputs-not-and-outputs gates)
               (wrong-xor-outs gates))
       (into #{})
       sort
       (str/join ",")))

;; Puzzle solutions
(defn part1
  "What decimal number does it output on the wires starting with z?"
  [input]
  (circuit-output input))

(defn part2
  "What do you get if you sort the names of the eight wires involved in a
   swap and then join those names with commas?"
  [input]
  (swapped-wires input))
