(ns aoc-clj.2017.day16
  "Solution to https://adventofcode.com/2017/day/16"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Constants
(def programs ["a" "b" "c" "d" "e" "f" "g" "h" "i" "j" "k" "l" "m" "n" "o" "p"])
(def repeat-count 1000000000)

;; Input parsing
(defn parse-inst
  [inst]
  (cond
    (str/starts-with? inst "s") [:spin (read-string (subs inst 1))]
    (str/starts-with? inst "x") [:exchange (map read-string (re-seq #"\d+" inst))]
    (str/starts-with? inst "p") [:partner (str/split (subs inst 1) #"\/")]))

(defn parse
  [input]
  (map parse-inst (str/split (first input) #",")))

;; Puzzle logic
(defn swap
  "Exchange the elements in the `state` vector in positions `pos-a` and `pos-b`"
  [state [pos-a pos-b]]
  (-> state
      (assoc pos-a (state pos-b))
      (assoc pos-b (state pos-a))))

(defn move
  "Return the updated `state` vector based on the dance move specified
   as a `cmd` and `args`"
  [state [cmd args]]
  (case cmd
    :spin (vec (u/rotate (- args) state))
    :exchange (swap state args)
    :partner (swap state (map #(u/index-of (u/equals? %) state) args))))

(defn dance
  "Apply all of the dance steps specified in `moves` to return a new state"
  [moves state]
  (reduce move state moves))

(defn n-dances
  "Simulates a large number of dances by finding the length of a repeated
   sequence among the dances and then only simulating the remainder after
   repeated loops."
  [moves state n]
  (let [[_ finish] (u/first-duplicates (iterate #(dance moves %) state))
        shift (mod n finish)]
    (->> (iterate #(dance moves %) state)
         (drop shift)
         first)))

;; Puzzle solutions
(defn part1
  "In what order are the programs standing after their dance?"
  [input]
  (apply str (dance input programs)))

(defn part2
  "In what order are the programs standing after their billion dances?"
  [input]
  (apply str (n-dances input programs repeat-count)))

