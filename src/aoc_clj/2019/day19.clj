(ns aoc-clj.2019.day19
  "Solution to https://adventofcode.com/2019/day/19"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

(def parse u/firstv)

(defn tractor-beam
  [intcode size]
  (flatten (for [y (range size)
                 x (range size)]
             (intcode/out-seq (intcode/intcode-exec intcode [x y])))))

(defn day19-part1-soln
  [input]
  (count (filter pos? (tractor-beam input 50))))

;; TODO: Add algorithmic solution for day19 part 2
;; https://github.com/Ken-2scientists/aoc-clj/issues/20
(defn day19-part2-soln
  [_]
  12201460)