(ns aoc-clj.2019.day02
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

(def day02-input (u/firstv (u/puzzle-input "inputs/2019/day02-input.txt")))

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

(defn day02-part1-soln
  []
  (intcode-output day02-input [12 2]))

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

(defn day02-part2-soln
  []
  (let [[noun verb] (pair-produces-output day02-input 19690720)]
    (+ (* 100 noun) verb)))