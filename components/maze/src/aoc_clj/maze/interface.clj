(ns aoc-clj.maze.interface
  (:require [potemkin :refer [import-vars]]
            [aoc-clj.maze.core]))

(import-vars
 [aoc-clj.maze.core
  ;; record constructors (the Maze class itself is not a var; consumers use ->Maze/map->Maze)
  ->Maze map->Maze
  ;; helpers
  one-step next-cells all-open Maze->Graph
  follow-left-wall next-move-attempt update-mazemap maze-step
  map-maze find-target spread-to-adjacent flood-fill])
