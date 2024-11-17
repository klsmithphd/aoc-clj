(ns aoc-clj.2018.day16
  "Solution to https://adventofcode.com/2018/day/16"
  (:require [aoc-clj.utils.core :as u]))

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
  [type regs arg]
  (case type
    :immediate arg
    :register  (get regs arg 0)))

(defn mutate-op
  [op type regs [a b c]]
  (assoc regs c (op (dref :register regs a) (dref type regs b))))

(defn set-op
  [type regs [a _ c]]
  (assoc regs c (dref type regs a)))

(defn comparison-op
  [a-type b-type comp regs [a b c]]
  (let [a-val (dref a-type regs a)
        b-val (dref b-type regs b)]
    (assoc regs c (if (comp a-val b-val) 1 0))))

(def opcodes
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
  [[before [_ & args] after]]
  (->> opcodes
       (filter #(= after ((val %) before args)))
       keys
       set))

(defn three-or-more-opcode-count
  [samples]
  (->> samples
       (map compatible-opcodes)
       (filter #(>= (count %) 3))
       count))

;; Puzzle solutions
(defn part1
  [{:keys [samples]}]
  (three-or-more-opcode-count samples))