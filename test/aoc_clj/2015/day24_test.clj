(ns aoc-clj.2015.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day24 :as t]))

(def d24-s00 (concat (range 1 6) (range 7 12)))

(deftest best-qe-test
  (testing "Correctly computes the lowest quantum entanglement for the sample data"
    (is (= 99 (t/best-qe-thirds d24-s00)))
    (is (= 44 (t/best-qe-fourths d24-s00)))))

(def day24-input (u/parse-puzzle-input t/parse 2015 24))

(deftest part1-test
  (testing "Reproduces the answer for day24, part1"
    (is (= 11846773891 (t/part1 day24-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day24, part1"
    (is (= 80393059 (t/part2 day24-input)))))