(ns aoc-clj.2023.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2023.day01 :as t]))

(def d01-s01
  ["1abc2"
   "pqr3stu8vwx"
   "a1b2c3d4e5f"
   "treb7uchet"])

(deftest calibration-value-test
  (testing "Computes a calibration value (first and last digit)"
    (is (= [12 38 15 77]
           (mapv t/calibration-value d01-s01)))))

(deftest calibration-value-sum-test
  (testing "Computes the calibration value sum"
    (is (= 142 (t/calibration-value-sum d01-s01)))))

(deftest day01-part1-soln
  (testing "Reproduces the answer for day01, part1"
    (is (= 55172 (t/day01-part1-soln)))))