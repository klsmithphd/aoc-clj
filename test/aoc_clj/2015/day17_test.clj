(ns aoc-clj.2015.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day17 :as t]))

(def d17-s00 [20 15 10 5 5])

(def total-options-test
  (testing "Computes the total options to meet a target"
    (is (= 0 (t/total-options 10 [5])))
    (is (= 1 (t/total-options 8 [8])))
    (is (= 1 (t/total-options 0 [1 2 3 4])))
    (is (= 1 (t/total-options 5 [3 2])))
    (is (= 2 (t/total-options 5 [3 2 2])))

    (is (= 4 (t/total-options 25 d17-s00)))))



(deftest combinations-test
  (testing "Identifies the correct combinations, allowing for dupes"
    (is (= '((20 5) (20 5) (15 10) (15 5 5)) (t/combinations 25 d17-s00)))))

(deftest min-combinations-test
  (testing "Identifies the correct minimal combinations"
    (is (= '((20 5) (20 5) (15 10)) (t/minimal-combinations 25 d17-s00)))))

(def day17-input (u/parse-puzzle-input t/parse 2015 17))

;; FIXME: 2015.day17 solution is slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/6
(deftest part1
  (testing "Reproduces the answer for day17, part1"
    (is (= 654 (t/part1 day17-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day17, part2"
    (is (= 57 (t/part2 day17-input)))))