(ns aoc-clj.2017.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day14 :as d14]))

(def d14-s00 "flqrgnkx")

(deftest used-squares-test
  (testing "Computes the number of used squares in the grid"
    (is (= 8108 (d14/used-squares d14-s00)))))

(def day14-input (u/parse-puzzle-input d14/parse 2017 14))

(deftest part1-test
  (testing "Reproduces the answer for day14, part1"
    (is (= 8222 (d14/part1 day14-input)))))
