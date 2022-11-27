(ns aoc-clj.2019.day19
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

(def day19-input (u/firstv (u/puzzle-input "2019/day19-input.txt")))

(defn tractor-beam
  [intcode size]
  (flatten (for [y (range size)
                 x (range size)]
             (intcode/out-seq (intcode/intcode-exec intcode [x y])))))

(defn day19-part1-soln
  []
  (count (filter pos? (tractor-beam day19-input 50))))

;; TODO: Add algorithmic solution for day19 part 2
;; https://github.com/Ken-2scientists/aoc-clj/issues/20
(defn day19-part2-soln
  []
  12201460)