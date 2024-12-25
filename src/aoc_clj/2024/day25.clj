(ns aoc-clj.2024.day25
  "Solution to https://adventofcode.com/2024/day/25"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.vecgrid :as vg]))

;; Input parsing
(def charmap {\# 1 \. 0})

(defn key-lock-parse
  [lines]
  [(case (ffirst lines)
     0 :lock
     1 :key)
   (mapv #(reduce + -1 %) lines)])

(defn parse
  [input]
  (->> (u/split-at-blankline input)
       (map #(:v (vg/ascii->VecGrid2D charmap %)))
       (map u/transpose)
       (map key-lock-parse)))