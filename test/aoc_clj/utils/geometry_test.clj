(ns aoc-clj.utils.geometry-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.geometry :as geo]))

(def s01-vertices
  "Sample data representing a polygon depicted as follows
   ```
   #######
   #.....#
   ###...#
   ..#...#
   ..#...#
   ###.###
   #...#..
   ##..###
   .#....#
   .######
   ```
   Taken from https://adventofcode.com/2023/day/18"
  [[6 0] [6 5] [4 5] [4 7] [6 7] [6 9] [1 9]
   [1 7] [0 7] [0 5] [2 5] [2 2] [0 2] [0 0]])

(def s01-edges
  [[[6 0] [6 5]]
   [[6 5] [4 5]]
   [[4 5] [4 7]]
   [[4 7] [6 7]]
   [[6 7] [6 9]]
   [[6 9] [1 9]]
   [[1 9] [1 7]]
   [[1 7] [0 7]]
   [[0 7] [0 5]]
   [[0 5] [2 5]]
   [[2 5] [2 2]]
   [[2 2] [0 2]]
   [[0 2] [0 0]]
   [[0 0] [6 0]]])

(deftest vertices->edges-test
  (testing "Takes an ordered set of vertices and returns all the edges pairs"
    (is (= s01-edges (geo/vertices->edges s01-vertices)))))

(deftest perimeter-length-test
  (testing "Computes the length along the perimiter"
    (is (= 38 (int (geo/perimeter-length s01-edges))))))

(deftest polygon-area-test
  (testing "Computes the area of a polygon described by its vertices"
    (is (= 42 (int (geo/simple-polygon-area s01-edges))))))

(deftest interior-count-test
  (testing "Computes the number of internal points within a polygon"
    (is (= 24 (int (geo/interior-count s01-edges))))))