(ns aoc-clj.2018.day06
  "Solution to https://adventofcode.com/2018/day/6"
  (:require
   [aoc-clj.utils.grid :as grid]
   [aoc-clj.utils.vectors :as vec]))

;; Constants
(def distance-limit 10000)

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

(defn distance-sum
  "Computes the sum of the Manhattan distance between `pos` and all
   the other `coords`"
  [coords pos]
  (reduce + (map #(vec/manhattan pos %) coords)))

(defn possible-points
  "Returns a seq of all the possible points in the space bounded by
   `coords`"
  [coords]
  (let [[[min-x max-x] [min-y max-y]] (bounds coords)]
    (for [y (range min-y (inc max-y))
          x (range min-x (inc max-x))]
      [x y])))

(defn region
  "The region size is defined to be the number of points whose
   total Manhattan distance from all the other `coords` is less than
   `limit`"
  [coords limit]
  (->> (possible-points coords)
       (map #(distance-sum coords %))
       (filter #(< % limit))
       count))

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

(defn part2
  "What is the size of the region containing all locations which have a total
   distance to all given coordinates of less than 10000?"
  [input]
  (region input distance-limit))