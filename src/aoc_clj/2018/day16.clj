(ns aoc-clj.2018.day16
  "Solution to https://adventofcode.com/2018/day/16"
  (:require [aoc-clj.utils.core :as u]
            [clojure.set :as set]))

;; Input parsing
(defn parse-stanza
  [stanza]
  (map #(mapv read-string (re-seq #"\d+" %)) stanza))

(defn parse
  [input]
  (let [[samples _ [program]] (->> (u/split-at-blankline input)
                                   (map parse-stanza)
                                   (partition-by (comp empty? first)))]
    {:samples samples
     :program program}))

;; Puzzle logic
(defn dref
  "Dereference the argument from regs.
   If type is `:immediate`, return the arg as-is
   If type is `:register`, return the register value at `arg`"
  [type regs arg]
  (case type
    :immediate arg
    :register  (get regs arg 0)))

(defn- mutate-op
  "Base implementation of mutating register c through an operation
   on a and b, where b can either be an :immediate value or the
   :register value, depending upon `type`"
  [op type regs [a b c]]
  (assoc regs c (op (dref :register regs a) (dref type regs b))))

(defn- set-op
  "Base implementation for setting register c based on either
   the :immediate value of a, or the :register value, determined
   by `type`"
  [type regs [a _ c]]
  (assoc regs c (dref type regs a)))

(defn- comparison-op
  "Base implementation for setting register c to 1 or 0 depending
   upon whether the comparison operation between a and b is true,
   with a and b each uniquely interpreted as :immediate or :register
   based on `a-type` and `b-type`"
  [a-type b-type comp regs [a b c]]
  (assoc regs c (if (comp (dref a-type regs a) (dref b-type regs b)) 1 0)))

(def opcodes
  "The sixteen possible opcodes"
  {:addr (partial mutate-op + :register)
   :addi (partial mutate-op + :immediate)
   :mulr (partial mutate-op * :register)
   :muli (partial mutate-op * :immediate)
   :banr (partial mutate-op bit-and :register)
   :bani (partial mutate-op bit-and :immediate)
   :borr (partial mutate-op bit-or :register)
   :bori (partial mutate-op bit-or :immediate)
   :setr (partial set-op :register)
   :seti (partial set-op :immediate)
   :gtir (partial comparison-op :immediate :register >)
   :gtri (partial comparison-op :register :immediate >)
   :gtrr (partial comparison-op :register :register  >)
   :eqir (partial comparison-op :immediate :register =)
   :eqri (partial comparison-op :register :immediate =)
   :eqrr (partial comparison-op :register :register  =)})

(defn compatible-opcodes
  "For a given sample, returns the set of opcodes that could
   possibly produce the same effect"
  [[before [_ & args] after]]
  (->> opcodes
       (filter #(= after ((val %) before args)))
       keys
       set))

(defn three-or-more-opcode-count
  "Returns the count of samples for which three or more opcodes are
   possibly consistent with the sample."
  [samples]
  (->> samples
       (map compatible-opcodes)
       (filter #(>= (count %) 3))
       count))

(defn opcode-map
  "For a given sample, returns a map of the opcode-number and the 
   possible opcode names it's compatible with"
  [[_ [opcode & _] _ :as sample]]
  {opcode (compatible-opcodes sample)})

(defn possible-mapping
  "Given the samples provided, returns a map between opcode numbers
   and the sets of potentially compatible opcode names"
  [samples]
  (->> samples
       (map opcode-map)
       (apply merge-with set/intersection)))

(defn opcode-id-to-name-map
  "Returns the unique opcode-number-to-name map for the provided samples."
  [samples]
  ;; Starts with the possible mapping and then iteratively collapses
  ;; the possibilities based on removing the mappings for which there's
  ;; only one possible match.
  (loop [mapping (possible-mapping samples) solved {}]
    (if (= 16 (count solved))
      solved
      ;; done is the map of all id-to-name pairs that only have one name
      (let [done (->> mapping
                      (filter #(= 1 (count (val %))))
                      (u/fmap first))
            left-to-map (u/without-keys mapping (keys done))]
        ;; In the next recurrence, the possible mapping will strip out
        ;; the done values
        (recur (u/fmap #(set/difference % (set (vals done))) left-to-map)
               (merge solved done))))))

(defn op-step
  "Returns the new register state after applying an opcode instruction
   to the current `regs`, given the id-to-name mapping in `mapping`"
  [mapping regs [opcode & args]]
  ((opcodes (mapping opcode)) regs args))

(defn execute
  "Given the input of sample effects and the program to execute,
   first determine the correct id-to-name mapping for opcodes and
   then execute the sample program. Returns the final register state"
  [{:keys [samples program]}]
  (let [opcode-mapping (opcode-id-to-name-map samples)]
    (reduce (partial op-step opcode-mapping) [0 0 0 0] program)))

;; Puzzle solutions
(defn part1
  "Ignoring the opcode numbers, how many samples in your puzzle
   input behave like three or more opcodes?"
  [{:keys [samples]}]
  (three-or-more-opcode-count samples))

(defn part2
  "What value is contained in register 0 after executing the test program?"
  [input]
  (first (execute input)))