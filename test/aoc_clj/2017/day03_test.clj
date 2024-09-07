(ns aoc-clj.2017.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day03 :as d03]))

(deftest spiral-position-test
  (testing "Finds the coordinate position of numbers laid out in a spiral"
    (is (= [0 0] (d03/spiral-position 1)))
    (is (= [1 0] (d03/spiral-position 2)))
    (is (= [1 1] (d03/spiral-position 3)))
    (is (= [0 1] (d03/spiral-position 4)))
    (is (= [-1 1] (d03/spiral-position 5)))
    (is (= [-1 0] (d03/spiral-position 6)))
    (is (= [-1 -1] (d03/spiral-position 7)))
    (is (= [0 -1] (d03/spiral-position 8)))
    (is (= [1 -1] (d03/spiral-position 9)))
    (is (= [-1 2] (d03/spiral-position 16)))
    (is (= [2 -2] (d03/spiral-position 25)))))

(deftest distance-test
  (testing "Computes distance from numbered cell to origin"
    (is (= 0 (d03/distance 1)))
    (is (= 3 (d03/distance 12)))
    (is (= 2 (d03/distance 23)))
    (is (= 31 (d03/distance 1024)))))

(def day03-input (u/parse-puzzle-input d03/parse 2017 3))

(deftest part1-test
  (testing "Reproduces the answer for day03, part1"
    (is (= 552 (d03/part1 day03-input)))))