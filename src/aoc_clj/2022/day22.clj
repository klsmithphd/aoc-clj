(ns aoc-clj.2022.day22
  "Solution to https://adventofcode.com/2022/day/22"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]))

(def charmap {\  nil \. :open \# :wall})

;; TODO: This isn't yet correct given that the ascii->MapGrid2D implementation
;; assumes a rectangular grid (of fixed width and height)
(defn parse-map
  [s]
  (mapgrid/ascii->MapGrid2D charmap s))

(defn parse-path
  [s]
  (map read-string (re-seq #"\d+|[LR]" s)))

(defn parse
  [input]
  (let [[a b] (u/split-at-blankline input)]
    {:themap  (parse-map a)
     :path    (parse-path (first b))}))

(def d22-s01
  (parse
   ["        ...#"
    "        .#.."
    "        #..."
    "        ...."
    "...#.......#"
    "........#..."
    "..#....#...."
    "..........#."
    "        ...#...."
    "        .....#.."
    "        .#......"
    "        ......#."
    ""
    "10R5L5R10L4R5L5"]))

(def day22-input (parse (u/puzzle-input "2022/day22-input.txt")))