(ns aoc-clj.2015.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day23 :as t]))

(def day23-sample
  (t/parse
   ["inc a"
    "jio a, +2"
    "tpl a"
    "inc a"]))

(deftest run-program-test
  (testing "Runs on the simple input"
    (is (= {:a 2 :b 0 :next-inst 4} (t/run-program day23-sample {:a 0 :b 0 :next-inst 0})))))

(deftest day23-part1-soln
  (testing "Reproduces the answer for day23, part1"
    (is (= 184 (t/day23-part1-soln)))))

(deftest day23-part2-soln
  (testing "Reproduces the answer for day23, part2"
    (is (= 231 (t/day23-part2-soln)))))