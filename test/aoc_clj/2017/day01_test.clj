(ns aoc-clj.2017.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day01 :as d01]))

(def d01-s00-raw ["1122"])

(def d01-s00 [1 1 2 2])
(def d01-s01 [1 1 1 1])
(def d01-s02 [1 2 3 4])
(def d01-s03 [9 1 2 1 2 1 2 9])

(def d01-s04 [1 2 1 2])
(def d01-s05 [1 2 2 1])
(def d01-s06 [1 2 3 4 2 5])
(def d01-s07 [1 2 3 1 2 3])
(def d01-s08 [1 2 1 3 1 4 1 5])

(deftest parse-test
  (testing "Correctly parses thte input"
    (is (= d01-s00 (d01/parse d01-s00-raw)))))

(deftest sum-of-matching-digits-test
  (testing "Computes the sum of all the digits that match the next digit"
    (is (= 3 (d01/sum-of-matching-digits d01-s00)))
    (is (= 4 (d01/sum-of-matching-digits d01-s01)))
    (is (= 0 (d01/sum-of-matching-digits d01-s02)))
    (is (= 9 (d01/sum-of-matching-digits d01-s03)))))

(deftest sum-of-halfway-around-matching-digits-test
  (testing "Computes the sum of all the digits that match a digit
            halfway around the collection"
    (is (= 6 (d01/sum-of-halfway-around-matching-digits d01-s04)))
    (is (= 0 (d01/sum-of-halfway-around-matching-digits d01-s05)))
    (is (= 4 (d01/sum-of-halfway-around-matching-digits d01-s06)))
    (is (= 12 (d01/sum-of-halfway-around-matching-digits d01-s07)))
    (is (= 4 (d01/sum-of-halfway-around-matching-digits d01-s08)))))

(def day01-input (u/parse-puzzle-input d01/parse 2017 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= 1253 (d01/part1 day01-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day01, part2"
    (is (= 1278 (d01/part2 day01-input)))))
