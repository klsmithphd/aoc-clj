(ns aoc-clj.2024.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day14 :as d14]))

(def d14-s00-raw
  ["p=0,4 v=3,-3"
   "p=6,3 v=-1,-3"
   "p=10,3 v=-1,2"
   "p=2,0 v=2,-1"
   "p=0,0 v=1,3"
   "p=3,0 v=-2,-2"
   "p=7,6 v=-1,-3"
   "p=3,0 v=-1,-2"
   "p=9,3 v=2,3"
   "p=7,3 v=-1,2"
   "p=2,4 v=2,-3"
   "p=9,5 v=-3,-3"])

(def d14-s00
  [[[0,4] [3,-3]]
   [[6,3] [-1,-3]]
   [[10,3] [-1,2]]
   [[2,0] [2,-1]]
   [[0,0] [1,3]]
   [[3,0] [-2,-2]]
   [[7,6] [-1,-3]]
   [[3,0] [-1,-2]]
   [[9,3] [2,3]]
   [[7,3] [-1,2]]
   [[2,4] [2,-3]]
   [[9,5] [-3,-3]]])

(def sample-width 11)
(def sample-height 7)

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d14-s00 (d14/parse d14-s00-raw)))))

(deftest robots-at-t-test
  (testing "Determines the position of the robots at any time"
    (is (= [[3 5] [5 4] [9 0] [4 5] [1 6] [1 3] [6 0] [2 3] [0 2] [6 0] [4 5] [6 6]]
           (d14/robots-at-t sample-width sample-height 100 d14-s00)))))

(deftest quadrant-counts-test
  (testing "Counts the number of robots in each quadrant"
    (is (= {1 1
            2 3
            3 4
            4 1}
           (d14/quadrant-counts
            sample-width
            sample-height
            (d14/robots-at-t sample-width sample-height 100 d14-s00))))))

(deftest safety-factor-test
  (testing "Computes the safety factor at a given time"
    (is (= 12 (d14/safety-factor sample-width sample-height 100 d14-s00)))))

(def day14-input (u/parse-puzzle-input d14/parse 2024 14))

(deftest part1-test
  (testing "Reproduces the answer for day14, part1"
    (is (= 230172768 (d14/part1 day14-input)))))