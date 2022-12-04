(ns aoc-clj.2022.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day04 :as t]))

(def d04-s01
  (t/parse
   ["2-4,6-8"
    "2-3,4-5"
    "5-7,7-9"
    "2-8,3-7"
    "6-6,4-6"
    "2-6,4-8"]))

(deftest fully-contained-test
  (testing "Finds the pairs where one is fully contained in the other"
    (is (= [[[2 8] [3 7]] [[6 6] [4 6]]]
           (filter t/fully-contained? d04-s01)))))

(deftest overlap-test
  (testing "Finds the pairs where one overlaps with the other"
    (is (= [[[5 7] [7 9]] [[2 8] [3 7]] [[6 6] [4 6]] [[2 6] [4 8]]]
           (filter t/overlap? d04-s01)))))

(deftest day04-part1-soln
  (testing "Reproduces the answer for day04, part1"
    (is (= 584 (t/day04-part1-soln)))))

(deftest day04-part2-soln
  (testing "Reproduces the answer for day04, part2"
    (is (= 933 (t/day04-part2-soln)))))