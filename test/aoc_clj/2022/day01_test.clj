(ns aoc-clj.2022.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day01 :as t]))

(deftest day01-part1-soln
  (testing "Reproduces the answer for day01, part1"
    (is (= 70116 (t/day01-part1-soln)))))

(deftest day01-part2-soln
  (testing "Reproduces the answer for day01, part2"
    (is (= 206582 (t/day01-part2-soln)))))