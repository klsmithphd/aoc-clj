(ns aoc-clj.2017.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day10 :as d10]))

(def d10-s00-raw ["3, 4, 1, 5"])
(def d10-s00 [3 4 1 5])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d10-s00 (d10/parse d10-s00-raw)))))

(deftest twist-test
  (testing "Executes one twist instruction and returns the new state"
    (is (= {:v [2 1 0 3 4] :pos 3 :skip 1}
           (d10/twist {:v [0 1 2 3 4] :pos 0 :skip 0} 3)))
    (is (= {:v [4 3 0 1 2] :pos 3 :skip 2}
           (d10/twist {:v [2 1 0 3 4] :pos 3 :skip 1} 4)))
    (is (= {:v [4 3 0 1 2] :pos 1 :skip 3}
           (d10/twist {:v [4 3 0 1 2] :pos 3 :skip 2} 1)))
    (is (= {:v [3 4 2 1 0] :pos 4 :skip 4}
           (d10/twist {:v [4 3 0 1 2] :pos 1 :skip 3} 5)))))

(deftest first-two-nums-prod-test
  (testing "Applies all the twists and then computes product of first two nums"
    (is (= 12 (d10/first-two-nums-prod 5 d10-s00)))))

(def day10-input (u/parse-puzzle-input d10/parse 2017 10))

(deftest part1-test
  (testing "Reproduces the answer for day10, part1"
    (is (= 38628 (d10/part1 day10-input)))))