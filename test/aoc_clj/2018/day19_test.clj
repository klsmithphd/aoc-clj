(ns aoc-clj.2018.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day19 :as d19]))

(def d19-s00-raw
  ["#ip 0"
   "seti 5 0 1"
   "seti 6 0 2"
   "addi 0 1 0"
   "addr 1 2 3"
   "setr 1 0 0"
   "seti 8 0 4"
   "seti 9 0 5"])

(def d19-s00
  {:ip 0
   :insts [[:seti 5 0 1]
           [:seti 6 0 2]
           [:addi 0 1 0]
           [:addr 1 2 3]
           [:setr 1 0 0]
           [:seti 8 0 4]
           [:seti 9 0 5]]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d19-s00 (d19/parse d19-s00-raw)))))

(deftest step-test
  (testing "Iterates the state forward one instruction"
    (is (= [1 5 0 0 0 0]
           (d19/step d19-s00 [0 0 0 0 0 0])))

    (is (= [2 5 6 0 0 0]
           (d19/step d19-s00 [1 5 0 0 0 0])))

    (is (= [4 5 6 0 0 0]
           (d19/step d19-s00 [2 5 6 0 0 0])))

    (is (= [6 5 6 0 0 0]
           (d19/step d19-s00 [4 5 6 0 0 0])))

    (is (= [7 5 6 0 0 9]
           (d19/step d19-s00 [6 5 6 0 0 0])))))

(deftest execute-test
  (testing "Executes the program until it halts"
    (is (= [7 5 6 0 0 9] (d19/execute d19/init-regs d19-s00)))))

(def day19-input (u/parse-puzzle-input d19/parse 2018 19))

(deftest part1-test
  (testing "Reproduces the answer for day19, part1"
    (is (= 1764 (d19/part1 day19-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day19, part2"
    (is (= 0 (d19/part2 day19-input)))))