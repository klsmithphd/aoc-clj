(ns aoc-clj.year-2025.interface
  "Solutions to the Advent of Code 2025 puzzles.
   See https://adventofcode.com/2025")

(def solutions
  {1  {:parse 'aoc-clj.year-2025.day01/parse :part1 'aoc-clj.year-2025.day01/part1 :part2 'aoc-clj.year-2025.day01/part2}
   2  {:parse 'aoc-clj.year-2025.day02/parse :part1 'aoc-clj.year-2025.day02/part1 :part2 'aoc-clj.year-2025.day02/part2}
   3  {:parse 'aoc-clj.year-2025.day03/parse :part1 'aoc-clj.year-2025.day03/part1 :part2 'aoc-clj.year-2025.day03/part2}
   4  {:parse 'aoc-clj.year-2025.day04/parse :part1 'aoc-clj.year-2025.day04/part1 :part2 'aoc-clj.year-2025.day04/part2}
   5  {:parse 'aoc-clj.year-2025.day05/parse :part1 'aoc-clj.year-2025.day05/part1 :part2 'aoc-clj.year-2025.day05/part2}
   6  {:parse 'aoc-clj.year-2025.day06/parse :part1 'aoc-clj.year-2025.day06/part1 :part2 'aoc-clj.year-2025.day06/part2}
   7  {:parse 'aoc-clj.year-2025.day07/parse :part1 'aoc-clj.year-2025.day07/part1 :part2 'aoc-clj.year-2025.day07/part2}
   8  {:parse 'aoc-clj.year-2025.day08/parse :part1 'aoc-clj.year-2025.day08/part1 :part2 'aoc-clj.year-2025.day08/part2}
   9  {:parse 'aoc-clj.year-2025.day09/parse :part1 'aoc-clj.year-2025.day09/part1 :part2 'aoc-clj.year-2025.day09/part2}
   10 {:parse 'aoc-clj.year-2025.day10/parse :part1 'aoc-clj.year-2025.day10/part1 :part2 'aoc-clj.year-2025.day10/part2}
   11 {:parse 'aoc-clj.year-2025.day11/parse :part1 'aoc-clj.year-2025.day11/part1 :part2 'aoc-clj.year-2025.day11/part2}
   12 {:parse 'aoc-clj.year-2025.day12/parse :part1 'aoc-clj.year-2025.day12/part1 :part2 'aoc-clj.year-2025.day12/part2}})

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
