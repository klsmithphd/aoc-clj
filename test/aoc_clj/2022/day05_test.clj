(ns aoc-clj.2022.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day05 :as t]))

(def d05-s01
  (t/parse
   ["    [D]    "
    "[N] [C]    "
    "[Z] [M] [P]"
    " 1   2   3 "
    ""
    "move 1 from 2 to 1"
    "move 3 from 1 to 3"
    "move 2 from 2 to 1"
    "move 1 from 1 to 2"]))

(deftest final-arrangement-1-test
  (testing "Stacks end up in the correct final state in part1"
    (is (= {1 ["C"], 2 ["M"], 3 ["P" "D" "N" "Z"]}
           (t/final-arrangement-1 d05-s01)))))

(deftest final-arrangement-2-test
  (testing "Stacks end up in the correct final state in part1"
    (is (= {1 ["M"], 2 ["C"], 3 ["P" "Z" "N" "D"]}
           (t/final-arrangement-2 d05-s01)))))

(deftest day05-part1-soln
  (testing "Reproduces the answer for day05, part1"
    (is (= "CNSZFDVLJ" (t/day05-part1-soln)))))

(deftest day05-part2-soln
  (testing "Reproduces the answer for day05, part2"
    (is (= "QNDWLMGNS" (t/day05-part2-soln)))))