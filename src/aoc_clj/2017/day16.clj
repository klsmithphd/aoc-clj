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
  [state [pos-a pos-b]]
  (-> state
      (assoc pos-a (state pos-b))
      (assoc pos-b (state pos-a))))

(defn move
  [state [cmd args]]
  (case cmd
    :spin (vec (u/rotate (- args) state))
    :exchange (swap state args)
    :partner (swap state (map #(u/index-of (u/equals? %) state) args))))

(defn dance
  [init-state moves]
  (reduce move init-state moves))

(defn- indexer
  [acc [idx x]]
  (if-let [old-idx (acc x)]
    (reduced [old-idx idx])
    (assoc acc x idx)))

(defn first-duplicates
  [coll]
  (let [indices (reduce indexer {} (map-indexed vector coll))]
    (if (map? indices)
      nil
      indices)))

(defn dance-at-large-n
  [init-state moves n]
  (let [[_ finish] (first-duplicates (iterate #(dance % moves) init-state))
        shift (mod n finish)]
    (->> (iterate #(dance % moves) init-state)
         (drop shift)
         first)))

;; Puzzle solutions
(defn part1
  [input]
  (apply str (dance programs input)))

(defn part2
  [input]
  (apply str (dance-at-large-n programs input repeat-count)))

