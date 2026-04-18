(ns aoc-clj.2023.day10
  (:require [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.utils.grid :as grid
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
  (vg/ascii->VecGrid2D charmap input :down true))

(defn start
  "Returns the grid position of the starting tile"
  [grid]
  (let [locs (for [y (range (height grid))
                   x (range (width grid))]
               [x y])]
    (first (drop-while #(not= :start (value grid %)) locs))))

(defn allowed-tiles
  "For the set of neighboring tile locations and their values,
   determine which are allowable moves
   (based on which ends of the pipes are open to the direction we'd be
   heading in)"
  [grid pos]
  (filter some?
          [(let [south-pos (grid/neighbor-pos pos :s :y-down)]
             (when (#{:pipe-v :ell-ne :ell-nw} (grid/value grid south-pos))
               south-pos))
           (let [east-pos (grid/neighbor-pos pos :e :y-down)]
             (when (#{:pipe-h :ell-nw :ell-sw} (grid/value grid east-pos))
               east-pos))
           (let [north-pos (grid/neighbor-pos pos :n :y-down)]
             (when (#{:pipe-v :ell-se :ell-sw} (grid/value grid north-pos))
               north-pos))
           (let [west-pos (grid/neighbor-pos pos :w :y-down)]
             (when (#{:pipe-h :ell-ne :ell-se} (grid/value grid west-pos))
               west-pos))]))

(defn heading
  "Determines the direction we just came from by comparing the current
   and previous positions. Returns `:right`, `:left:`, `:up`, or `:down`"
  [[px py] [cx cy]]
  (if (zero? (- cy py))
    (if (> cx px)
      :right
      :left)
    (if (> cy py)
      :down
      :up)))

(defn next-direction
  "Returns the next direction along the loop based on the current pipe
   and the direction we entered from"
  [prev curr tile]
  (let [dir (heading prev curr)]
    (case dir
      :right (case tile
               :pipe-h :e
               :ell-nw :n
               :ell-sw :s)
      :left  (case tile
               :pipe-h :w
               :ell-ne :n
               :ell-se :s)
      :up    (case tile
               :pipe-v :n
               :ell-se :e
               :ell-sw :w)
      :down  (case tile
               :pipe-v :s
               :ell-ne :e
               :ell-nw :w))))


(defn loop-positions
  "Finds all of the positions along the loop"
  [grid]
  (let [init (start grid)]
    (loop [pos (first (allowed-tiles grid init))
           visited [init]]
      (if (= pos init)
        visited
        (recur (grid/neighbor-pos pos (next-direction (last visited) pos (value grid pos)) :y-down)
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

