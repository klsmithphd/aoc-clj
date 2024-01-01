(ns aoc-clj.2020.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day08 :as t]))

(def d08-s00
  (t/parse
   ["nop +0"
    "acc +1"
    "jmp +4"
    "acc +3"
    "jmp -3"
    "acc -99"
    "acc +1"
    "jmp -4"
    "acc +6"]))

(deftest part1-sample
  (testing "Can return accumulator value at beginning of second loop"
    (is (= 5 (t/acc-value-at-second-loop d08-s00)))))

(deftest part2-sample
  (testing "Returns the accumulator after fixing the invalid instruction"
    (is (= 8 (t/acc-value-for-finite-loop d08-s00)))))

(def day08-input (u/parse-puzzle-input t/parse 2020 8))

(deftest day08-part1-soln
  (testing "Reproduces the answer for day08, part1"
    (is (= 1451 (t/day08-part1-soln day08-input)))))

(deftest day08-part2-soln
  (testing "Reproduces the answer for day08, part2"
    (is (= 1160 (t/day08-part2-soln day08-input)))))