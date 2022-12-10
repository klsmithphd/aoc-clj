(ns aoc-clj.2022.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day08 :as t]))

(def d08-s01
  (t/parse
   ["30373"
    "25512"
    "65332"
    "33549"
    "35390"]))

(deftest visible-trees-test
  (testing "Counts the number of visible trees in the sample data"
    (is (= 21 (t/visible-trees d08-s01)))))

(deftest max-scenic-score-test
  (testing "Finds the maximum scenic score in the sample data"
    (is (= 8 (t/max-scenic-score d08-s01)))))

(deftest day08-part1-soln
  (testing "Reproduces the answer for day08, part1"
    (is (= 1538 (t/day08-part1-soln)))))

(deftest day08-part2-soln
  (testing "Reproduces the answer for day08, part2"
    (is (= 496125 (t/day08-part2-soln)))))