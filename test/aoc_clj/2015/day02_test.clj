(ns aoc-clj.2015.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day02 :as t]))

(deftest wrapping-paper-area
  (testing "Computes the correct area of wrapping paper"
    (is (= 58 (t/wrapping-paper-area [2 3 4])))
    (is (= 43 (t/wrapping-paper-area [1 1 10])))))

(deftest ribbon-length
  (testing "Computes the length of the ribbon from the examples given"
    (is (= 34 (t/ribbon-length [2 3 4])))
    (is (= 14 (t/ribbon-length [1 1 10])))))

(deftest day02-part1-soln
  (testing "Reproduces the answer for day02, part1"
    (is (= 1598415 (t/day02-part1-soln)))))

(deftest day02-part2-soln
  (testing "Reproduces the answer for day02, part2"
    (is (= 3812909 (t/day02-part2-soln)))))