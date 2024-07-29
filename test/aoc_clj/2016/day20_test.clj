(ns aoc-clj.2016.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day20 :as d20]
            [aoc-clj.utils.intervals :as ivals]))

(def d20-s00-raw
  ["5-8"
   "0-2"
   "4-7"])

(def d20-s00
  [[5 8]
   [0 2]
   [4 7]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d20-s00 (d20/parse d20-s00-raw)))))

(deftest lowest-unblocked-ip-test
  (testing "Finds the smallest unblocked IP address"
    (is (= 3 (d20/lowest-unblocked-ip d20-s00)))))

(def day20-input (u/parse-puzzle-input d20/parse 2016 20))

(deftest part1-test
  (testing "Reproduces the answer for day20, part1"
    (is (= 14975795 (d20/part1 day20-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day20, part2"
    (is (= 101 (d20/part2 day20-input)))))
