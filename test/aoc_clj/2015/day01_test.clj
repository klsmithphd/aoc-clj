(ns aoc-clj.2015.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day01 :as d01]))

(def d01-s00 (d01/parse ["(())"]))
(def d01-s01 (d01/parse ["()()"]))
(def d01-s02 (d01/parse ["((("]))
(def d01-s03 (d01/parse ["(()(()("]))
(def d01-s04 (d01/parse ["))((((("]))
(def d01-s05 (d01/parse ["())"]))
(def d01-s06 (d01/parse ["))("]))
(def d01-s07 (d01/parse [")))"]))
(def d01-s08 (d01/parse [")())())"]))

(def d01-s09 (d01/parse [")"]))
(def d01-s10 (d01/parse ["()())"]))

(deftest final-floor
  (testing "Correctly determines the final floor"
    (is (= 0  (d01/final-floor d01-s00)))
    (is (= 0  (d01/final-floor d01-s01)))
    (is (= 3  (d01/final-floor d01-s02)))
    (is (= 3  (d01/final-floor d01-s03)))
    (is (= 3  (d01/final-floor d01-s04)))
    (is (= -1 (d01/final-floor d01-s05)))
    (is (= -1 (d01/final-floor d01-s06)))
    (is (= -3 (d01/final-floor d01-s07)))
    (is (= -3 (d01/final-floor d01-s08)))))

(deftest first-pos-in-basement
  (testing "Finds the first position where the sum equals -1"
    (is (= 1 (d01/first-pos-in-basement d01-s09)))
    (is (= 5 (d01/first-pos-in-basement d01-s10)))))

(def day01-input (u/parse-puzzle-input d01/parse 2015 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= 138 (d01/part1 day01-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day01, part2"
    (is (= 1771 (d01/part2 day01-input)))))