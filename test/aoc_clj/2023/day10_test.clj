(ns aoc-clj.2023.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.2023.day10 :as t]))

(def d10-s01-raw
  ["-L|F7"
   "7S-7|"
   "L|7||"
   "-L-J|"
   "L|-JF"])

(def d10-s01
  (vg/->VecGrid2D [[:pipe-h :ell-ne :pipe-v :ell-se :ell-sw]
                   [:ell-sw :start :pipe-h :ell-sw :pipe-v]
                   [:ell-ne :pipe-v :ell-sw :pipe-v :pipe-v]
                   [:pipe-h :ell-ne :pipe-h :ell-nw :pipe-v]
                   [:ell-ne :pipe-v :pipe-h :ell-nw :ell-se]]))

(def d10-s02-raw ["7-F7-"
                  ".FJ|7"
                  "SJLL7"
                  "|F--J"
                  "LJ.LJ"])
(def d10-s02 (t/parse d10-s02-raw))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d10-s01 (t/parse d10-s01-raw)))))

(deftest start-test
  (testing "Finds the starting position"
    (is (= [1 1] (t/start d10-s01)))))

(deftest loop-positions-test
  (testing "Follows the loop from the start returning positions along the way"
    (is (= [[1 1] [1 2] [1 3] [2 3] [3 3] [3 2] [3 1] [2 1]]
           (t/loop-positions d10-s01)))
    (is (= [[0 2] [0 3] [0 4] [1 4] [1 3] [2 3] [3 3] [4 3]
            [4 2] [3 2] [3 1] [3 0] [2 0] [2 1] [1 1] [1 2]]
           (t/loop-positions d10-s02)))))

(deftest farthest-steps-from-start-test
  (testing "Computes the number of steps to get farthest away from the start 
            along the loop"
    (is (= 4 (t/farthest-steps-from-start d10-s01)))
    (is (= 8 (t/farthest-steps-from-start d10-s02)))))

(def day10-input (u/parse-puzzle-input t/parse 2023 10))

(deftest day02-part1-soln
  (testing "Reproduces the answer for day10, part1"
    (is (= 7093 (t/day10-part1-soln day10-input)))))