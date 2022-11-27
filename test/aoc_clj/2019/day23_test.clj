(ns aoc-clj.2019.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2019.day23 :as t]))

(deftest day23-part1-soln-test
  (testing "Can reproduce the answer for part1"
    (is (= 23886 (t/day23-part1-soln)))))

(deftest day21-part2-soln-test
  (testing "Can reproduce the answer for part2"
    (is (= 18333 (t/day23-part2-soln)))))