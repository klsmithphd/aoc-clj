(ns aoc-clj.2021.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2021.day24 :as t]))

(deftest day24-part1-soln
  (testing "Reproduces the answer for day24, part1"
    (is (= 98998519596997 (t/day24-part1-soln)))))

;; FIXME: 2021.day24 part 2 solution is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/11
(deftest ^:slow day24-part2-soln
  (testing "Reproduces the answer for day24, part2"
    (is (= 31521119151421 (t/day24-part2-soln)))))
