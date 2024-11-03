(ns aoc-clj.2018.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day01 :as d01]))

(def d01-s00-raw
  ["+1"
   "+1"
   "+1"])

(def d01-s01-raw
  ["+1"
   "+1"
   "-2"])

(def d01-s02-raw
  ["-1"
   "-2"
   "-3"])

(def d01-s00 [1 1 1])
(def d01-s01 [1 1 -2])
(def d01-s02 [-1 -2 -3])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d01-s00 (d01/parse d01-s00-raw)))
    (is (= d01-s01 (d01/parse d01-s01-raw)))
    (is (= d01-s02 (d01/parse d01-s02-raw)))))

(deftest net-freq-change-test
  (testing "Finds the net change in frequency"
    (is (= 3 (d01/net-freq-change d01-s00)))
    (is (= 0 (d01/net-freq-change d01-s01)))
    (is (= -6 (d01/net-freq-change d01-s02)))))

(deftest find-first-repeated-freq-test
  (testing "Finds the first net frequency that duplicates"
    (is (= 2 (d01/find-first-repeated-freq [1 -2 3 1])))
    (is (= 0 (d01/find-first-repeated-freq [1 -1])))
    (is (= 10 (d01/find-first-repeated-freq [3 3 4 -2 -4])))
    (is (= 5 (d01/find-first-repeated-freq [-6 3 8 5 -6])))
    (is (= 14 (d01/find-first-repeated-freq [7 7 -2 -7 -4])))))

(def day01-input (u/parse-puzzle-input d01/parse 2018 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= 543 (d01/part1 day01-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day01, part2"
    (is (= 621 (d01/part2 day01-input)))))