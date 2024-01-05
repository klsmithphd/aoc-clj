(ns aoc-clj.2020.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day06 :as t]))

(def d06-s00-raw
  ["abc"
   ""
   "a"
   "b"
   "c"
   ""
   "ab"
   "ac"
   ""
   "a"
   "a"
   "a"
   "a"
   ""
   "b"])

(def d06-s00
  [["abc"] ["a" "b" "c"] ["ab" "ac"] ["a" "a" "a" "a"] ["b"]])

(deftest parse-test
  (testing "Correctly parses the input format"
    (is (= d06-s00 (t/parse d06-s00-raw)))))

(deftest unique-items-in-group
  (testing "Correct identifies the sets of unique items in a group"
    (is (= '(#{\a \b \c} #{\a \b \c} #{\a \b \c} #{\a} #{\b})
           (map t/unique-answers-in-group d06-s00)))))

(deftest common-items-in-group
  (testing "Correct identifies the sets of common items in a group"
    (is (= '(#{\a \b \c} #{} #{\a} #{\a} #{\b})
           (map t/common-answers-in-group d06-s00)))))

(def day06-input (u/parse-puzzle-input t/parse 2020 6))

(deftest part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= 6170 (t/part1 day06-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day06, part2"
    (is (= 2947 (t/part2 day06-input)))))