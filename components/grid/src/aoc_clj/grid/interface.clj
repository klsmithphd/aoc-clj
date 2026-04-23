(ns aoc-clj.grid.interface
  (:require [potemkin :refer [import-vars]]
            [aoc-clj.grid.core]
            [aoc-clj.grid.mapgrid]
            [aoc-clj.grid.vecgrid]))

(import-vars
 [aoc-clj.grid.core
  ;; protocol + methods
  Grid2D width height in-grid? value pos-seq val-seq slice neighbors-4 neighbors-8
  ;; direction constants
  relative->cardinal cardinal-offsets extended-cardinal-offsets
  headings rel-bearings relative-bearing relative-heading
  ;; helpers
  turn forward find-nodes Grid2D->ascii within-grid? positions
  adj-coords-2d neighbor-data with-rel-bearings adj-coords-3d
  neighbors-2d neighbor-pos neighbor-value rel-neighbors
  mapgrid->vectors interpolated]
 [aoc-clj.grid.mapgrid
  ->MapGrid2D map->MapGrid2D lists->MapGrid2D ascii->MapGrid2D]
 [aoc-clj.grid.vecgrid
  ->VecGrid2D map->VecGrid2D
  summed-area-table area-sum square-area-sum ascii->VecGrid2D])
