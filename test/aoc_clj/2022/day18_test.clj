(ns aoc-clj.2022.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day18 :as t]))

(def d18-s00
  #{[1 1 1]
    [2 1 1]})

(def d18-s01
  #{[2 2 2]
    [1 2 2]
    [3 2 2]
    [2 1 2]
    [2 3 2]
    [2 2 1]
    [2 2 3]
    [2 2 4]
    [2 2 6]
    [1 2 5]
    [3 2 5]
    [2 1 5]
    [2 3 5]})

(deftest surface-area-test
  (testing "Counts the number of exposed (non-adjacent) sides of the cubes"
    (is (= 10 (t/surface-area d18-s00)))
    (is (= 64 (t/surface-area d18-s01)))))

(deftest outer-surface-area-test
  (testing "Counts the number of exposed (non-adjacent) sides of the cubes
            that are along the outer surface"
    (is (= 10 (t/outer-surface-area d18-s00)))
    (is (= 58 (t/outer-surface-area d18-s01)))))

(def day18-input (u/parse-puzzle-input t/parse 2022 18))

(deftest day18-part1-soln
  (testing "Reproduces the answer for day18, part1"
    (is (= 3390 (t/day18-part1-soln day18-input)))))

(deftest day18-part2-soln
  (testing "Reproduces the answer for day18, part2"
    (is (= 2058 (t/day18-part2-soln day18-input)))))