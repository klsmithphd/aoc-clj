(ns aoc-clj.2017.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day12 :as d12]))

(def d12-s00-raw
  ["0 <-> 2"
   "1 <-> 1"
   "2 <-> 0, 3, 4"
   "3 <-> 2, 4"
   "4 <-> 2, 3, 6"
   "5 <-> 6"
   "6 <-> 4, 5"])

(def d12-s00
  {0 [2]
   1 [1]
   2 [0 3 4]
   3 [2 4]
   4 [2 3 6]
   5 [6]
   6 [4 5]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d12-s00 (d12/parse d12-s00-raw)))))

(deftest group-size-test
  (testing "Computes the number of reachable programs in the group"
    (is (= 6 (d12/group-size d12-s00 0)))
    (is (= 1 (d12/group-size d12-s00 1)))))

(def day12-input (u/parse-puzzle-input d12/parse 2017 12))

(deftest part1-test
  (testing "Reproduces the answer for day12, part1"
    (is (= 239 (d12/part1 day12-input)))))