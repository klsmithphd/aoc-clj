(ns aoc-clj.year-2022.interface
  "Solutions to the Advent of Code 2022 puzzles.
   See https://adventofcode.com/2022"
  (:require [aoc-clj.year-2022.day01 :as d01]
            [aoc-clj.year-2022.day02 :as d02]
            [aoc-clj.year-2022.day03 :as d03]
            [aoc-clj.year-2022.day04 :as d04]
            [aoc-clj.year-2022.day05 :as d05]
            [aoc-clj.year-2022.day06 :as d06]
            [aoc-clj.year-2022.day07 :as d07]
            [aoc-clj.year-2022.day08 :as d08]
            [aoc-clj.year-2022.day09 :as d09]
            [aoc-clj.year-2022.day10 :as d10]
            [aoc-clj.year-2022.day11 :as d11]
            [aoc-clj.year-2022.day12 :as d12]
            [aoc-clj.year-2022.day13 :as d13]
            [aoc-clj.year-2022.day14 :as d14]
            [aoc-clj.year-2022.day15 :as d15]
            [aoc-clj.year-2022.day16 :as d16]
            [aoc-clj.year-2022.day17 :as d17]
            [aoc-clj.year-2022.day18 :as d18]
            [aoc-clj.year-2022.day19 :as d19]
            [aoc-clj.year-2022.day20 :as d20]
            [aoc-clj.year-2022.day21 :as d21]
            [aoc-clj.year-2022.day22 :as d22]
            [aoc-clj.year-2022.day23 :as d23]
            [aoc-clj.year-2022.day24 :as d24]
            [aoc-clj.year-2022.day25 :as d25]))

(def solutions
  {1  {:parse #'d01/parse :part1 #'d01/part1 :part2 #'d01/part2}
   2  {:parse #'d02/parse :part1 #'d02/part1 :part2 #'d02/part2}
   3  {:parse #'d03/parse :part1 #'d03/part1 :part2 #'d03/part2}
   4  {:parse #'d04/parse :part1 #'d04/part1 :part2 #'d04/part2}
   5  {:parse #'d05/parse :part1 #'d05/part1 :part2 #'d05/part2}
   6  {:parse #'d06/parse :part1 #'d06/part1 :part2 #'d06/part2}
   7  {:parse #'d07/parse :part1 #'d07/part1 :part2 #'d07/part2}
   8  {:parse #'d08/parse :part1 #'d08/part1 :part2 #'d08/part2}
   9  {:parse #'d09/parse :part1 #'d09/part1 :part2 #'d09/part2}
   10 {:parse #'d10/parse :part1 #'d10/part1 :part2 #'d10/part2}
   11 {:parse #'d11/parse :part1 #'d11/part1 :part2 #'d11/part2}
   12 {:parse #'d12/parse :part1 #'d12/part1 :part2 #'d12/part2}
   13 {:parse #'d13/parse :part1 #'d13/part1 :part2 #'d13/part2}
   14 {:parse #'d14/parse :part1 #'d14/part1 :part2 #'d14/part2}
   15 {:parse #'d15/parse :part1 #'d15/part1 :part2 #'d15/part2}
   16 {:parse #'d16/parse :part1 #'d16/part1 :part2 #'d16/part2}
   17 {:parse #'d17/parse :part1 #'d17/part1 :part2 #'d17/part2}
   18 {:parse #'d18/parse :part1 #'d18/part1 :part2 #'d18/part2}
   19 {:parse #'d19/parse :part1 #'d19/part1 :part2 #'d19/part2}
   20 {:parse #'d20/parse :part1 #'d20/part1 :part2 #'d20/part2}
   21 {:parse #'d21/parse :part1 #'d21/part1 :part2 #'d21/part2}
   22 {:parse #'d22/parse :part1 #'d22/part1 :part2 #'d22/part2}
   23 {:parse #'d23/parse :part1 #'d23/part1 :part2 #'d23/part2}
   24 {:parse #'d24/parse :part1 #'d24/part1 :part2 #'d24/part2}
   25 {:parse #'d25/parse :part1 #'d25/part1}})

(defn solution-fns
  "Solution fns for the given day of this year.
   Returns {:parse _ :part1 _ :part2 _}, or nil if day is out of range."
  [day]
  (get solutions day))
