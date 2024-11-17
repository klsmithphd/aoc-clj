(ns aoc-clj.2018.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day16 :as d16]))

(def d16-s00-raw
  ["Before: [3, 2, 1, 1]"
   "9 2 1 2"
   "After:  [3, 2, 2, 1]"
   ""
   "Before: [0, 1, 2, 1]"
   "12 3 2 2"
   "After:  [0, 1, 1, 1]"
   ""
   ""
   ""
   "7 2 0 0"
   "11 0 2 0"
   "2 1 1 3"])

(def d16-s00
  {:samples [[[3 2 1 1] [9 2 1 2] [3 2 2 1]]
             [[0 1 2 1] [12 3 2 2] [0 1 1 1]]]
   :program [[7 2 0 0] [11 0 2 0] [2 1 1 3]]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d16-s00 (d16/parse d16-s00-raw)))))

(deftest compatible-opcodes-test
  (testing "Determines which opcodes could match the sample"
    (is (= #{:addi :mulr :seti}
           (d16/compatible-opcodes (nth (:samples d16-s00) 0))))))

(def day16-input (u/parse-puzzle-input d16/parse 2018 16))

(deftest part1-test
  (testing "Reproduces the answer for day16, part1"
    (is (= 517 (d16/part1 day16-input)))))