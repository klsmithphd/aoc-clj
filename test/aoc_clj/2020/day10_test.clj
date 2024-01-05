(ns aoc-clj.2020.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day10 :as t]))

(def d10-s00
  (t/parse
   ["16"
    "10"
    "15"
    "5"
    "1"
    "11"
    "7"
    "19"
    "6"
    "12"
    "4"]))

(def d10-s01
  (t/parse
   ["28"
    "33"
    "18"
    "42"
    "31"
    "14"
    "46"
    "20"
    "48"
    "47"
    "24"
    "23"
    "49"
    "45"
    "19"
    "38"
    "39"
    "11"
    "1"
    "32"
    "25"
    "35"
    "8"
    "17"
    "7"
    "9"
    "4"
    "2"
    "34"
    "10"
    "3"]))

(deftest jolt-diff-counts
  (testing "Correctly determines the number of 1-jolt and 3-jolt differences"
    (is (= [7 5]   (t/freq-steps d10-s00)))
    (is (= [22 10] (t/freq-steps d10-s01)))))

(deftest combination-counts
  (testing "Correctly determines the number of unique valid combinations of adapters"
    (is (= 8     (t/combination-count d10-s00)))
    (is (= 19208 (t/combination-count d10-s01)))))

(def day10-input (u/parse-puzzle-input t/parse 2020 10))

(deftest part1-test
  (testing "Reproduces the answer for day10, part1"
    (is (= 2760 (t/part1 day10-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day10, part2"
    (is (= 13816758796288 (t/part2 day10-input)))))