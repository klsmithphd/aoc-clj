(ns aoc-clj.2019.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2019.day13 :as t]))

(deftest day13-part1-soln-test
  (testing "Can reproduce the answer for part1"
    (is (= 230 (t/day13-part1-soln)))))

(deftest day13-part2-soln-test
  (testing "Can reproduce the answer for part2"
    (is (= 11140 (t/day13-part2-soln)))))