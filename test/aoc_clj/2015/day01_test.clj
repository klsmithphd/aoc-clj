(ns aoc-clj.2015.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day01 :as t]))

(deftest final-floor
  (testing "Correctly determines the final floor"
    (is (= 0   (t/final-floor "(())")))
    (is (= 0   (t/final-floor "()()")))
    (is (= 3   (t/final-floor "(((")))
    (is (= 3   (t/final-floor "(()(()(")))
    (is (= 3   (t/final-floor "))(((((")))
    (is (= -1  (t/final-floor "())")))
    (is (= -1  (t/final-floor "))(")))
    (is (= -3  (t/final-floor ")))")))
    (is (= -3  (t/final-floor ")())())")))))

(deftest first-pos-in-basement
  (testing "Finds the first position where the sum equals -1"
    (is (= 1 (t/first-pos-in-basement ")")))
    (is (= 5 (t/first-pos-in-basement "()())")))))

(def day01-input (u/parse-puzzle-input t/parse 2015 1))

(deftest day01-part1-soln
  (testing "Reproduces the answer for day01, part1"
    (is (= 138 (t/day01-part1-soln day01-input)))))

(deftest day01-part2-soln
  (testing "Reproduces the answer for day01, part2"
    (is (= 1771 (t/day01-part2-soln day01-input)))))