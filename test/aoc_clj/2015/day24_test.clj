(ns aoc-clj.2015.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day24 :as d24]))

(def d24-s00-raw ["1" "2" "3" "4" "5" "7" "8" "9" "10" "11"])
(def d24-s00 [1 2 3 4 5 7 8 9 10 11])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d24-s00 (d24/parse d24-s00-raw)))))

(deftest smallest-passenger-packages-test
  (testing "Computes the combinations of packages for the passenger compartment
            with the smallest number of packages"
    (is (= [[9 11]] (d24/smallest-passenger-packages 3 d24-s00)))
    (is (= [[4 11] [5 10] [7 8]] (d24/smallest-passenger-packages 4 d24-s00)))))

(deftest ideal-quantum-entanglement-test
  (testing "Correctly computes the lowest quantum entanglement for the sample data"
    (is (= 99 (d24/ideal-quantum-entanglement 3 d24-s00)))
    (is (= 44 (d24/ideal-quantum-entanglement 4 d24-s00)))))

(def day24-input (u/parse-puzzle-input d24/parse 2015 24))

(deftest part1-test
  (testing "Reproduces the answer for day24, part1"
    (is (= 11846773891 (d24/part1 day24-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day24, part1"
    (is (= 80393059 (d24/part2 day24-input)))))