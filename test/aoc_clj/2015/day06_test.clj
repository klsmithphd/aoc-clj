(ns aoc-clj.2015.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day06 :as t]))

(deftest day06-part1-soln
  (testing "Reproduces the answer for day06, part1"
    (is (= 377891 (t/day06-part1-soln)))))

(deftest day06-part2-soln
  (testing "Reproduces the answer for day06, part2"
    (is (= 14110788 (t/day06-part2-soln)))))