(ns aoc-clj.2022.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day12 :as t]))

(deftest day12-part1-soln
  (testing "Reproduces the answer for day12, part1"
    (is (= 408 (t/day12-part1-soln)))))

(deftest day12-part2-soln
  (testing "Reproduces the answer for day12, part2"
    (is (= 399 (t/day12-part2-soln)))))