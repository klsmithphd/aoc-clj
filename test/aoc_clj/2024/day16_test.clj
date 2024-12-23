(ns aoc-clj.2024.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day16 :as d16]))

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
    "#S#.............#"
    "#################"]))

(deftest shortest-path-score-test
  (testing "Computes the score for the shortest path to end"
    (is (= 7036  (d16/shortest-path-score d16-s00)))
    (is (= 11048 (d16/shortest-path-score d16-s01)))))

(deftest all-shortest-path-tile-count-test
  (testing "Counts the number of tiles used in any possible shortest path"
    (is (= 45 (d16/all-shortest-path-tile-count d16-s00)))
    (is (= 64 (d16/all-shortest-path-tile-count d16-s01)))))

(def day16-input (u/parse-puzzle-input d16/parse 2024 16))

(deftest part1-test
  (testing "Reproduces the answer for day16, part1"
    (is (= 111480 (d16/part1 day16-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day16, part2"
    (is (= 529 (d16/part2 day16-input)))))