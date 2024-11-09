(ns aoc-clj.2018.day06
  "Solution to https://adventofcode.com/2018/day/6"
  (:require
   [aoc-clj.utils.grid :as grid]
   [aoc-clj.utils.vectors :as vec]))

;; Input parsing
(defn parse-line
  [line]
  (mapv read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn bounds
  "Returns the outer bounds of the provided coordinates as 
   [min-x, max-x], [min-y, max-y]"
  [coords]
  [[(apply min (map first coords))
    (apply max (map first coords))]
   [(apply min (map second coords))
    (apply max (map second coords))]])

(defn strictly-inside?
  "Returns true if the provided position is strictly contained
   within the provided bounds"
  [[[min-x max-x] [min-y max-y]] [x y]]
  (and (< min-x x max-x)
       (< min-y y max-y)))

(defn inner-coords
  "Returns the coordinates that are strictly inside the bounds
   set by the other coordinates"
  [coords]
  (let [bnds (bounds coords)]
    (filter #(strictly-inside? bnds %) coords)))

(defn closer?
  "Returns true if `pos` is strictly closer to `coord` than any
   other coord in `others`, via Manhattan distance"
  [others coord pos]
  (let [dist (vec/manhattan coord pos)]
    (every? #(> (vec/manhattan pos %) dist) others)))

(defn area
  "Returns the number of points that are closer to `coord` than
   any other point in `cooords`, or zero if that area would
   expand out into infinity."
  [coords coord]
  (let [not-inside? #(not (strictly-inside? (bounds coords) %))
        others (remove #{coord} coords)]
    (loop [front (grid/adj-coords-2d coord) in-area #{}]
      (if (or (empty? front) (some not-inside? in-area))
        (if (some not-inside? in-area)
          0
          (count in-area))
        (let [keepers (filter #(closer? others coord %) front)]
          (recur (remove in-area (set (mapcat grid/adj-coords-2d keepers)))
                 (into in-area keepers)))))))

(defn largest-area
  "Computes the largest area around any coordinate."
  [coords]
  (->> (inner-coords coords)
       (map #(area coords %))
       (apply max)))

;; Puzzle solutions
(defn part1
  "What is the size of the largest area that isn't infinite?"
  [input]
  (largest-area input))