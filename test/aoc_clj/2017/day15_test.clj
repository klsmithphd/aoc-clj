(ns aoc-clj.2017.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day15 :as d15]))

(def d15-s00-raw
  ["Generator A uses 65"
   "Generator B uses 8921"])

(def d15-s00 [65 8921])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d15-s00 (d15/parse d15-s00-raw)))))

(deftest judge-count-test
  (testing "How many values match within the sample size"
    (is (= 588 (d15/judge-count d15/sample-size d15-s00)))))

(def day15-input (u/parse-puzzle-input d15/parse 2017 15))

(deftest part1-test
  (testing "Reproduces the answer for day15, part1"
    (is (= 594 (d15/part1 day15-input)))))