(ns aoc-clj.2015.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day20 :as d20]))

(deftest presents-test
  (testing "Counts the right number of presents at the first few houses"
    (is (= {1 1
            2 3
            3 4
            4 7
            5 6
            6 12
            7 8
            8 15
            9 13} (d20/presents 10 0)))))

(deftest house-with-n-presents-test
  (testing "Finds the first house with at least n presents delivered"
    (is (= 4 (d20/house-with-n-presents 5 10)))
    (is (= 8 (d20/house-with-n-presents 14 10)))))

(def day20-input (u/parse-puzzle-input d20/parse 2015 20))

(deftest ^:slow part1-test
  (testing "Reproduces the answer for day20, part1"
    (is (= 776160 (d20/part1 day20-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day20, part2"
    (is (= 786240 (d20/part2 day20-input)))))