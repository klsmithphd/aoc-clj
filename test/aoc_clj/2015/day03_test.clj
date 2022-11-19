(ns aoc-clj.2015.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day03 :as t]))

(def day03-sample1 ">")
(def day03-sample2 "^>v<")
(def day03-sample3 "^v^v^v^v^v")
(def day03-sample4 "^v")

(deftest houses-visited
  (testing "Counts the houses visited following the directions"
    (is (= 2 (t/houses-visited day03-sample1)))
    (is (= 4 (t/houses-visited day03-sample2)))
    (is (= 2 (t/houses-visited day03-sample3)))))

(deftest split-houses-visited
  (testing "Counts houses visited by Santa and Robo-Santa"
    (is (= 3 (t/split-houses-visited day03-sample4)))
    (is (= 3 (t/split-houses-visited day03-sample2)))
    (is (= 11 (t/split-houses-visited day03-sample3)))))

(deftest day03-part1-soln
  (testing "Reproduces the answer for day03, part1"
    (is (= 2081 (t/day03-part1-soln)))))

(deftest day03-part2-soln
  (testing "Reproduces the answer for day03, part2"
    (is (= 2341 (t/day03-part2-soln)))))