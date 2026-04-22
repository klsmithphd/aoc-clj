(ns aoc-clj.grid.mapgrid
  (:require [aoc-clj.grid.core :as core :refer [Grid2D]]))

(defrecord MapGrid2D [width height grid-map]
  Grid2D
  (width    [_] width)
  (height   [_] height)
  (value    [_ pos] (get grid-map pos))
  (pos-seq  [this] (core/positions this))
  (val-seq  [this] (map grid-map (core/positions this)))
  (in-grid? [this pos] (core/within-grid? this pos))
  (slice
    [_ dim idx]
    (let [coord (case dim :row first  :col second)
          w     (case dim :row width  :col 1)
          h     (case dim :row 1      :col height)]
      (->MapGrid2D w h (into (sorted-map) (filter #(= idx (-> % key coord)) grid-map)))))
  (neighbors-4
    [_ pos]
    (let [locs (core/adj-coords-2d pos)]
      (zipmap locs (map (partial get grid-map) locs))))
  (neighbors-8
    [_ pos]
    (let [locs (core/adj-coords-2d pos :include-diagonals true)]
      (zipmap locs (map (partial get grid-map) locs)))))

(defn lists->MapGrid2D
  "Index a 2D list-of-lists into a MapGrid2D with [row col] coordinates.
   Row 0 is the first list; col 0 is the first element of each list."
  [values]
  (let [width  (count (first values))
        height (count values)
        coords (for [row (range height)
                     col (range width)]
                 [row col])]
    (->MapGrid2D width height (zipmap coords (flatten values)))))

(defn ascii->MapGrid2D
  "Convert an ASCII representation of a 2D grid into a MapGrid2D.

   charmap is a map where the keys are ASCII chars and the values are the
   symbols used in your application. Ex.: (def codes {\\. :space \\# :wall})

   Row 0 is always the first line of input; no :down option is needed."
  [charmap lines]
  (let [height (count lines)
        width  (count (first lines))
        coords (for [row (range height)
                     col (range width)]
                 [row col])
        syms   (mapcat #(map charmap %) lines)]
    (->MapGrid2D width height (zipmap coords syms))))
