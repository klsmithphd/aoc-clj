(ns aoc-clj.2024.day08
  "Solution to https://adventofcode.com/2024/day/8"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.mapgrid :as mg]))

;; Input parsing
(defn parse
  [input]
  (let [{:keys [width height grid]} (mg/ascii->MapGrid2D identity input)]
    {:width width
     :height height
     :antennae (->> grid
                    (remove #(= \. (val %)))
                    (group-by val)
                    (u/fmap #(into #{} (map first %))))}))