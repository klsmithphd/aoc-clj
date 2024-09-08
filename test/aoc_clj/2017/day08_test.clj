(ns aoc-clj.2017.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day08 :as d08]))

(def d08-s00-raw
  ["b inc 5 if a > 1"
   "a inc 1 if b < 5"
   "c dec -10 if a >= 1"
   "c inc -20 if c == 10"])

(def d08-s01-raw
  ["x dec 315 if y != -910"])

(def d08-s00
  [["b" d08/nil+ 5 "a" > 1]
   ["a" d08/nil+ 1 "b" < 5]
   ["c" d08/nil- -10 "a" >= 1]
   ["c" d08/nil+ -20 "c" = 10]])

(def d08-s01
  [["x" d08/nil- 315 "y" not= -910]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d08-s00 (d08/parse d08-s00-raw)))
    (is (= d08-s01 (d08/parse d08-s01-raw)))))

(deftest step-test
  (testing "Can incrementally update state based on instructions"
    (is (= {} (d08/step {} (nth d08-s00 0))))
    (is (= {"a" 1} (d08/step {} (nth d08-s00 1))))
    (is (= {"a" 1 "c" 10} (d08/step {"a" 1} (nth d08-s00 2))))
    (is (= {"a" 1 "c" -10} (d08/step {"a" 1 "c" 10} (nth d08-s00 3))))))

(deftest largest-value-test
  (testing "After executing instructions, returns largest register value"
    (is (= 1 (d08/largest-value d08-s00)))))

(deftest largest-value-ever-test
  (testing "Returns largest register at any point during processing instructions"
    (is (= 10 (d08/largest-value-ever d08-s00)))))

(def day08-input (u/parse-puzzle-input d08/parse 2017 8))

(deftest part1-test
  (testing "Reproduces the answer for day08, part1"
    (is (= 7296 (d08/part1 day08-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day08, part2"
    (is (= 8186 (d08/part2 day08-input)))))