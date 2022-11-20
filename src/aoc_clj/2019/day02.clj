(ns aoc-clj.2019.day02
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

(def day02-input (u/firstv (u/puzzle-input "2019/day02-input.txt")))

(defn override-intcode
  [intcode noun verb]
  (-> intcode
      (assoc 1 noun)
      (assoc 2 verb)))

(defn intcode-output
  [intcode [noun verb]]
  (->> (override-intcode intcode noun verb)
       intcode/intcode-exec
       first))

(defn day02-part1-soln
  []
  (intcode-output day02-input [12 2]))

(defn pair-produces-output
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