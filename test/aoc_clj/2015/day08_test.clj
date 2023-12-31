(ns aoc-clj.2015.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day08 :as t]))

(def d08-s00 "\"\"")
(def d08-s01 "\"abc\"")
(def d08-s02 "\"aaa\\\"aaa\"")
(def d08-s03 "\"\\x27\"")

(deftest code-chars-test
  (testing "Correctly counts the number of code chars"
    (is (= 2  (t/code-chars d08-s00)))
    (is (= 5  (t/code-chars d08-s01)))
    (is (= 10 (t/code-chars d08-s02)))
    (is (= 6  (t/code-chars d08-s03)))))

(deftest unescaped-chars-test
  (testing "Correctly counts the number of unescaped chars"
    (is (= 0  (t/unescaped-chars d08-s00)))
    (is (= 3  (t/unescaped-chars d08-s01)))
    (is (= 7  (t/unescaped-chars d08-s02)))
    (is (= 1  (t/unescaped-chars d08-s03)))))

(deftest escaped-chars-test
  (testing "Correctly counts the number of escaped chars"
    (is (= 6  (t/escaped-chars d08-s00)))
    (is (= 9  (t/escaped-chars d08-s01)))
    (is (= 16 (t/escaped-chars d08-s02)))
    (is (= 11 (t/escaped-chars d08-s03)))))


(deftest unescaped-difference-test
  (is (= 12 (t/unescaped-difference [d08-s00
                                     d08-s01
                                     d08-s02
                                     d08-s03]))))

(deftest escaped-difference-test
  (is (= 19 (t/escaped-difference [d08-s00
                                   d08-s01
                                   d08-s02
                                   d08-s03]))))

(def day08-input (u/parse-puzzle-input t/parse 2015 8))

(deftest day08-part1-soln
  (testing "Reproduces the answer for day08, part1"
    (is (= 1371 (t/day08-part1-soln day08-input)))))

(deftest day08-part2-soln
  (testing "Reproduces the answer for day08, part2"
    (is (= 2117 (t/day08-part2-soln day08-input)))))