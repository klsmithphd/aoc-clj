(ns aoc-clj.2015.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day24 :as t]))

(def day24-sample (concat (range 1 6) (range 7 12)))

(deftest best-qe-test
  (testing "Correctly computes the lowest quantum entanglement for the sample data"
    (is (= 99 (t/best-qe-thirds day24-sample)))
    (is (= 44 (t/best-qe-fourths day24-sample)))))

(deftest day24-part1-soln
  (testing "Reproduces the answer for day24, part1"
    (is (= 11846773891 (t/day24-part1-soln)))))

(deftest day24-part2-soln
  (testing "Reproduces the answer for day24, part1"
    (is (= 80393059 (t/day24-part2-soln)))))