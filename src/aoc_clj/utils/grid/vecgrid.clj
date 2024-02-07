(ns aoc-clj.utils.grid.vecgrid
  (:require [aoc-clj.utils.grid :as grid :refer [Grid2D]]))

(defrecord VecGrid2D [v]
  Grid2D
  (width [_] (count (first v)))
  (height [_] (count v))
  (value [_ [x y]] (get-in v [y x]))
  (pos-seq [this] (grid/positions this))
  (val-seq [_] (flatten v))
  (in-grid? [this pos] (grid/within-grid? this pos))
  (slice
    [_ dim idx]
    (->VecGrid2D
     (case dim
       :row (vector (get v idx))
       :col (mapv vector (map #(nth % idx) v)))))
  (neighbors-4
    [_ pos]
    (let [locs (grid/adj-coords-2d pos)]
      (zipmap locs (map #(get-in v (-> % reverse vec)) locs))))
  (neighbors-8
    [_ pos]
    (let [locs (grid/adj-coords-2d pos :include-diagonals true)]
      (zipmap locs (map #(get-in v (-> % reverse vec)) locs)))))

(defn ascii->VecGrid2D
  "Convert an ASCII represention of a 2D grid into
   a VecGrid2D.
   
   charmap is a map where the keys are ASCII chars and
   the values are expected to be symbols to use in
   your application. Ex.: (def codes {\\. :space \\# :wall})
   
   If `down` is specified, the first row represents zero and the
   y coordinate increases in the down direction, i.e the way
   screen coordinates typically work."
  [charmap lines & {:keys [down]}]
  (->VecGrid2D (mapv #(mapv charmap %)
                     (if down lines (reverse lines)))))
