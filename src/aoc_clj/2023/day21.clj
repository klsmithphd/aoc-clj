(ns aoc-clj.2023.day21
  (:require [aoc-clj.utils.grid :refer [height width neighbors-4]]
            [aoc-clj.utils.grid.vecgrid :as vg]))

(def steps-part1 64)
(def steps-part2 26501365)

(def charmap {\S :start \. :plot \# :rock})

(defn parse
  [input]
  (vg/ascii->VecGrid2D charmap input :down true))

(defn start-pos
  "Find the position of the starting tile.
   
   The inputs for this puzzle are all squares (identical height and 
   width) with an odd number on a side. The starting position is always in 
   the dead center"
  [grid]
  (let [half-side (quot (height grid) 2)]
    [half-side half-side]))

(defn in-grid?
  "Returns true if the position is contained within the `grid`"
  [grid [x y]]
  (let [h (dec (height grid))
        w (dec (width grid))]
    (and (<= 0 x w) (<= 0 y h))))

(defn possible-locations
  "For the given position `loc`, return the possible non-rock neighbor
   locations in `grid`s"
  [grid loc]
  (->> (neighbors-4 grid loc)
       (filter #(in-grid? grid (key %)))
       (filter #(not= :rock (val %)))
       keys))

(defn reachable-plot-distances
  [grid start]
  (loop [visited {start 0}
         dist    1
         frontier [start]]
    (if (empty? frontier)
      visited
      (let [next-frontier (->> (mapcat #(possible-locations grid %) frontier)
                               set
                               (remove #(contains? visited %)))]
        (recur (into visited (map #(vector % dist) next-frontier))
               (inc dist)
               next-frontier)))))

(defn same-parity
  [n x]
  (if (odd? n) (odd? x) (even? x)))

(defn reachable-plots
  [plot-distances n]
  (keys (filter #(and (<= (val %) n) (same-parity n (val %))) plot-distances)))

(defn reachable-steps
  [grid n]
  (count (reachable-plots (reachable-plot-distances grid (start-pos grid)) n)))

(defn tile-repetitions
  [n tile-size]
  (-> (- n (quot tile-size 2))
      (quot tile-size)))

(defn inf-reachable-steps
  "The idea for this approach came from
   https://github.com/villuna/aoc23/wiki/A-Geometric-solution-to-advent-of-code-2023,-day-21"
  [grid n]
  (let [tile-size (height grid)]
    (if (< n (quot tile-size 2))
      (reachable-steps grid n)
      (let [tile-mults (tile-repetitions n tile-size)
            dists      (reachable-plot-distances grid (start-pos grid))
            even-tile-count (count (filter even? (vals dists)))
            odd-tile-count  (count (filter odd? (vals dists)))
            even-corner-count (count (filter #(and (even? %)
                                                   (> % 65)) (vals dists)))
            odd-corner-count  (count (filter #(and (odd? %)
                                                   (> % 65)) (vals dists)))]
        (+ (* (inc tile-mults) (inc tile-mults) odd-tile-count)
           (* tile-mults tile-mults even-tile-count)
           (* tile-mults even-corner-count)
           (- (* (inc tile-mults) odd-corner-count)))))))

(defn day21-part1-soln
  "Starting from the garden plot marked S on your map, how many garden plots 
   could the Elf reach in exactly 64 steps?"
  [input]
  (reachable-steps input steps-part1))

(defn day21-part2-soln
  "Starting from the garden plot marked S on your infinite map, how many garden
   plots could the Elf reach in exactly 26501365 steps?"
  [input]
  (inf-reachable-steps input steps-part2))