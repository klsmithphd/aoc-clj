(ns aoc-clj.2024.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day03 :as d03]))

(def d03-s00
  "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

(def d03-s01
  "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

(deftest real-mul-insts-test
  (testing "Finds the real mul instructions in a string"
    (is (= ["mul(2,4)"
            "mul(5,5)"
            "mul(11,8)"
            "mul(8,5)"]
           (d03/real-mul-insts d03-s00)))))

(deftest all-insts-test
  (testing "Finds all the valid instructions in a string"
    (is (= ["mul(2,4)"
            "don't()"
            "mul(5,5)"
            "mul(11,8)"
            "do()"
            "mul(8,5)"]
           (d03/all-insts d03-s01)))))

(deftest enablement-groups-test
  (testing "Groups instructions by do()/don't() followers"
    (is (= [["mul(2,4)"]
            ["don't()" "mul(5,5)" "mul(11,8)"]
            ["do()" "mul(8,5)"]]
           (d03/enablement-groups (d03/all-insts d03-s01))))))

(deftest enabled-insts-test
  (testing "Returns only the enabled real mul insts"
    (is (= ["mul(2,4)" "mul(8,5)"]
           (d03/enabled-insts d03-s01)))))

(deftest mul-args-test
  (testing "Extracts the multiplicands from a mul inst string"
    (is (= [2 4] (d03/mul-args "mul(2,4)")))))

(deftest sum-of-products-test
  (testing "Computes the sum of products of the real mul insts"
    (is (= 161 (d03/sum-of-products (d03/real-mul-insts d03-s00))))
    (is (= 48  (d03/sum-of-products (d03/enabled-insts d03-s01))))))

(def day03-input (u/parse-puzzle-input d03/parse 2024 3))

(deftest part1-test
  (testing "Reproduces the answer for day03, part1"
    (is (= 196826776 (d03/part1 day03-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day03, part2"
    (is (= 106780429 (d03/part2 day03-input)))))