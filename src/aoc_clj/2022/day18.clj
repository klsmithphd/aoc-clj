(ns aoc-clj.2022.day18
  "Solution to https://adventofcode.com/2022/day/18"
  (:require [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (set (map u/str->vec input)))

(def day18-input (parse (u/puzzle-input "2022/day18-input.txt")))

(defn surface-area
  "Count the number of sides of each cube that are not immediately 
   connected to another cube."
  [cubes]
  (->> cubes
       (mapcat grid/adj-coords-3d)
       (remove cubes)
       count))

(defn find-bounds
  [cubes]
  (let [min-max (juxt min max)]
    [(apply min-max (map #(nth % 0) cubes))
     (apply min-max (map #(nth % 1) cubes))
     (apply min-max (map #(nth % 2) cubes))]))

(defn in-bounds?
  [[[xmin xmax] [ymin ymax] [zmin zmax]] [x y z]]
  (and (<= (dec xmin) x (inc xmax))
       (<= (dec ymin) y (inc ymax))
       (<= (dec zmin) z (inc zmax))))

(defn flood-step-fn
  [bounds cubes]
  (fn [{:keys [stack seen exposed]}]
    (let [pos (peek stack)
          neighbors (->> (grid/adj-coords-3d pos)
                         (remove cubes)
                         (filter (partial in-bounds? bounds)))
          new-cells (remove seen neighbors)]
      {:stack   (into (pop stack) new-cells)
       :seen    (into seen neighbors)
       :exposed (into exposed new-cells)})))

(defn flood-fill
  [cubes]
  (let [bounds (find-bounds cubes)
        start  (mapv first bounds)
        step   (flood-step-fn bounds cubes)]
    (loop [state {:stack [start] :seen #{start} :exposed #{}}]
      (if (empty? (:stack state))
        (:exposed state)
        (recur (step state))))))

(defn outer-surface-area
  [cubes]
  (let [exposed (flood-fill cubes)]
    (->> cubes
         (mapcat grid/adj-coords-3d)
         (remove cubes)
         (filter exposed)
         count)))

(defn day18-part1-soln
  []
  (surface-area day18-input))

(defn day18-part2-soln
  []
  (outer-surface-area day18-input))