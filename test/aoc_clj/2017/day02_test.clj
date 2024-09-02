(ns aoc-clj.2017.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day02 :as d02]))

(def d02-s00-raw
  ["5 1 9 5"
   "7 5 3"
   "2 4 6 8"])

(def d02-s00
  [[5 1 9 5]
   [7 5 3]
   [2 4 6 8]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d02-s00 (d02/parse d02-s00-raw)))))

(def d02-s01
  [[5 9 2 8]
   [9 4 7 3]
   [3 8 6 5]])

(deftest max-diff-test
  (testing "Finds the difference between max and min values"
    (is (= 8 (d02/max-diff (nth d02-s00 0))))
    (is (= 4 (d02/max-diff (nth d02-s00 1))))
    (is (= 6 (d02/max-diff (nth d02-s00 2))))))

(deftest even-quotient-test
  (testing "Finds the quotient of the values that divide evenly"
    (is (= 4 (d02/even-quotient (nth d02-s01 0))))
    (is (= 3 (d02/even-quotient (nth d02-s01 1))))
    (is (= 2 (d02/even-quotient (nth d02-s01 2))))))

(deftest checksum-test
  (testing "Computes the checksum"
    (is (= 18 (d02/checksum d02/max-diff d02-s00)))
    (is (= 9 (d02/checksum d02/even-quotient d02-s01)))))

(def day02-input (u/parse-puzzle-input d02/parse 2017 2))

(deftest part1-test
  (testing "Reproduces the answer for day02, part1"
    (is (= 53460 (d02/part1 day02-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day02, part2"
    (is (= 282 (d02/part2 day02-input)))))