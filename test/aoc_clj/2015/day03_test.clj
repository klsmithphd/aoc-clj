(ns aoc-clj.2015.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day03 :as t]))

(def d03-s00 ">")
(def d03-s01 "^>v<")
(def d03-s02 "^v^v^v^v^v")
(def d03-s03 "^v")

(deftest houses-visited
  (testing "Counts the houses visited following the directions"
    (is (= 2 (t/houses-visited d03-s00)))
    (is (= 4 (t/houses-visited d03-s01)))
    (is (= 2 (t/houses-visited d03-s02)))))

(deftest split-houses-visited
  (testing "Counts houses visited by Santa and Robo-Santa"
    (is (= 3  (t/split-houses-visited d03-s03)))
    (is (= 3  (t/split-houses-visited d03-s01)))
    (is (= 11 (t/split-houses-visited d03-s02)))))

(def day03-input (u/parse-puzzle-input t/parse 2015 3))

(deftest day03-part1-soln
  (testing "Reproduces the answer for day03, part1"
    (is (= 2081 (t/day03-part1-soln day03-input)))))

(deftest day03-part2-soln
  (testing "Reproduces the answer for day03, part2"
    (is (= 2341 (t/day03-part2-soln day03-input)))))