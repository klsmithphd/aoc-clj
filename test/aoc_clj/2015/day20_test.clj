(ns aoc-clj.2015.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day20 :as d20]))

(def A000203-first70
  "https://oeis.org/A00020"
  [1 3 4 7 6 12 8 15 13 18 12 28 14 24 24 31 18 39 20 42 32 36 24 60
   31 42 40 56 30 72 32 63 48 54 48 91 38 60 56 90 42 96 44 84 78 72
   48 124 57 93 72 98 54 120 72 120 80 90 60 168 62 96 104 127 84 144
   68 126 96 144])

(deftest sum-of-divisors-test
  (testing "Computes the sum-of-divisors for integer n"
    (is (= A000203-first70 (map d20/sum-of-divisors (range 1 71))))))

(deftest presents-test
  (testing "Counts the right number of presents at the first few houses"
    (is (= [10 30 40 70 60 120 80 150 130]
           (map d20/house-presents (range 1 10))))))

(def day20-input (u/parse-puzzle-input d20/parse 2015 20))

(deftest part1-test
  (testing "Reproduces the answer for day20, part1"
    (is (= 776160 (d20/part1 day20-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day20, part2"
    (is (= 786240 (d20/part2 day20-input)))))