(ns aoc-clj.2022.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day05 :as t]))

(def d05-s00
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

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= d05-s00
           {:stacks {1 ["Z" "N"]
                     2 ["M" "C" "D"]
                     3 ["P"]}
            :moves [[1 2 1]
                    [3 1 3]
                    [2 2 1]
                    [1 1 2]]}))))

(deftest apply-move-1-test
  (testing "Move logic of part 1 is correctly implemented"
    (is (= {1 ["Z" "N" "D"]
            2 ["M" "C"]
            3 ["P"]}
           (t/apply-move-1
            {1 ["Z" "N"]
             2 ["M" "C" "D"]
             3 ["P"]}
            [1 2 1])))
    (is (= {1 []
            2 ["M" "C"]
            3 ["P" "D" "N" "Z"]}
           (t/apply-move-1
            {1 ["Z" "N" "D"]
             2 ["M" "C"]
             3 ["P"]}
            [3 1 3])))
    (is (= {1 ["C" "M"]
            2 []
            3 ["P" "D" "N" "Z"]}
           (t/apply-move-1
            {1 []
             2 ["M" "C"]
             3 ["P" "D" "N" "Z"]}
            [2 2 1])))
    (is (= {1 ["C"]
            2 ["M"]
            3 ["P" "D" "N" "Z"]}
           (t/apply-move-1
            {1 ["C" "M"]
             2 []
             3 ["P" "D" "N" "Z"]}
            [1 1 2])))))

(deftest apply-move-2-test
  (testing "Move logic of part 2 is correctly implemented"
    (is (= {1 ["Z" "N" "D"]
            2 ["M" "C"]
            3 ["P"]}
           (t/apply-move-2
            {1 ["Z" "N"]
             2 ["M" "C" "D"]
             3 ["P"]}
            [1 2 1])))
    (is (= {1 []
            2 ["M" "C"]
            3 ["P" "Z" "N" "D"]}
           (t/apply-move-2
            {1 ["Z" "N" "D"]
             2 ["M" "C"]
             3 ["P"]}
            [3 1 3])))
    (is (= {1 ["M" "C"]
            2 []
            3 ["P" "Z" "N" "D"]}
           (t/apply-move-2
            {1 []
             2 ["M" "C"]
             3 ["P" "Z" "N" "D"]}
            [2 2 1])))
    (is (= {1 ["M"]
            2 ["C"]
            3 ["P" "Z" "N" "D"]}
           (t/apply-move-2
            {1 ["M" "C"]
             2 []
             3 ["P" "Z" "N" "D"]}
            [1 1 2])))))

(deftest final-arrangement-1-test
  (testing "Stacks end up in the correct final state in part1"
    (is (= {1 ["C"], 2 ["M"], 3 ["P" "D" "N" "Z"]}
           (t/final-arrangement-1 d05-s00)))))

(deftest final-arrangement-2-test
  (testing "Stacks end up in the correct final state in part1"
    (is (= {1 ["M"], 2 ["C"], 3 ["P" "Z" "N" "D"]}
           (t/final-arrangement-2 d05-s00)))))

(def day05-input (u/parse-puzzle-input t/parse 2022 5))

(deftest part1-test
  (testing "Reproduces the answer for day05, part1"
    (is (= "CNSZFDVLJ" (t/part1 day05-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day05, part2"
    (is (= "QNDWLMGNS" (t/part2 day05-input)))))