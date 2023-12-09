(ns aoc-clj.2023.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day09 :as t]))

(def d09-s01-raw ["0 3 6 9 12 15"
                  "1 3 6 10 15 21"
                  "10 13 16 21 30 45"])

(def d09-s01 [[0 3 6 9 12 15]
              [1 3 6 10 15 21]
              [10 13 16 21 30 45]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d09-s01 (t/parse d09-s01-raw)))))

(deftest extrapolate-test
  (testing "Appends the correct next value in the sequence"
    (is (= [0 3 6 9 12 15 18]     (t/extrapolate (nth d09-s01 0))))
    (is (= [1 3 6 10 15 21 28]    (t/extrapolate (nth d09-s01 1))))
    (is (= [10 13 16 21 30 45 68] (t/extrapolate (nth d09-s01 2))))))

(deftest extrapolation-sum-test
  (testing "Returns the sum of the extrapolated values"
    (is (= 114 (t/extrapolation-sum d09-s01)))))

(def day09-input (u/parse-puzzle-input t/parse 2023 9))

(deftest day09-part1-soln
  (testing "Reproduces the answer for day09, part1"
    (is (= 2162 (t/day09-part1-soln day09-input)))))

