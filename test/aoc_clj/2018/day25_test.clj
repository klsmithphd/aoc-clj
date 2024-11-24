(ns aoc-clj.2018.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day25 :as d25]))

(def d25-s00-raw
  ["0,0,0,0"
   "3,0,0,0"
   "0,3,0,0"
   "0,0,3,0"
   "0,0,0,3"
   "0,0,0,6"
   "9,0,0,0"
   "12,0,0,0"])

(def d25-s00
  [[0 0 0 0]
   [3 0 0 0]
   [0 3 0 0]
   [0 0 3 0]
   [0 0 0 3]
   [0 0 0 6]
   [9 0 0 0]
   [12 0 0 0]])

(def d25-s01
  [[-1 2 2 0]
   [0 0 2 -2]
   [0 0 0 -2]
   [-1 2 0 0]
   [-2 -2 -2 2]
   [3 0 2 -1]
   [-1 3 2 2]
   [-1 0 -1 0]
   [0 2 1 -2]
   [3 0 0 0]])

(def d25-s02
  [[1 -1 0 1]
   [2 0 -1 0]
   [3 2 -1 0]
   [0 0 3 1]
   [0 0 -1 -1]
   [2 3 -2 0]
   [-2 2 0 0]
   [2 -2 0 -1]
   [1 -1 0 -1]
   [3 2 0 2]])

(def d25-s03
  [[1 -1 -1 -2]
   [-2 -2 0 1]
   [0 2 1 3]
   [-2 3 -2 1]
   [0 2 3 -2]
   [-1 -1 1 -2]
   [0 -2 -1 0]
   [-2 2 3 -1]
   [1 2 2 0]
   [-1 -2 0 -2]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d25-s00 (d25/parse d25-s00-raw)))))

(deftest merge-pass-test
  (testing "In a single pass, combines the constellations that are
            within range of each other"
    (is (= [#{[0 0 0 0]
              [3 0 0 0]
              [0 3 0 0]
              [0 0 3 0]
              [0 0 0 3]}
            #{[0 0 0 6]}
            #{[9 0 0 0]
              [12 0 0 0]}]
           (d25/merge-pass (d25/coll-as-sets d25-s00))))

    (is (= [#{[0 0 0 0]
              [3 0 0 0]
              [0 3 0 0]
              [0 0 3 0]
              [0 0 0 3]
              [0 0 0 6]}
            #{[9 0 0 0]
              [12 0 0 0]}]
           (d25/merge-pass
            [#{[0 0 0 0]
               [3 0 0 0]
               [0 3 0 0]
               [0 0 3 0]
               [0 0 0 3]}
             #{[0 0 0 6]}
             #{[9 0 0 0]
               [12 0 0 0]}])))))


(deftest constellation-count-test
  (testing "Counts the number of constellations among the points"
    (is (= 2 (d25/constellation-count d25-s00)))
    (is (= 4 (d25/constellation-count d25-s01)))
    (is (= 3 (d25/constellation-count d25-s02)))
    (is (= 8 (d25/constellation-count d25-s03)))))

(def day25-input (u/parse-puzzle-input d25/parse 2018 25))

(deftest part1-test
  (testing "Reproduces the answer for day25, part1"
    (is (= 386 (d25/part1 day25-input)))))