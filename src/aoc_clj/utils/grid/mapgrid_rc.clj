(ns aoc-clj.utils.grid.mapgrid-rc
  (:require [aoc-clj.utils.grid.core :as grid :refer [GridRC]]))

(defrecord MapGridRC [width height grid-map]
  GridRC
  (width    [_] width)
  (height   [_] height)
  (value    [_ pos] (get grid-map pos))
  (pos-seq  [this] (grid/positions this))
  (val-seq  [this] (map grid-map (grid/positions this)))
  (in-grid? [this pos] (grid/within-grid? this pos))
  (slice
    [_ dim idx]
    (let [coord (case dim :row first  :col second)
          w     (case dim :row width  :col 1)
          h     (case dim :row 1      :col height)]
      (->MapGridRC w h (into (sorted-map) (filter #(= idx (-> % key coord)) grid-map)))))
  (neighbors-4
    [_ pos]
    (let [locs (grid/adj-coords-2d pos)]
      (zipmap locs (map (partial get grid-map) locs))))
  (neighbors-8
    [_ pos]
    (let [locs (grid/adj-coords-2d pos :include-diagonals true)]
      (zipmap locs (map (partial get grid-map) locs)))))

(defn lists->MapGridRC
  "Index a 2D list-of-lists into a MapGridRC with [row col] coordinates.
   Row 0 is the first list; col 0 is the first element of each list."
  [values]
  (let [width  (count (first values))
        height (count values)
        coords (for [row (range height)
                     col (range width)]
                 [row col])]
    (->MapGridRC width height (zipmap coords (flatten values)))))

(defn ascii->MapGridRC
  "Convert an ASCII representation of a 2D grid into a MapGridRC.

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
    (->MapGridRC width height (zipmap coords syms))))
