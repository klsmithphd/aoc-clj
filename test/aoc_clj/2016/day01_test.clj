(ns aoc-clj.2016.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day01 :as d01]))

(def d01-s00-raw ["R2, L3"])
(def d01-s00 [[:right 2] [:left 3]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d01-s00 (d01/parse d01-s00-raw)))))

(def d01-s01 (d01/parse ["R2, R2, R2"]))
(def d01-s02 (d01/parse ["R5, L5, R5, R3"]))
(def d01-s03 (d01/parse ["R8, R4, R4, R8"]))

(deftest move-test
  (testing "Moves to the correct location on sample data"
    (is (= [2 3]  (:pos (d01/move d01-s00))))
    (is (= [0 -2] (:pos (d01/move d01-s01))))
    (is (= [10 2] (:pos (d01/move d01-s02))))))

(deftest distance-test
  (testing "Moves to the correct location on sample data"
    (is (= 5  (d01/distance d01-s00)))
    (is (= 2  (d01/distance d01-s01)))
    (is (= 12 (d01/distance d01-s02)))))

(deftest first-location-visited-twice-test
  (testing "Finds the first location visited twice"
    (is (= [4 0] (u/first-duplicate (d01/all-points d01-s03)))))
  (testing "Finds distance to the first location visited twice"
    (is (= 4 (d01/distance-to-first-duplicate d01-s03)))))

(def day01-input (u/parse-puzzle-input d01/parse 2016 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= 241 (d01/part1 day01-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day01, part2"
    (is (= 116 (d01/part2 day01-input)))))