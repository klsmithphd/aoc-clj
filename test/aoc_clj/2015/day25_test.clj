(ns aoc-clj.2015.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day25 :as d25]))

(def d25-s00-raw ["...Enter the code at row 2981, column 3075."])
(def d25-s00 [2981 3075])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d25-s00 (d25/parse d25-s00-raw)))))

(deftest code-number-test
  (testing "Correctly computes the code number for a given row col"
    (is (= 1  (d25/code-number 1 1)))
    (is (= 2  (d25/code-number 2 1)))
    (is (= 3  (d25/code-number 1 2)))
    (is (= 4  (d25/code-number 3 1)))
    (is (= 5  (d25/code-number 2 2)))
    (is (= 6  (d25/code-number 1 3)))
    (is (= 12 (d25/code-number 4 2)))
    (is (= 15 (d25/code-number 1 5)))))

(deftest code-at-position-test
  (testing "Computes the code at given row/col position"
    (is (= 20151125 (d25/code 1 1)))
    (is (= 33071741 (d25/code 6 1)))
    (is (= 33511524 (d25/code 1 6)))
    (is (= 27995004 (d25/code 6 6)))))

(def day25-input (u/parse-puzzle-input d25/parse 2015 25))

(deftest part1-test
  (testing "Reproduces the answer for day25, part1"
    (is (= 9132360 (d25/part1 day25-input)))))