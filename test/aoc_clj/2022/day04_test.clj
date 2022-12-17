(ns aoc-clj.2022.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day04 :as t]))

(deftest day04-part1-soln
  (testing "Reproduces the answer for day04, part1"
    (is (= 584 (t/day04-part1-soln)))))

(deftest day04-part2-soln
  (testing "Reproduces the answer for day04, part2"
    (is (= 933 (t/day04-part2-soln)))))