(ns aoc-clj.2015.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day06 :as t]))

;; FIXME: 2015.day06 solution is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/2
(deftest ^:slow day06-part1-soln
  (testing "Reproduces the answer for day06, part1"
    (is (= 377891 (t/day06-part1-soln)))))

(deftest ^:slow day06-part2-soln
  (testing "Reproduces the answer for day06, part2"
    (is (= 14110788 (t/day06-part2-soln)))))