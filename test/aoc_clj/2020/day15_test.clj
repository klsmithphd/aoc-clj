(ns aoc-clj.2020.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day15 :as t]))

(def day15-sample1 (t/parse ["0,3,6"]))
(def day15-sample2 (t/parse ["1,3,2"]))
(def day15-sample3 (t/parse ["2,1,3"]))
(def day15-sample4 (t/parse ["1,2,3"]))
(def day15-sample5 (t/parse ["2,3,1"]))
(def day15-sample6 (t/parse ["3,2,1"]))
(def day15-sample7 (t/parse ["3,1,2"]))

(deftest find-2020th-number
  (testing "Can find the 2020th number in the sequence for sample data"
    (is (= 436  (last (t/memory-seq day15-sample1 2020))))
    (is (= 1    (last (t/memory-seq day15-sample2 2020))))
    (is (= 10   (last (t/memory-seq day15-sample3 2020))))
    (is (= 27   (last (t/memory-seq day15-sample4 2020))))
    (is (= 78   (last (t/memory-seq day15-sample5 2020))))
    (is (= 438  (last (t/memory-seq day15-sample6 2020))))
    (is (= 1836 (last (t/memory-seq day15-sample7 2020))))))

(def day15-input (u/parse-puzzle-input t/parse 2020 15))

(deftest part1-test
  (testing "Reproduces the answer for day15, part1"
    (is (= 1428 (t/part1 day15-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day15, part2"
    (is (= 3718541 (t/part2 day15-input)))))