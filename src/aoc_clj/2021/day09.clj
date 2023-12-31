(ns aoc-clj.2021.day09
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]))

(defn parse-line
  [line]
  (map (comp read-string str) line))

(def day09-input
  (->> (u/puzzle-input "inputs/2021/day09-input.txt")
       (map parse-line)
       mapgrid/lists->MapGrid2D
       :grid))

(defn low-point?
  [grid pos]
  (let [val (grid pos)
        coords (grid/adj-coords-2d pos)
        n-vals (->> (map grid coords)
                    (filter some?))]
    (every? #(< val %) n-vals)))

(defn risk-level
  [val]
  (inc val))

(defn risk-level-sum
  [input]
  (let [low-points (filter (partial low-point? input) (keys input))
        low-vals   (map input low-points)
        risks      (map risk-level low-vals)]
    (reduce + risks)))

(defn day09-part1-soln
  []
  (risk-level-sum day09-input))

(defn non-nine-neighbors
  [grid pos]
  (let [coords (grid/adj-coords-2d pos)]
    (->> (filter (comp some? grid) coords)
         (filter #(not= 9 (grid %))))))

(defn basin
  [grid low-point]
  (loop [basin #{low-point} next (non-nine-neighbors grid low-point)]
    (if (empty? next)
      basin
      (recur (into basin next)
             (->> (mapcat (partial non-nine-neighbors grid) next)
                  (filter (complement basin)))))))

(defn basin-sizes
  [grid]
  (let [low-points (filter (partial low-point? grid) (keys grid))
        basins     (map (partial basin grid) low-points)]
    (map count basins)))

(defn three-largest-basins-product
  [grid]
  (->> (basin-sizes grid)
       (sort >)
       (take 3)
       (reduce *)))

(defn day09-part2-soln
  []
  (three-largest-basins-product day09-input))