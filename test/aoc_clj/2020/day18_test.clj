(ns aoc-clj.2020.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day18 :as t]))

(def d18-s00 "1 + 2 * 3 + 4 * 5 + 6")
(def d18-s01 "1 + (2 * 3) + (4 * (5 + 6))")
(def d18-s02 "2 * 3 + (4 * 5)")
(def d18-s03 "5 + (8 * 3 + 9 + 3 * 4 * 3)")
(def d18-s04 "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")
(def d18-s05 "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")

(deftest infix-test
  (testing "Can correctly compute infix expressions"
    (is (= 71    (t/infix (t/interpret d18-s00))))
    (is (= 51    (t/infix (t/interpret d18-s01))))
    (is (= 26    (t/infix (t/interpret d18-s02))))
    (is (= 437   (t/infix (t/interpret d18-s03))))
    (is (= 12240 (t/infix (t/interpret d18-s04))))
    (is (= 13632 (t/infix (t/interpret d18-s05))))))

(deftest infix-test2
  (testing "Infix expressions with operator precedence"
    (is (= 231    (t/infix (t/interpret2 d18-s00))))
    (is (= 51     (t/infix (t/interpret2 d18-s01))))
    (is (= 46     (t/infix (t/interpret2 d18-s02))))
    (is (= 1445   (t/infix (t/interpret2 d18-s03))))
    (is (= 669060 (t/infix (t/interpret2 d18-s04))))
    (is (= 23340  (t/infix (t/interpret2 d18-s05))))))

(def day18-input (u/parse-puzzle-input t/parse 2020 18))

(deftest part1-test
  (testing "Reproduces the answer for day18, part1"
    (is (= 69490582260 (t/part1 day18-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day18, part2"
    (is (= 362464596624526 (t/part2 day18-input)))))