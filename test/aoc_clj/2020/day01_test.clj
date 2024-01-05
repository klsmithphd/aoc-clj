(ns aoc-clj.2020.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day01 :as t]))

(def d01-s00 [1721 979 366 299 675 1456])

(deftest find-pairs-that-sum
  (testing "Finds pair in a list that sums to 2020"
    (is (= '(299 1721) (t/find-pair-that-sums-to-total 2020 d01-s00)))))

(deftest find-triples-that-sum
  (testing "Finds a triple in a list that sums to 2020"
    (is (= '(979 366 675) (t/find-triple-that-sums-to-total 2020 d01-s00)))))

(def day01-input (u/parse-puzzle-input t/parse 2020 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= 989824 (t/part1 day01-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day01, part2"
    (is (= 66432240 (t/part2 day01-input)))))