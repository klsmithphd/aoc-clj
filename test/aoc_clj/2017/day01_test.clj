(ns aoc-clj.2017.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day01 :as d01]))

(def d01-s00-raw ["1122"])

(def d01-s00 [1 1 2 2])
(def d01-s01 [1 1 1 1])
(def d01-s02 [1 2 3 4])
(def d01-s03 [9 1 2 1 2 1 2 9])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d01-s00 (d01/parse d01-s00-raw)))))

(deftest sum-of-matching-digits-test
  (testing "Computes the sum of all the digits that match the next digit"
    (is (= 3 (d01/sum-of-matching-digits d01-s00)))
    (is (= 4 (d01/sum-of-matching-digits d01-s01)))
    (is (= 0 (d01/sum-of-matching-digits d01-s02)))
    (is (= 9 (d01/sum-of-matching-digits d01-s03)))))

(def day01-input (u/parse-puzzle-input d01/parse 2017 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= 1253 (d01/part1 day01-input)))))
