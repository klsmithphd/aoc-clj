(ns aoc-clj.2019.day21
  "Solution to https://adventofcode.com/2019/day/21"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

;; FIXME Add code or documentation for where the springcode came from
;; https://github.com/Ken-2scientists/aoc-clj/issues/22

(def parse u/firstv)

(def spring-codes-part1
  ["OR A T"
   "AND B T"
   "AND C T"
   "NOT T J"
   "AND D J"
   "WALK"])

(def spring-codes-part2
  ["OR A J"
   "AND B J"
   "AND C J"
   "NOT J J"
   "AND D J"
   "OR E T"
   "OR H T"
   "AND T J"
   "RUN"])

(defn part1
  [input]
  (->> (intcode/cmds->ascii spring-codes-part1)
       (intcode/intcode-exec input)
       intcode/last-out))

(defn part2
  [input]
  (->> (intcode/cmds->ascii spring-codes-part2)
       (intcode/intcode-exec input)
       intcode/last-out))