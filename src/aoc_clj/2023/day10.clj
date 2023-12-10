(ns aoc-clj.2023.day10
  (:require [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.utils.grid :as grid
             :refer [height width value neighbors-4]]))

(def charmap
  {\| :pipe-v
   \- :pipe-h
   \L :ell-ne
   \J :ell-nw
   \7 :ell-sw
   \F :ell-se
   \. :ground
   \S :start})

(defn parse
  [input]
  (vg/ascii->VecGrid2D charmap input :down true))

(defn start
  "Returns the grid position of the starting tile"
  [grid]
  (let [locs (for [y (range (height grid))
                   x (range (width grid))]
               [x y])]
    (first (drop-while #(not= :start (value grid %)) locs))))

(defn allowed-tiles
  [neighbors]
  (let [[south east north west] (seq neighbors)]
    (filter some?
            [(when (#{:pipe-v :ell-ne :ell-nw} (val south))
               (key south))
             (when (#{:pipe-h :ell-nw :ell-sw} (val east))
               (key east))
             (when (#{:pipe-v :ell-se :ell-sw} (val north))
               (key north))
             (when (#{:pipe-h :ell-ne :ell-se} (val west))
               (key west))])))

(defn heading
  [[px py] [cx cy]]
  (if (zero? (- cy py))
    (if (> cx px)
      :right
      :left)
    (if (> cy py)
      :down
      :up)))

(defn follow-loop
  [prev curr tile]
  (let [dir (heading prev curr)]
    ;; S = 0, E = 1, N = 2, W = 3
    (case dir
      :right (case tile
               :pipe-h 1
               :ell-nw 2
               :ell-sw 0)
      :left  (case tile
               :pipe-h 3
               :ell-ne 2
               :ell-se 0)
      :up    (case tile
               :pipe-v 2
               :ell-se 1
               :ell-sw 3)
      :down  (case tile
               :pipe-v 0
               :ell-ne 1
               :ell-nw 3))))


(defn loop-positions
  "Finds all of the positions along the loop"
  [grid]
  (let [init (start grid)]
    (loop [pos (first (allowed-tiles (neighbors-4 grid init)))
           visited [init]]
      (if (= pos init)
        visited
        (recur (key (nth (seq (neighbors-4 grid pos)) (follow-loop (last visited) pos (value grid pos))))
               (into visited [pos]))))))

(defn farthest-steps-from-start
  "Counts how many steps it takes to get to the farthest point from the start
   along the loop. That distance is just half of the loop length"
  [grid]
  (quot (count (loop-positions grid)) 2))

(defn day10-part1-soln
  "Find the single giant loop starting at S. How many steps along the loop does
   it take to get from the starting position to the point farthest from the 
   starting position?"
  [input]
  (farthest-steps-from-start input))

