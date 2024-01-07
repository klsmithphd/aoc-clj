(ns aoc-clj.2015.day07
  "Solution to https://adventofcode.com/2015/day/7"
  (:require [clojure.string :as str]))

;; Input parsing
(defn tokenize
  [s]
  (if (number? (read-string s))
    (int (read-string s))
    s))

(defn parse-unary
  [s]
  (let [[op a] (str/split s #" ")]
    {:op (keyword (str/lower-case op)) :args [(tokenize a)]}))

(defn parse-binary
  [s]
  (let [[a op b] (str/split s #" ")]
    {:op (keyword (str/lower-case op))
     :args [(tokenize a) (tokenize b)]}))

(defn parse-assign
  [s]
  {:op :assign :args (tokenize s)})

(defn parse-line
  [line]
  (let [[ops dest] (str/split line #" -> ")
        inst (cond
               (str/includes? ops "NOT")             (parse-unary ops)
               (some? (re-find #"AND|OR|SHIFT" ops)) (parse-binary ops)
               :else (parse-assign ops))]
    [(tokenize dest) inst]))

(defn parse
  [input]
  (into {} (map parse-line input)))

;; Puzzle logic
(def wire-val
  "Determines the signal provided to wire given the circuit by recursively
   evaluating its connections in the circuit
   
   This approach requires memoization or else nothing completes in a reasonable
   amount of time"
  (memoize
   (fn  [circuit wire]
     (let [{:keys [op args]} (circuit wire)]
       (if (= op :assign)
         (if (number? args) args (wire-val circuit args))
         (let [[a b] args]
           (case op
             :or     (bit-or (wire-val circuit a) (wire-val circuit b))
             :and    (if (number? a)
                       (bit-and a (wire-val circuit b))
                       (bit-and (wire-val circuit a) (wire-val circuit b)))
             :lshift (bit-shift-left  (wire-val circuit a) b)
             :rshift (bit-shift-right (wire-val circuit a) b)
             ;; We need to bit-and with 2^16-1 (65535) in order to trim out the
             ;; higher order bits present after we bit-not the value
             ;; This wouldn't be necessary if we could guarantee we're working
             ;; with 2-byte (16 bit) unsigned integers
             :not    (bit-and 65535 (bit-not (wire-val circuit a))))))))))

(defn override-wire-b
  "Override the signal provided to wire b with a new value"
  [circuit val]
  (assoc circuit "b" {:op :assign :args val}))

;; Puzzle solutions
(defn part1
  "Determine the signal provided to wire a"
  [input]
  (wire-val input "a"))

(defn part2
  "Take the signal from a in part 1, feed it to wire b and recompute a's signal"
  [input]
  (let [wire-a (wire-val input "a")
        newcircuit (override-wire-b input wire-a)]
    (wire-val newcircuit "a")))