(ns aoc-clj.2023.day21
  (:require [aoc-clj.utils.grid :refer [height width value neighbors-4]]
            [aoc-clj.utils.grid.vecgrid :as vg]))

(def steps-part1 64)
;; (def steps-part2 26501365)

(def charmap {\S :start \. :plot \# :rock})

(defn parse
  [input]
  (vg/ascii->VecGrid2D charmap input :down true))

(defn start-pos
  "Find the position of the starting tile"
  [grid]
  (let [locs (for [y (range (height grid))
                   x (range (width grid))]
               [x y])]
    (first (filter #(= :start (value grid %)) locs))))

(defn possible-locations
  "For the given position `loc`, return the possible non-rock neighbor
   locations in `grid`s"
  [grid loc]
  (keys (filter #(not= :rock (val %)) (neighbors-4 grid loc))))

(defn all-possible-locations
  "From all of the given starting positions in `locs`, return all of the
   possible reachable (non-rock) locations in `grid`"
  [grid locs]
  (set (mapcat #(possible-locations grid %) locs)))

(defn reachable-steps
  "How many total plots are reachable in `grid` after exactly `n` steps"
  [grid n]
  (-> (iterate (partial all-possible-locations grid) [(start-pos grid)])
      (nth n)
      count))

(defn day21-part1-soln
  "Starting from the garden plot marked S on your map, how many garden plots 
   could the Elf reach in exactly 64 steps?"
  [input]
  (reachable-steps input steps-part1))