(ns aoc-clj.2017.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day06 :as d06]))

(def d06-s00-raw
  ["0    2    7    0"])

(def d06-s00
  [0 2 7 0])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d06-s00 (d06/parse d06-s00-raw)))))

(deftest largest-value-test
  (testing "Finds the position and value of max num"
    (is (= [2 5] (d06/largest-value [0 2 5 0])))
    (is (= [0 8] (d06/largest-value [8 1 8 7])))))

(deftest reallocate-test
  (testing "Redistributes the blocks to sequential banks"
    (is (= [2 4 1 2] (d06/reallocate d06-s00)))
    (is (= [3 1 2 3] (d06/reallocate [2 4 1 2])))
    (is (= [0 2 3 4] (d06/reallocate [3 1 2 3])))
    (is (= [1 3 4 1] (d06/reallocate [0 2 3 4])))
    (is (= [2 4 1 2] (d06/reallocate [1 3 4 1])))))

(deftest cycles-to-repeat-test
  (testing "Computes the number of cycles till a repeat value seen"
    (is (= 5 (d06/cycles-to-repeat d06-s00)))))

(deftest loop-size-test
  (testing "Computes the size of the loop for repeated values"
    (is (= 4 (d06/loop-size d06-s00)))))

(def day06-input (u/parse-puzzle-input d06/parse 2017 6))

(deftest part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= 6681 (d06/part1 day06-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day06, part2"
    (is (= 2392 (d06/part2 day06-input)))))