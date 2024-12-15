(ns aoc-clj.2024.day15
  "Solution to https://adventofcode.com/2024/day/15"
  (:require [clojure.string :as str]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(def grid-charmap
  {\# :wall
   \O :box
   \@ :robot
   \. :space})

(def move-charmap
  {\< :west
   \> :east
   \^ :north
   \v :south})

(defn parse-moves
  [moves-strs]
  (->> (str/join moves-strs)
       (map move-charmap)))

(defn parse
  [input]
  (let [[grid-str moves-str] (u/split-at-blankline input)]
    {:grid (mg/ascii->MapGrid2D grid-charmap grid-str)
     :moves (parse-moves moves-str)}))