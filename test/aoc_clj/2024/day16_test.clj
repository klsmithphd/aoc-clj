(ns aoc-clj.2024.day16-test
  (:require [aoc-clj.2024.day16 :as d16]))

(def d16-s00
  (d16/parse
   ["###############"
    "#.......#....E#"
    "#.#.###.#.###.#"
    "#.....#.#...#.#"
    "#.###.#####.#.#"
    "#.#.#.......#.#"
    "#.#.#####.###.#"
    "#...........#.#"
    "###.#.#####.#.#"
    "#...#.....#.#.#"
    "#.#.#.###.#.#.#"
    "#.....#...#.#.#"
    "#.###.#.#.#.#.#"
    "#S..#.....#...#"
    "###############"]))

(def d16-s01
  (d16/parse
   ["#################"
    "#...#...#...#..E#"
    "#.#.#.#.#.#.#.#.#"
    "#.#.#.#...#...#.#"
    "#.#.#.#.###.#.#.#"
    "#...#.#.#.....#.#"
    "#.#.#.#.#.#####.#"
    "#.#...#.#.#.....#"
    "#.#.#####.#.###.#"
    "#.#.#.......#...#"
    "#.#.###.#####.###"
    "#.#.#...#.....#.#"
    "#.#.#.#####.###.#"
    "#.#.#.........#.#"
    "#.#.#.#########.#"
    "#S#.............#"]))
"#################"