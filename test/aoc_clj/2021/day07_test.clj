(ns aoc-clj.2021.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day07 :as t]))

(def day07-sample (t/parse ["16,1,2,0,4,2,7,1,2,14"]))

(deftest min-fuel
  (testing "Computes minimum fuel for sample data"
    (is (= 37 (t/min-fuel day07-sample)))))

(deftest min-fuel-part2
  (testing "Computes minimum fuel for sample data in part2 rules"
    (is (= 168 (t/min-fuel-part2 day07-sample)))))

(def day07-input (u/parse-puzzle-input t/parse 2021 7))

(deftest part1-test
  (testing "Reproduces the answer for day07, part1"
    (is (= 349769 (t/part1 day07-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day07, part2"
    (is (= 99540554 (t/part2 day07-input)))))