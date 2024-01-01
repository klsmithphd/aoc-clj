(ns aoc-clj.2015.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day23 :as t]))

(def d23-s00
  (t/parse
   ["inc a"
    "jio a, +2"
    "tpl a"
    "inc a"]))

(deftest run-program-test
  (testing "Runs on the simple input"
    (is (= {:a 2 :b 0 :next-inst 4}
           (t/run-program d23-s00 {:a 0 :b 0 :next-inst 0})))))

(def day23-input (u/parse-puzzle-input t/parse 2015 23))

(deftest day23-part1-soln
  (testing "Reproduces the answer for day23, part1"
    (is (= 184 (t/day23-part1-soln day23-input)))))

(deftest day23-part2-soln
  (testing "Reproduces the answer for day23, part2"
    (is (= 231 (t/day23-part2-soln day23-input)))))