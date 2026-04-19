(ns aoc-clj.2023.day10
  (:require [aoc-clj.utils.grid.vecgrid-rc :as vg]
            [aoc-clj.utils.grid.core :as grid
             :refer [height width value neighbors-4]]
            [aoc-clj.utils.geometry :as geo]))

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
  (vg/ascii->VecGridRC charmap input))

(defn start
  "Returns the grid position of the starting tile"
  [grid]
  (let [locs (for [row (range (height grid))
                   col (range (width grid))]
               [row col])]
    (first (drop-while #(not= :start (value grid %)) locs))))

(defn allowed-tiles
  "For the set of neighboring tile locations and their values,
   return in the order S E N W, determine which are allowable moves
   (based on which ends of the pipes are open to the direction we'd be
   heading in)"
  [neighbors]
  (let [[north east south west] (seq neighbors)]
    (filter some?
            [(when (#{:pipe-v :ell-se :ell-sw} (val north))
               (key north))
             (when (#{:pipe-h :ell-nw :ell-sw} (val east))
               (key east))
             (when (#{:pipe-v :ell-ne :ell-nw} (val south))
               (key south))
             (when (#{:pipe-h :ell-ne :ell-se} (val west))
               (key west))])))

(defn heading
  "Determines the direction we just came from by comparing the current
   and previous positions. Returns `:right`, `:left:`, `:up`, or `:down`"
  [[pr pc] [cr cc]]
  (if (zero? (- cr pr))
    (if (> cc pc)
      :right
      :left)
    (if (> cr pr)
      :down
      :up)))

(defn next-location
  "Returns the next location along the loop based on the current pipe
   and the direction we entered from"
  [prev curr tile]
  (let [dir (heading prev curr)]
    ;; N = 0, E = 1, S = 2, W = 3
    (case dir
      :right (case tile
               :pipe-h 1
               :ell-nw 0
               :ell-sw 2)
      :left  (case tile
               :pipe-h 3
               :ell-ne 0
               :ell-se 2)
      :up    (case tile
               :pipe-v 0
               :ell-se 1
               :ell-sw 3)
      :down  (case tile
               :pipe-v 2
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
        (recur (key (nth (seq (neighbors-4 grid pos)) (next-location (last visited) pos (value grid pos))))
               (into visited [pos]))))))

(defn farthest-steps-from-start
  "Counts how many steps it takes to get to the farthest point from the start
   along the loop. That distance is just half of the loop length"
  [grid]
  (quot (count (loop-positions grid)) 2))

(defn interior-tiles
  "Computes how many tiles are enclosed by the pipe loop represented in the
   `grid`"
  [grid]
  (-> (loop-positions grid)
      geo/vertices->edges
      geo/interior-count
      int))

(defn part1
  "Find the single giant loop starting at S. How many steps along the loop does
   it take to get from the starting position to the point farthest from the 
   starting position?"
  [input]
  (farthest-steps-from-start input))

(defn part2
  "Figure out whether you have time to search for the nest by calculating the 
   area within the loop. How many tiles are enclosed by the loop?"
  [input]
  (interior-tiles input))

