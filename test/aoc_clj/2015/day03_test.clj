(ns aoc-clj.2015.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day03 :as d03]))

(def d03-s00 ">")
(def d03-s01 "^>v<")
(def d03-s02 "^v^v^v^v^v")
(def d03-s03 "^v")

(deftest santa-visits-test
  (testing "Computes the positions of the houses santa visited"
    (is (= [[0 0] [1 0]] (d03/visits d03-s00)))
    (is (= [[0 0] [0 1] [1 1] [1 0] [0 0]] (d03/visits d03-s01)))
    (is (= [[0 0] [0 1] [0 0] [0 1] [0 0] [0 1] [0 0]
            [0 1] [0 0] [0 1] [0 0]]
           (d03/visits d03-s02)))))

(deftest split-visits-test
  (testing "Computes the positions of the houses santa and his robot visited"
    (is (= [[0 0] [0 1] [0 0] [0 -1]] (d03/split-visits d03-s03)))
    (is (= [[0 0] [0 1] [0 0] [0 0] [1 0] [0 0]] (d03/split-visits d03-s01)))
    (is (= [[0 0] [0 1] [0 2] [0 3] [0 4] [0 5]
            [0 0] [0 -1] [0 -2] [0 -3] [0 -4] [0 -5]]
           (d03/split-visits d03-s02)))))

(deftest distinct-visits-test
  (testing "Counts the houses visited following the directions"
    (is (= 2 (d03/distinct-visits d03-s00 d03/visits)))
    (is (= 4 (d03/distinct-visits d03-s01 d03/visits)))
    (is (= 2 (d03/distinct-visits d03-s02 d03/visits)))

    (is (= 3  (d03/distinct-visits d03-s03 d03/split-visits)))
    (is (= 3  (d03/distinct-visits d03-s01 d03/split-visits)))
    (is (= 11 (d03/distinct-visits d03-s02 d03/split-visits)))))

(deftest split-houses-visited
  (testing "Counts houses visited by Santa and Robo-Santa"))

(def day03-input (u/parse-puzzle-input d03/parse 2015 3))

(deftest part1-test
  (testing "Reproduces the answer for day03, part1"
    (is (= 2081 (d03/part1 day03-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day03, part2"
    (is (= 2341 (d03/part2 day03-input)))))