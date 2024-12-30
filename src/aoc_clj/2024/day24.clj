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

;; (def x-wires (partial wires-starting-with "x"))
;; (def y-wires (partial wires-starting-with "y"))
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

(defn max-z-bit
  "Returns the number of the last z wire in the circuit"
  [{:keys [gates]}]
  (let [last-z (->> gates (map last) sort last)]
    (Integer/parseInt (subs last-z 1))))


(defn zs-not-output-from-xor
  "Any of the z wires should only be attached to the output of an XOR
   gate, so any z wires appearing as the output from an AND or an OR 
   gate must be swapped."
  [gates]
  (->> gates
       (filter #(str/starts-with? (nth % 3) "z"))
       (filter #(#{:and :or} (nth % 2)))
       (map last)))

(defn xy-xor-not-zs
  "When a non-x and non-y wire are both inputs to an XOR, the output should
   be a z-wire. When the output isn't a z-wire, that wire must be swapped."
  [gates]
  (->> gates
       (filter #(= :xor (nth % 2)))
       (remove #(str/starts-with? (nth % 0) "x"))
       (remove #(str/starts-with? (nth % 0) "y"))
       (remove #(str/starts-with? (nth % 3) "z"))
       (map last)))

(defn or-inputs-not-and-outputs
  "All of the inputs to the OR gates ought to be outputs from the AND gates.
   Any wires that are inputs to the OR gates not coming from an AND must
   be swapped."
  [gates]
  (let [and-outputs (->> gates
                         (filter #(= :and (nth % 2)))
                         (map last)
                         set)
        or-inputs   (->> gates
                         (filter #(= :or (nth % 2)))
                         (mapcat #(take 2 %)))]
    (remove and-outputs or-inputs)))


(defn swapped-wires
  [{:keys [gates]}]
  (->> (concat (zs-not-output-from-xor gates)
               (xy-xor-not-zs gates)
               (or-inputs-not-and-outputs gates))
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
