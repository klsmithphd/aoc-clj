(ns aoc-clj.2015.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day02 :as d02]))

(def d02-s00-raw ["2x3x4" "1x1x10" "9x8x7"])
(def d02-s00     [[2 3 4] [1 1 10] [7 8 9]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d02-s00 (d02/parse d02-s00-raw)))))

(deftest wrapping-paper-area
  (testing "Computes the correct area of wrapping paper"
    (is (= 58 (d02/wrapping-paper-area (nth d02-s00 0))))
    (is (= 43 (d02/wrapping-paper-area (nth d02-s00 1))))))

(deftest ribbon-length
  (testing "Computes the length of the ribbon from the examples given"
    (is (= 34 (d02/ribbon-length (nth d02-s00 0))))
    (is (= 14 (d02/ribbon-length (nth d02-s00 1))))))

(def day02-input (u/parse-puzzle-input d02/parse 2015 2))

(deftest part1-test
  (testing "Reproduces the answer for day02, part1"
    (is (= 1598415 (d02/part1 day02-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day02, part2"
    (is (= 3812909 (d02/part2 day02-input)))))