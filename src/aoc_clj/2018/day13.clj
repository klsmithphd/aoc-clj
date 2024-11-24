(ns aoc-clj.2018.day13
  "Solution to https://adventofcode.com/2018/day/13"
  (:require [aoc-clj.utils.grid.mapgrid :as mg]))

;; Input parsing
(def charmap
  {\\ :curve-135
   \/ :curve-45
   \| :v
   \- :h
   \+ :intersection
   \< :cart-l
   \> :cart-r
   \v :cart-d
   \^ :cart-u
   \  nil})

(defn parse
  [input]
  (let [{:keys [grid width height]} (mg/ascii->MapGrid2D charmap input :down true)]
    (mg/->MapGrid2D width height (into {} (remove #(nil? (val %)) grid)))))