(ns aoc-clj.2019.day02
  "Solution to https://adventofcode.com/2019/day/2"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

(def parse u/firstv)

(defn override-intcode
  "Replace `intcode` with `noun` in position 1 and `verb` in position 2"
  [intcode noun verb]
  (-> intcode
      (assoc 1 noun)
      (assoc 2 verb)))

(defn intcode-output
  "Execute `intcode` program after overriding `noun` and `verb` in
   positions 1 and 2 respectively. Return the value in position 0."
  [intcode [noun verb]]
  (->> (override-intcode intcode noun verb)
       intcode/intcode-exec
       :intcode
       first))

(defn pair-produces-output
  "Find the noun/verb pair that will produce the `output` value for
   `intcode` program."
  [intcode output]
  (let [candidates (for [noun (range 100)
                         verb (range 100)]
                     [noun verb])]
    (first (drop-while
            #(not= output (intcode-output intcode %))
            candidates))))

(defn day02-part1-soln
  [input]
  (intcode-output input [12 2]))

(defn day02-part2-soln
  [input]
  (let [[noun verb] (pair-produces-output input 19690720)]
    (+ (* 100 noun) verb)))