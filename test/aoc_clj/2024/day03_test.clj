(ns aoc-clj.2024.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day03 :as d03]))

(def d03-s00
  "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

(deftest real-mul-insts-test
  (testing "Finds the real mul instructions in a string"
    (is (= ["mul(2,4)"
            "mul(5,5)"
            "mul(11,8)"
            "mul(8,5)"]
           (d03/real-mul-insts d03-s00)))))

(deftest mul-args-test
  (testing "Extracts the multiplicands from a mul inst string"
    (is (= [2 4] (d03/mul-args "mul(2,4)")))))

(deftest sum-of-products-test
  (testing "Computes the sum of products of the real mul insts"
    (is (= 161 (d03/sum-of-products d03-s00)))))

(def day03-input (u/parse-puzzle-input d03/parse 2024 3))

(deftest part1-test
  (testing "Reproduces the answer for day03, part1"
    (is (= 196826776 (d03/part1 day03-input)))))