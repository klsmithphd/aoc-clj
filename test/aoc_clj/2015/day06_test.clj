(ns aoc-clj.2015.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day06 :as t]))

(def day06-input (u/parse-puzzle-input t/parse 2015 6))

;; FIXME: 2015.day06 solution is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/2
(deftest ^:slow part1
  (testing "Reproduces the answer for day06, part1"
    (is (= 377891 (t/part1 day06-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day06, part2"
    (is (= 14110788 (t/part2 day06-input)))))