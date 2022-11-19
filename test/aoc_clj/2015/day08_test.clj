(ns aoc-clj.2015.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day08 :as t]))

(def day08-sample1 "\"\"")
(def day08-sample2 "\"abc\"")
(def day08-sample3 "\"aaa\\\"aaa\"")
(def day08-sample4 "\"\\x27\"")

(deftest code-chars-test
  (testing "Correctly counts the number of code chars"
    (is (= 2  (t/code-chars day08-sample1)))
    (is (= 5  (t/code-chars day08-sample2)))
    (is (= 10 (t/code-chars day08-sample3)))
    (is (= 6  (t/code-chars day08-sample4)))))

(deftest unescaped-chars-test
  (testing "Correctly counts the number of unescaped chars"
    (is (= 0  (t/unescaped-chars day08-sample1)))
    (is (= 3  (t/unescaped-chars day08-sample2)))
    (is (= 7  (t/unescaped-chars day08-sample3)))
    (is (= 1  (t/unescaped-chars day08-sample4)))))

(deftest escaped-chars-test
  (testing "Correctly counts the number of escaped chars"
    (is (= 6  (t/escaped-chars day08-sample1)))
    (is (= 9  (t/escaped-chars day08-sample2)))
    (is (= 16 (t/escaped-chars day08-sample3)))
    (is (= 11 (t/escaped-chars day08-sample4)))))


(deftest unescaped-difference-test
  (is (= 12 (t/unescaped-difference [day08-sample1
                                     day08-sample2
                                     day08-sample3
                                     day08-sample4]))))

(deftest escaped-difference-test
  (is (= 19 (t/escaped-difference [day08-sample1
                                   day08-sample2
                                   day08-sample3
                                   day08-sample4]))))

(deftest day08-part1-soln
  (testing "Reproduces the answer for day08, part1"
    (is (= 1371 (t/day08-part1-soln)))))

(deftest day08-part2-soln
  (testing "Reproduces the answer for day08, part2"
    (is (= 2117 (t/day08-part2-soln)))))