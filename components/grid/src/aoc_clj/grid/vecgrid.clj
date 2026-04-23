(ns aoc-clj.grid.vecgrid
  (:require [aoc-clj.grid.core :as core :refer [Grid2D]]))

(defrecord VecGrid2D [v]
  Grid2D
  (width    [_] (count (first v)))
  (height   [_] (count v))
  (value    [_ [row col]] (get-in v [row col]))
  (pos-seq  [this] (core/positions this))
  (val-seq  [_] (flatten v))
  (in-grid? [this pos] (core/within-grid? this pos))
  (slice
    [_ dim idx]
    (->VecGrid2D
     (case dim
       :row (vector (get v idx))
       :col (mapv vector (map #(nth % idx) v)))))
  (neighbors-4
    [_ pos]
    (let [locs (core/adj-coords-2d pos)]
      (zipmap locs (map #(get-in v %) locs))))
  (neighbors-8
    [_ pos]
    (let [locs (core/adj-coords-2d pos :include-diagonals true)]
      (zipmap locs (map #(get-in v %) locs)))))

(defn summed-area-table
  "Returns a summed-area table for a given 2d vec-of-vecs.
   See https://en.wikipedia.org/wiki/Summed-area_table"
  [vecs]
  (let [ltor-sums (map #(reductions + %) vecs)]
    (vec (concat [(vec (first ltor-sums))]
                 (rest (reductions #(mapv + %1 %2) ltor-sums))))))

(defn area-sum
  "Given a summed-area table, the [row col] of the upper-left corner
   (inclusive), and the [row col] of the lower-right corner (inclusive),
   returns the sum of all values contained within that area."
  [sat [ul-row ul-col] [lr-row lr-col]]
  (- (+ (get-in sat [lr-row lr-col] 0)
        (get-in sat [(dec ul-row) (dec ul-col)] 0))
     (+ (get-in sat [(dec ul-row) lr-col] 0)
        (get-in sat [lr-row (dec ul-col)] 0))))

(defn square-area-sum
  "Returns the sum of all values in a square whose upper-left corner is at
   [ul-row ul-col] with side length size."
  [sat [ul-row ul-col size]]
  (area-sum sat [ul-row ul-col] [(+ ul-row (dec size)) (+ ul-col (dec size))]))

(defn ascii->VecGrid2D
  "Convert an ASCII representation of a 2D grid into a VecGrid2D.

   charmap is a map where the keys are ASCII chars and the values are the
   symbols used in your application. Ex.: (def codes {\\. :space \\# :wall})

   Row 0 is always the first line of input; no :down option is needed."
  [charmap lines]
  (->VecGrid2D (mapv #(mapv charmap %) lines)))
