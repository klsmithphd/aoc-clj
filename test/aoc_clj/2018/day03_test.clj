(ns aoc-clj.2018.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day03 :as t]))

(deftest day03-part1-soln
  (testing "Reproduces the answer for day03, part1"
    (is (= 116489 (t/day03-part1-soln)))))

(deftest day03-part2-soln
  (testing "Reproduces the answer for day03, part2"
    (is (= "#1260" (t/day03-part2-soln)))))