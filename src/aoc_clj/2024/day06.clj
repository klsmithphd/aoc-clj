(ns aoc-clj.2024.day06
  "Solution to https://adventofcode.com/2024/day/6"
  (:require [aoc-clj.utils.grid :as grid :refer [value]]
            [aoc-clj.utils.grid.mapgrid :as mg]))

;; Input parsing
(def charmap {\. nil \# :wall \^ :guard})

(defn parse
  [input]
  (mg/ascii->MapGrid2D charmap input))

;; Puzzle logic
(defn guard-start
  [{:keys [grid]}]
  {:heading :n
   :pos (ffirst (filter #(= :guard (val %)) grid))})

(defn next-move
  [grid guard]
  (let [nxt (grid/forward guard 1)]
    (if (= :wall (value grid (:pos nxt)))
      (next-move grid (grid/turn guard :right))
      nxt)))

(defn guard-path
  [grid]
  (->> (guard-start grid)
       (iterate #(next-move grid %))
       (take-while #(grid/in-grid? grid (:pos %)))))

;; Puzzle solutions
(defn part1
  [input]
  (count (set (guard-path input))))