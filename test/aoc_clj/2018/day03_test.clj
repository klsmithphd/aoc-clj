(ns aoc-clj.2018.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day03 :as d03]))

(def d03-s00-raw
  ["#1 @ 1,3: 4x4"
   "#2 @ 3,1: 4x4"
   "#3 @ 5,5: 2x2"])

(def d03-s00
  [{:id 1 :x 1 :y 3 :w 4 :h 4}
   {:id 2 :x 3 :y 1 :w 4 :h 4}
   {:id 3 :x 5 :y 5 :w 2 :h 2}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d03-s00 (d03/parse d03-s00-raw)))))

(deftest overlapping-squares-test
  (testing "Counts the number of squares in more than one claim"
    (is (= 4 (d03/overlapping-squares d03-s00)))))

(deftest nonoverlapping-swath-test
  (testing "Finds the id of the swath that doesn't overlap"
    (is (= 3 (d03/nonoverlapping-swath d03-s00)))))

(def day03-input (u/parse-puzzle-input d03/parse 2018 3))

(deftest part1-test
  (testing "Reproduces the answer for day03, part1"
    (is (= 116489 (d03/part1 day03-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day03, part2"
    (is (= 1260 (d03/part2 day03-input)))))