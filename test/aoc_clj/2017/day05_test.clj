(ns aoc-clj.2017.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day05 :as d05]))

(def d05-s00-raw
  ["0"
   "3"
   "0"
   "1"
   "-3"])

(def d05-s00
  [0 3 0 1 -3])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d05-s00 (d05/parse d05-s00-raw)))))

(deftest steps-to-escape-test
  (testing "Counts the number of steps it takes to escape"
    (is (= 5 (d05/steps-to-escape d05-s00)))))

(def day05-input (u/parse-puzzle-input d05/parse 2017 5))

(deftest part1-test
  (testing "Reproduces the answer for day05, part1"
    (is (= 339351 (d05/part1 day05-input)))))