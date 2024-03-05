(ns aoc-clj.2016.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day01 :as t]))

(def d01-s00 (t/parse ["R2, L3"]))
(def d01-s01 (t/parse ["R2, R2, R2"]))
(def d01-s02 (t/parse ["R5, L5, R5, R3"]))
(def d01-s03 (t/parse ["R8, R4, R4, R8"]))



(deftest move-test
  (testing "Moves to the correct location on sample data"
    (is (= [2 3]  (:pos (t/move d01-s00))))
    (is (= [0 -2] (:pos (t/move d01-s01))))
    (is (= [10 2] (:pos (t/move d01-s02))))))

(deftest distance-test
  (testing "Moves to the correct location on sample data"
    (is (= 5  (t/distance d01-s00)))
    (is (= 2  (t/distance d01-s01)))
    (is (= 12 (t/distance d01-s02)))))

(deftest first-location-visited-twice-test
  (testing "Finds the first location visited twice"
    (is (= [4 0] (t/first-duplicate (t/all-points d01-s03)))))
  (testing "Finds distance to the first location visited twice"
    (is (= 4 (t/distance-to-first-duplicate d01-s03)))))

(def day01-input (u/parse-puzzle-input t/parse 2016 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= 241 (t/part1 day01-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day01, part2"
    (is (= 116 (t/part2 day01-input)))))