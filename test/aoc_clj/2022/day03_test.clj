(ns aoc-clj.2022.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day03 :as t]))

(def d03-s01
  ["vJrwpWtwJgWrhcsFMMfFFhFp"
   "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
   "PmmdzqPrVvPwwTWBwg"
   "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
   "ttgJtRGJQctTZtZT"
   "CrZsJsPPZsGzwwsLwLmpwMDw"])

(deftest overlaps-test
  (testing "Finds the overlapping items in the first/second halves
            or among groups of three"
    (is (= [\p \L \P \v \t \s]
           (t/overlaps ::t/halfway d03-s01)))
    (is (= [\r \Z]
           (t/overlaps ::t/thirds d03-s01)))))

(deftest priority-test
  (testing "Computes the priority for a given character"
    (is (= 16 (t/priority \p)))
    (is (= 38 (t/priority \L)))
    (is (= 42 (t/priority \P)))
    (is (= 22 (t/priority \v)))
    (is (= 20 (t/priority \t)))
    (is (= 19 (t/priority \s)))))

(deftest overlaps-priority-test
  (testing "Finds the sum of the priorities of the overlapping items"
    (is (= 157 (t/overlap-priority-sum ::t/halfway d03-s01)))
    (is (= 70  (t/overlap-priority-sum ::t/thirds d03-s01)))))

(deftest day03-part1-soln
  (testing "Reproduces the answer for day03, part1"
    (is (= 7597 (t/day03-part1-soln)))))

(deftest day03-part2-soln
  (testing "Reproduces the answer for day03, part2"
    (is (= 2607 (t/day03-part2-soln)))))