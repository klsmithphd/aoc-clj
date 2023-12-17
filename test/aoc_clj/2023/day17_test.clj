(ns aoc-clj.2023.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.2023.day17 :as t]))

(def d17-s01-raw
  ["2413432311323"
   "3215453535623"
   "3255245654254"
   "3446585845452"
   "4546657867536"
   "1438598798454"
   "4457876987766"
   "3637877979653"
   "4654967986887"
   "4564679986453"
   "1224686865563"
   "2546548887735"
   "4322674655533"])

(def d17-s01
  (vg/->VecGrid2D
   [[2 4 1 3 4 3 2 3 1 1 3 2 3]
    [3 2 1 5 4 5 3 5 3 5 6 2 3]
    [3 2 5 5 2 4 5 6 5 4 2 5 4]
    [3 4 4 6 5 8 5 8 4 5 4 5 2]
    [4 5 4 6 6 5 7 8 6 7 5 3 6]
    [1 4 3 8 5 9 8 7 9 8 4 5 4]
    [4 4 5 7 8 7 6 9 8 7 7 6 6]
    [3 6 3 7 8 7 7 9 7 9 6 5 3]
    [4 6 5 4 9 6 7 9 8 6 8 8 7]
    [4 5 6 4 6 7 9 9 8 6 4 5 3]
    [1 2 2 4 6 8 6 8 6 5 5 6 3]
    [2 5 4 6 5 4 8 8 8 7 7 3 5]
    [4 3 2 2 6 7 4 6 5 5 5 3 3]]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d17-s01 (t/parse d17-s01-raw)))))

(deftest next-vertices-test
  (testing "Determines the next allowed vertices, and doesn't allow
            three straight-line segments in a row"
    (is (= [{:pos [1 0] :heading :R :count 1}
            {:pos [0 1] :heading :D :count 0}]
           (t/next-vertices d17-s01 {:pos [0 0] :heading :R :count 0})))
    (is (= [{:pos [2 0] :heading :R :count 2}
            {:pos [1 1] :heading :D :count 0}]
           (t/next-vertices d17-s01 {:pos [1 0] :heading :R :count 1})))
    (is (= [{:pos [0 2] :heading :D :count 1}
            {:pos [1 1] :heading :R :count 0}]
           (t/next-vertices d17-s01 {:pos [0 1] :heading :D :count 0})))
    (is (= [{:pos [2 1] :heading :D :count 0}]
           (t/next-vertices d17-s01 {:pos [2 0] :heading :R :count 2})))
    (is (= [{:pos [1 2] :heading :D :count 1}
            {:pos [0 1] :heading :L :count 0}
            {:pos [2 1] :heading :R :count 0}]
           (t/next-vertices d17-s01 {:pos [1 1] :heading :D :count 0})))
    (is (= [{:pos [1 2] :heading :R :count 0}]
           (t/next-vertices d17-s01 {:pos [0 2] :heading :D :count 2})))
    (is (= [{:pos [2 1] :heading :R :count 1}
            {:pos [1 0] :heading :U :count 0}
            {:pos [1 2] :heading :D :count 0}]
           (t/next-vertices d17-s01  {:pos [1 1] :heading :R :count 0})))))

(deftest min-heat-loss-test
  (testing "Computes the minimum possible heat loss"
    (is (= 102 (t/min-heat-loss d17-s01)))))

(def day17-input (u/parse-puzzle-input t/parse 2023 17))

(deftest day17-part1-soln
  (testing "Reproduces the answer for day17, part1"
    (is (= 843 (t/day17-part1-soln day17-input)))))