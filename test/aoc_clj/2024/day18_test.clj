(ns aoc-clj.2024.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day18 :as d18]))

(def d18-s00-raw
  ["5,4"
   "4,2"
   "4,5"
   "3,0"
   "2,1"
   "6,3"
   "2,4"
   "1,5"
   "0,6"
   "3,3"
   "2,6"
   "5,1"
   "1,2"
   "5,5"
   "2,5"
   "6,5"
   "1,4"
   "0,4"
   "6,4"
   "1,1"
   "6,1"
   "1,0"
   "0,5"
   "1,6"
   "2,0"])

(def d18-s00
  [[5 4]
   [4 2]
   [4 5]
   [3 0]
   [2 1]
   [6 3]
   [2 4]
   [1 5]
   [0 6]
   [3 3]
   [2 6]
   [5 1]
   [1 2]
   [5 5]
   [2 5]
   [6 5]
   [1 4]
   [0 4]
   [6 4]
   [1 1]
   [6 1]
   [1 0]
   [0 5]
   [1 6]
   [2 0]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d18-s00 (d18/parse d18-s00-raw)))))

(deftest shortest-path-test
  (testing "Finds the length of the shortest path"
    (is (= 22 (d18/shortest-path 7 12 d18-s00)))
    (is (= 24 (d18/shortest-path 7 20 d18-s00)))
    ;; Adding the 21st byte leaves no path, so the path
    ;; length is -1
    (is (= -1 (d18/shortest-path 7 21 d18-s00)))))

(deftest first-blocking-byte-test
  (testing "Returns the first byte that completely blocks the path"
    (is (= "6,1" (d18/first-blocking-byte 7 12 d18-s00)))))

(def day18-input (u/parse-puzzle-input d18/parse 2024 18))

(deftest part1-test
  (testing "Reproduces the answer for day18, part1"
    (is (= 340 (d18/part1 day18-input)))))

(deftest ^:slow part2-test
  (testing "Reproduces the answer for day18, part2"
    (is (= "34,32" (d18/part2 day18-input)))))