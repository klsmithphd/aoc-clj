(ns aoc-clj.2023.day16
  (:require [aoc-clj.utils.grid :as grid :refer [value width height]]
            [aoc-clj.utils.grid.vecgrid :as vg]))

(def part1-start [[0 0] :R])

(def charmap
  {\. :empty
   \| :spltv
   \- :splth
   \\ :mrrr1
   \/ :mrrr2})

(def heading-change
  "A verbose but simple way of representing the next headings among
   the various combinations of tile types and headings"
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
  "Returns the next cell from the position along the `heading` direction"
  [[x y] heading]
  (case heading
    :U [x (dec y)]
    :R [(inc x) y]
    :D [x (inc y)]
    :L [(dec x) y]))

(defn in-grid?
  "Returns true if the position is contained within the `grid`"
  [grid [x y]]
  (let [h (dec (height grid))
        w (dec (width grid))]
    (and (<= 0 x w) (<= 0 y h))))

(defn next-beams
  "For a given `grid` and a light beam represented by a position in the grid
   and a heading direction, computes the positions/headings of the next
   position/headings of light beams contained within the grid. May return
   0, 1, or 2 possible next beams in the set."
  [grid [pos heading]]
  (let [cell (value grid pos)
        new-headings (get-in heading-change [cell heading])]
    (->> (map #(vector (next-cell pos %) %) new-headings)
         (filter #(in-grid? grid (first %)))
         set)))

(defn energized
  "For a given `grid` and `start` position/heading for the light beam,
   follows all the unique light beam paths through the grid. Returns
   the positions of all of the energized tiles"
  [grid start]
  (loop [queue   [start]
         visited #{start}]
    (if (empty? queue)
      (set (map first visited))
      (let [next-cell  (first queue)]
        (recur (->> (next-beams grid next-cell)
                    (remove visited)
                    (into (rest queue)))
               (conj visited next-cell))))))

(defn energized-count
  "For a given `grid` and `start`ing position/heading for the light beam, 
   returns the number of energized tiles"
  [grid start]
  (count (energized grid start)))

(defn start-points
  "For a given `grid`, returns all possible starting positions and headings to
   enter the grid"
  [grid]
  (let [w (width grid)
        h (height grid)]
    (concat (for [x (range w)] [[x 0] :D])
            (for [x (range w)] [[x (dec h) :U]])
            (for [y (range h)] [[0 y] :R])
            (for [y (range h)] [[(dec w) y] :L]))))

(defn max-energization
  "Computes the energization for all possible starting positions and headings
   and returns the maximum energization"
  [grid]
  (apply max (map #(energized-count grid %) (start-points grid))))

(defn day16-part1-soln
  "With the beam starting in the top-left heading right, 
   how many tiles end up being energized?"
  [input]
  (energized-count input part1-start))

(defn day16-part2-soln
  "Find the initial beam configuration that energizes the largest number of 
   tiles; how many tiles are energized in that configuration?"
  [input]
  (max-energization input))