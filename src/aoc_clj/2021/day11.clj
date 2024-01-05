(ns aoc-clj.2021.day11
  "Solution to https://adventofcode.com/2021/day/11"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]))

(defn parse-line
  [line]
  (map (comp read-string str) line))

(defn parse
  [input]
  (->> (map parse-line input)
       mapgrid/lists->MapGrid2D
       :grid))

(defn to-flash
  [grid flashed]
  (->> (filter #(> (val %) 9) grid)
       keys
       (filter (complement flashed))))

(defn flash
  [grid flashers flashed]
  (let [valid?     (set (keys grid))
        adjacents (->> (mapcat #(grid/adj-coords-2d % :include-diagonals true) flashers)
                       (filter valid?))
        next-grid (reduce #(update %1 %2 inc) grid adjacents)
        next-flashed (into flashed flashers)]
    [next-grid
     (to-flash next-grid next-flashed)
     next-flashed]))

(defn step
  [[total-flashed grid]]
  (loop [next-grid (u/fmap inc grid)
         flashers (to-flash next-grid #{})
         flashed #{}]
    (if (empty? flashers)
      [(+ total-flashed (count flashed))
       (reduce #(assoc %1 %2 0) next-grid flashed)]
      (let [[a b c] (flash next-grid flashers flashed)]
        (recur a b c)))))

(defn flashes-after-100-steps
  [grid]
  (first (nth (iterate step [0 grid]) 100)))

(defn steps-until-sync
  [grid]
  (->> (iterate step [0 grid])
       (map-indexed
        (fn [idx [_ new-grid]]
          [idx (every? zero? (vals new-grid))]))
       (filter second)
       ffirst))

(defn part1
  [input]
  (flashes-after-100-steps input))

(defn part2
  [input]
  (steps-until-sync input))