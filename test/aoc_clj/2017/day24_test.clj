(ns aoc-clj.2017.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day24 :as d24]))

(def d24-s00-raw
  ["0/2"
   "2/2"
   "2/3"
   "3/4"
   "3/5"
   "0/1"
   "10/1"
   "9/10"])

(def d24-s00
  [[0 2]
   [2 2]
   [2 3]
   [3 4]
   [3 5]
   [0 1]
   [10 1]
   [9 10]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d24-s00 (d24/parse d24-s00-raw)))))

(deftest starters-test
  (testing "Finds the components we can start with"
    (is (= [[0 2] [0 1]] (d24/starters d24-s00)))))

(deftest other-end-test
  (testing "Given a component and one side, returns the value of the other side"
    (is (= 2 (d24/other-end [0 2] 0)))
    (is (= 10 (d24/other-end [10 1] 1)))))

(deftest edges-test
  (testing "Returns the compatible components for a given one"
    (is (= [[2 2] [2 3]] (d24/edges 2 (rest d24-s00))))))

(deftest longest-bridges-test
  (testing "Computes the longest bridges"
    (is (= #{[[0 2] [2 2] [2 3] [3 4]]
             [[0 2] [2 2] [2 3] [3 5]]
             [[0 2] [2 3] [3 4]]
             [[0 2] [2 3] [3 5]]
             [[0 1] [10 1] [9 10]]}
           (set (d24/longest-bridges d24-s00))))))

(deftest max-bridge-strength-test
  (testing "Computes the strongest possible bridge value"
    (is (= 31 (d24/max-bridge-strength d24-s00)))))

(deftest max-longest-bridge-strength-test
  (testing "Computes the strongest possible bridge value of the longest bridges"
    (is (= 19 (d24/max-longest-bridge-strength d24-s00)))))

(def day24-input (u/parse-puzzle-input d24/parse 2017 24))

(deftest part1-test
  (testing "Reproduces the answer for day24, part1"
    (is (= 1868 (d24/part1 day24-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day24, part1"
    (is (= 1841 (d24/part2 day24-input)))))