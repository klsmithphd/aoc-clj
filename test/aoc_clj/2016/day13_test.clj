(ns aoc-clj.2016.day13-test
  (:require [clojure.test :refer [deftest is testing]] 
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day13 :as d13]))

(deftest shortest-path-length-test
  (testing "Finds the length of the shortest path in the sample data"
    (is (= 11 (d13/shortest-path-length 10 [7 4])))))

(def day13-input (u/parse-puzzle-input d13/parse 2016 13))

(deftest part1-test
  (testing "Reproduces the answer for day13, part1"
    (is (= 90 (d13/part1 day13-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day13, part2"
    (is (= 135 (d13/part2 day13-input)))))