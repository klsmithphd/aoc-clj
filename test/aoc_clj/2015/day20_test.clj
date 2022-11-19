(ns aoc-clj.2015.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day20 :as t]))

(deftest presents-test
  (testing "Counts the right number of presents at the first few houses"
    (is (= [10 30 40 70 60 120 80 150 130]
           (map t/house-presents (range 1 10))))))

(deftest day20-part1-soln
  (testing "Reproduces the answer for day20, part1"
    (is (= 776160 (t/day20-part1-soln)))))

(deftest day20-part2-soln
  (testing "Reproduces the answer for day20, part2"
    (is (= 786240 (t/day20-part2-soln)))))