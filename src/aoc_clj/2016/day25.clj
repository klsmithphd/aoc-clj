(ns aoc-clj.2016.day25
  "Solution to https://adventofcode.com/2016/day/25"
  (:require [aoc-clj.utils.assembunny :as asmb]))

;; Input parsing
(def parse (comp asmb/parse butlast))

;; Puzzle logic
(defn is-square-wave?
  [s]
  (let [len (count s)]
    (= s (take len (cycle [0 1])))))

(defn valid-input?
  [program input]
  (let [{:keys [a out]} (asmb/execute (assoc asmb/init-state :a input) program)]
    (and (zero? a) (is-square-wave? out))))

(defn first-valid-input
  [prgrm]
  (->> (range)
       (filter #(valid-input? prgrm %))
       first))

;; Puzzle solutions
(defn part1
  [input]
  (first-valid-input input))
