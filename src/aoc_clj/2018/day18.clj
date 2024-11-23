(ns aoc-clj.2018.day18
  "Solution to https://adventofcode.com/2018/day/18"
  (:require [aoc-clj.utils.grid.vecgrid :as vg]))


(def charmap
  {\. :o ;; open
   \# :l ;; lumberyard
   \| :t ;; tree
   })

(defn parse
  [input]
  (vg/ascii->VecGrid2D charmap input :down true))