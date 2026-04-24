(ns aoc-clj.year-2018.interface
  "Solutions to the Advent of Code 2018 puzzles.
   See https://adventofcode.com/2018")

(def solutions
  {1  {:parse 'aoc-clj.year-2018.day01/parse :part1 'aoc-clj.year-2018.day01/part1 :part2 'aoc-clj.year-2018.day01/part2}
   2  {:parse 'aoc-clj.year-2018.day02/parse :part1 'aoc-clj.year-2018.day02/part1 :part2 'aoc-clj.year-2018.day02/part2}
   3  {:parse 'aoc-clj.year-2018.day03/parse :part1 'aoc-clj.year-2018.day03/part1 :part2 'aoc-clj.year-2018.day03/part2}
   4  {:parse 'aoc-clj.year-2018.day04/parse :part1 'aoc-clj.year-2018.day04/part1 :part2 'aoc-clj.year-2018.day04/part2}
   5  {:parse 'aoc-clj.year-2018.day05/parse :part1 'aoc-clj.year-2018.day05/part1 :part2 'aoc-clj.year-2018.day05/part2}
   6  {:parse 'aoc-clj.year-2018.day06/parse :part1 'aoc-clj.year-2018.day06/part1 :part2 'aoc-clj.year-2018.day06/part2}
   7  {:parse 'aoc-clj.year-2018.day07/parse :part1 'aoc-clj.year-2018.day07/part1 :part2 'aoc-clj.year-2018.day07/part2}
   8  {:parse 'aoc-clj.year-2018.day08/parse :part1 'aoc-clj.year-2018.day08/part1 :part2 'aoc-clj.year-2018.day08/part2}
   9  {:parse 'aoc-clj.year-2018.day09/parse :part1 'aoc-clj.year-2018.day09/part1 :part2 'aoc-clj.year-2018.day09/part2}
   10 {:parse 'aoc-clj.year-2018.day10/parse :part1 'aoc-clj.year-2018.day10/part1 :part2 'aoc-clj.year-2018.day10/part2}
   11 {:parse 'aoc-clj.year-2018.day11/parse :part1 'aoc-clj.year-2018.day11/part1 :part2 'aoc-clj.year-2018.day11/part2}
   12 {:parse 'aoc-clj.year-2018.day12/parse :part1 'aoc-clj.year-2018.day12/part1 :part2 'aoc-clj.year-2018.day12/part2}
   13 {:parse 'aoc-clj.year-2018.day13/parse :part1 'aoc-clj.year-2018.day13/part1 :part2 'aoc-clj.year-2018.day13/part2}
   14 {:parse 'aoc-clj.year-2018.day14/parse :part1 'aoc-clj.year-2018.day14/part1 :part2 'aoc-clj.year-2018.day14/part2}
   16 {:parse 'aoc-clj.year-2018.day16/parse :part1 'aoc-clj.year-2018.day16/part1 :part2 'aoc-clj.year-2018.day16/part2}
   18 {:parse 'aoc-clj.year-2018.day18/parse :part1 'aoc-clj.year-2018.day18/part1 :part2 'aoc-clj.year-2018.day18/part2}
   19 {:parse 'aoc-clj.year-2018.day19/parse :part1 'aoc-clj.year-2018.day19/part1 :part2 'aoc-clj.year-2018.day19/part2}
   21 {:parse 'aoc-clj.year-2018.day21/parse :part1 'aoc-clj.year-2018.day21/part1 :part2 'aoc-clj.year-2018.day21/part2}
   25 {:parse 'aoc-clj.year-2018.day25/parse :part1 'aoc-clj.year-2018.day25/part1}})

(defn- resolve-fns
  [fn-map]
  (when fn-map
    (update-vals fn-map requiring-resolve)))

(defn solution-fns
  "Solution fns for the given day of this year.
   Returns {:parse _ :part1 _ :part2 _} with each Var resolved on first call
   (days are loaded lazily), or nil if `day` is out of range."
  [day]
  (resolve-fns (get solutions day)))
