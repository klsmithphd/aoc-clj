(ns aoc-clj.2017.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day13 :as d13]))

(def d13-s00-raw
  ["0: 3"
   "1: 2"
   "4: 4"
   "6: 4"])

(def d13-s00
  {0 3
   1 2
   4 4
   6 4})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d13-s00 (d13/parse d13-s00-raw)))))

(deftest caught?-test
  (testing "Finds the scanners that will catch you"
    (is (= [[0 3] [6 4]] (filter d13/caught? d13-s00)))))

(deftest severity-test
  (testing "Computes the severity of being caught"
    (is (= 24 (d13/severity d13-s00)))))

(def day13-input (u/parse-puzzle-input d13/parse 2017 13))

(deftest part1-test
  (testing "Reproduces the answer for day13, part1"
    (is (= 1840 (d13/part1 day13-input)))))
