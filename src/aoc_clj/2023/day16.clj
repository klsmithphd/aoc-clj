(ns aoc-clj.2023.day16
  (:require [aoc-clj.utils.grid :as grid :refer [value width height]]
            [aoc-clj.utils.grid.vecgrid :as vg]))

(def charmap
  {\. :empty
   \| :spltv
   \- :splth
   \\ :mrrr1
   \/ :mrrr2})

(def heading-change
  {:empty {:R [:R]
           :L [:L]
           :U [:U]
           :D [:D]}
   :mrrr1 {:R [:D]
           :L [:U]
           :D [:R]
           :U [:L]}
   :mrrr2 {:R [:U]
           :L [:D]
           :D [:L]
           :U [:R]}
   :spltv {:R [:U :D]
           :L [:U :D]
           :U [:U]
           :D [:D]}
   :splth {:R [:R]
           :L [:L]
           :U [:L :R]
           :D [:L :R]}})

(defn parse
  [input]
  (vg/ascii->VecGrid2D charmap input :down true))

(defn next-cell
  [[x y] heading]
  (case heading
    :U [x (dec y)]
    :R [(inc x) y]
    :D [x (inc y)]
    :L [(dec x) y]))

(defn in-grid?
  [grid [x y]]
  (let [h (dec (height grid))
        w (dec (width grid))]
    (and (<= 0 x w) (<= 0 y h))))

(defn next-beams
  [grid [pos heading]]
  (let [cell (value grid pos)
        new-headings (get-in heading-change [cell heading])]
    (->> (map #(vector (next-cell pos %) %) new-headings)
         (filter #(in-grid? grid (first %)))
         set)))

(defn energized
  [grid]
  (loop [queue   [[[0 0] :R]]
         visited #{[[0 0] :R]}]
    (if (empty? queue)
      (set (map first visited))
      (let [next-cell  (first queue)]
        (recur (->> (next-beams grid next-cell)
                    (remove visited)
                    (into (rest queue)))
               (conj visited next-cell))))))

(defn energized-count
  [grid]
  (count (energized grid)))

(defn day16-part1-soln
  [input]
  (energized-count input))