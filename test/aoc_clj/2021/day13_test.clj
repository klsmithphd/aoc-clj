(ns aoc-clj.2021.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day13 :as t]))

(def d13-s00
  (t/parse
   ["6,10"
    "0,14"
    "9,10"
    "0,3"
    "10,4"
    "4,11"
    "6,0"
    "6,12"
    "4,1"
    "0,13"
    "10,12"
    "3,4"
    "3,0"
    "8,4"
    "1,10"
    "2,14"
    "8,10"
    "9,0"
    ""
    "fold along y=7"
    "fold along x=5"]))

(deftest dots-after-first-fold
  (testing "Counts the dots after only one fold in sample data"
    (is (= 17 (t/dots-after-first-fold d13-s00)))))

(deftest complete-folds
  (testing "Returns the 16 dots after all folds in sample data"
    (is (= #{[0 0] [1 0] [2 0] [3 0] [4 0]
             [0 1]                   [4 1]
             [0 2]                   [4 2]
             [0 3]                   [4 3]
             [0 4] [1 4] [2 4] [3 4] [4 4]}
           (t/complete-folds d13-s00)))))

(def day13-input (u/parse-puzzle-input t/parse 2021 13))

(deftest part1-test
  (testing "Reproduces the answer for day13, part1"
    (is (= 621 (t/part1 day13-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day13, part2"
    (is (= "HKUJGAJZ" (t/part2 day13-input)))))
