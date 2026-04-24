(ns aoc-clj.year-2017.interface
  "Solutions to the Advent of Code 2017 puzzles.
   See https://adventofcode.com/2017")

(def solutions
  {1  {:parse 'aoc-clj.year-2017.day01/parse :part1 'aoc-clj.year-2017.day01/part1 :part2 'aoc-clj.year-2017.day01/part2}
   2  {:parse 'aoc-clj.year-2017.day02/parse :part1 'aoc-clj.year-2017.day02/part1 :part2 'aoc-clj.year-2017.day02/part2}
   3  {:parse 'aoc-clj.year-2017.day03/parse :part1 'aoc-clj.year-2017.day03/part1 :part2 'aoc-clj.year-2017.day03/part2}
   4  {:parse 'aoc-clj.year-2017.day04/parse :part1 'aoc-clj.year-2017.day04/part1 :part2 'aoc-clj.year-2017.day04/part2}
   5  {:parse 'aoc-clj.year-2017.day05/parse :part1 'aoc-clj.year-2017.day05/part1 :part2 'aoc-clj.year-2017.day05/part2}
   6  {:parse 'aoc-clj.year-2017.day06/parse :part1 'aoc-clj.year-2017.day06/part1 :part2 'aoc-clj.year-2017.day06/part2}
   7  {:parse 'aoc-clj.year-2017.day07/parse :part1 'aoc-clj.year-2017.day07/part1 :part2 'aoc-clj.year-2017.day07/part2}
   8  {:parse 'aoc-clj.year-2017.day08/parse :part1 'aoc-clj.year-2017.day08/part1 :part2 'aoc-clj.year-2017.day08/part2}
   9  {:parse 'aoc-clj.year-2017.day09/parse :part1 'aoc-clj.year-2017.day09/part1 :part2 'aoc-clj.year-2017.day09/part2}
   10 {:parse 'aoc-clj.year-2017.day10/parse :part1 'aoc-clj.year-2017.day10/part1 :part2 'aoc-clj.year-2017.day10/part2}
   11 {:parse 'aoc-clj.year-2017.day11/parse :part1 'aoc-clj.year-2017.day11/part1 :part2 'aoc-clj.year-2017.day11/part2}
   12 {:parse 'aoc-clj.year-2017.day12/parse :part1 'aoc-clj.year-2017.day12/part1 :part2 'aoc-clj.year-2017.day12/part2}
   13 {:parse 'aoc-clj.year-2017.day13/parse :part1 'aoc-clj.year-2017.day13/part1 :part2 'aoc-clj.year-2017.day13/part2}
   14 {:parse 'aoc-clj.year-2017.day14/parse :part1 'aoc-clj.year-2017.day14/part1 :part2 'aoc-clj.year-2017.day14/part2}
   15 {:parse 'aoc-clj.year-2017.day15/parse :part1 'aoc-clj.year-2017.day15/part1 :part2 'aoc-clj.year-2017.day15/part2}
   16 {:parse 'aoc-clj.year-2017.day16/parse :part1 'aoc-clj.year-2017.day16/part1 :part2 'aoc-clj.year-2017.day16/part2}
   17 {:parse 'aoc-clj.year-2017.day17/parse :part1 'aoc-clj.year-2017.day17/part1 :part2 'aoc-clj.year-2017.day17/part2}
   18 {:parse 'aoc-clj.year-2017.day18/parse :part1 'aoc-clj.year-2017.day18/part1 :part2 'aoc-clj.year-2017.day18/part2}
   19 {:parse 'aoc-clj.year-2017.day19/parse :part1 'aoc-clj.year-2017.day19/part1 :part2 'aoc-clj.year-2017.day19/part2}
   20 {:parse 'aoc-clj.year-2017.day20/parse :part1 'aoc-clj.year-2017.day20/part1 :part2 'aoc-clj.year-2017.day20/part2}
   21 {:parse 'aoc-clj.year-2017.day21/parse :part1 'aoc-clj.year-2017.day21/part1 :part2 'aoc-clj.year-2017.day21/part2}
   22 {:parse 'aoc-clj.year-2017.day22/parse :part1 'aoc-clj.year-2017.day22/part1 :part2 'aoc-clj.year-2017.day22/part2}
   23 {:parse 'aoc-clj.year-2017.day23/parse :part1 'aoc-clj.year-2017.day23/part1 :part2 'aoc-clj.year-2017.day23/part2}
   24 {:parse 'aoc-clj.year-2017.day24/parse :part1 'aoc-clj.year-2017.day24/part1 :part2 'aoc-clj.year-2017.day24/part2}
   25 {:parse 'aoc-clj.year-2017.day25/parse :part1 'aoc-clj.year-2017.day25/part1}})

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
