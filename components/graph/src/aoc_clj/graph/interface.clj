(ns aoc-clj.graph.interface
  (:require [potemkin :refer [import-vars]]
            [aoc-clj.graph.core]))

(import-vars
 [aoc-clj.graph.core
  ;; protocol + methods
  Graph vertices vertex edges distance without-vertex
  ;; records + constructors
  ->MapGraph map->MapGraph ->SubMapGraph map->SubMapGraph
  ;; graph predicates / queries
  degree leaf? junction? entries-not-in-set
  ;; path helpers
  single-path single-path-2 all-paths path-retrace path-distance
  pruned summarize-path adjacencies
  ;; search
  reachable all-paths-dfs flood-fill
  ;; graph transforms
  adjacency-list reverse-graph
  ;; A* family
  a-star-allpath-update a-star-update
  all-shortest-paths shortest-path shortest-distance])
