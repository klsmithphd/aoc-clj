(ns aoc-clj.2023.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day09 :as t]))

(def d09-s00-raw ["0 3 6 9 12 15"
                  "1 3 6 10 15 21"
                  "10 13 16 21 30 45"])

(def d09-s00 [[0 3 6 9 12 15]
              [1 3 6 10 15 21]
              [10 13 16 21 30 45]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d09-s00 (t/parse d09-s00-raw)))))

(deftest extrapolate-test
  (testing "Appends the correct next value in the sequence"
    (is (= [0 3 6 9 12 15 18]     (t/right-extrapolate (nth d09-s00 0))))
    (is (= [1 3 6 10 15 21 28]    (t/right-extrapolate (nth d09-s00 1))))
    (is (= [10 13 16 21 30 45 68] (t/right-extrapolate (nth d09-s00 2))))))

(deftest left-extrapolate-test
  (testing "Prepends the correct next value in the sequence"
    (is (= [-3 0 3 6 9 12 15]    (t/left-extrapolate (nth d09-s00 0))))
    (is (= [0 1 3 6 10 15 21]    (t/left-extrapolate (nth d09-s00 1))))
    (is (= [5 10 13 16 21 30 45] (t/left-extrapolate (nth d09-s00 2))))))

(deftest extrapolation-sum-test
  (testing "Returns the sum of the extrapolated values"
    (is (= 114 (t/extrapolation-sum d09-s00)))
    (is (= 2   (t/extrapolation-sum d09-s00 true)))))

(def day09-input (u/parse-puzzle-input t/parse 2023 9))

(deftest part1-test
  (testing "Reproduces the answer for day09, part1"
    (is (= 1647269739 (t/part1 day09-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day09, part2"
    (is (= 864 (t/part2 day09-input)))))
