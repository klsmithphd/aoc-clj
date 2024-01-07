(ns aoc-clj.2015.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day08 :as d08]))

(def d08-s00
  ["\"\""            ;; ""
   "\"abc\""         ;; "abc"
   "\"aaa\\\"aaa\""  ;; "aaa\"aaa"
   "\"\\x27\""])     ;; "\x27"

(def d08-s00-unescaped
  [""
   "abc"
   "aaa\"aaa"
   "'"])

(def d08-s00-escaped
  ["\"\\\"\\\"\""                ;; "\"\""
   "\"\\\"abc\\\"\""             ;; "\"abc\""
   "\"\\\"aaa\\\\\\\"aaa\\\"\""  ;; "\"aaa\\\"aaa\""
   "\"\\\"\\\\x27\\\"\""])       ;; "\"\\x27\""

(deftest unescape-test
  (testing "Correctly un-escapes the input strings"
    (is (= d08-s00-unescaped (map d08/unescape d08-s00)))))

(deftest escape-test
  (testing "Correctly doubly escapes the input strings"
    (is (= d08-s00-escaped (map d08/escape d08-s00)))))

(deftest code-chars-test
  (testing "Correctly counts the number of code chars"
    (is (= [2 5 10 6] (map count d08-s00)))))

(deftest unescaped-chars-test
  (testing "Correctly counts the number of unescaped chars"
    (is (= [0 3 7 1] (map (comp count d08/unescape) d08-s00)))))

(deftest escaped-chars-test
  (testing "Correctly counts the number of escaped chars"
    (is (= [6 9 16 11] (map (comp count d08/escape) d08-s00)))))

(deftest unescaped-difference-test
  (testing "Correctly computes the size difference between encoded and 
            unescaped strings"
    (is (= 12 (d08/unescaped-difference d08-s00)))))

(deftest escaped-difference-test
  (testing "Correctly computes the size difference between doubly
            encoded and originally encoded strings"
    (is (= 19 (d08/escaped-difference d08-s00)))))

(def day08-input (u/parse-puzzle-input d08/parse 2015 8))

(deftest part1-test
  (testing "Reproduces the answer for day08, part1"
    (is (= 1371 (d08/part1 day08-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day08, part2"
    (is (= 2117 (d08/part2 day08-input)))))