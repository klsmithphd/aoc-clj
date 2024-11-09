(ns aoc-clj.2018.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day06 :as d06]))

(def d06-s00-raw
  ["1, 1"
   "1, 6"
   "8, 3"
   "3, 4"
   "5, 5"
   "8, 9"])

(def d06-s00
  [[1 1]
   [1 6]
   [8 3]
   [3 4]
   [5 5]
   [8 9]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d06-s00 (d06/parse d06-s00-raw)))))

(deftest bounds-test
  (testing "Determines the outer bounds of the coordinates"
    (is (= [[1 8] [1 9]] (d06/bounds d06-s00)))))

(deftest inner-coords-test
  (testing "Returns the coordinates contained within the bounds set by all 
            the others"
    (is (= [[3 4] [5 5]] (d06/inner-coords d06-s00)))))

(deftest area-test
  (testing "Returns the number of points that are closer to this coord
            than any other coord"
    (is (= 9 (d06/area d06-s00 [3 4])))
    (is (= 17 (d06/area d06-s00 [5 5])))))

(deftest largest-area-test
  (testing "Finds the largest area around any coordinate"
    (is (= 17 (d06/largest-area d06-s00)))))

(def day06-input (u/parse-puzzle-input d06/parse 2018 6))

(deftest ^:slow part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= 4290 (d06/part1 day06-input)))))