(ns aoc-clj.2022.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day09 :as t]))

(def d09-s01
  (t/parse
   ["R 4"
    "U 4"
    "L 3"
    "D 1"
    "R 4"
    "D 1"
    "L 5"
    "R 2"]))

(def d09-s02
  (t/parse
   ["R 5"
    "U 8"
    "L 8"
    "D 3"
    "R 17"
    "D 10"
    "L 25"
    "U 20"]))

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= d09-s01
           ["R" "R" "R" "R"
            "U" "U" "U" "U"
            "L" "L" "L"
            "D"
            "R" "R" "R" "R"
            "D"
            "L" "L" "L" "L" "L"
            "R" "R"]))))

(deftest all-moves-test
  (testing "The chain ends in the expected final state"
    (is (= [[1 2] [2 2]]
           (last (t/all-moves 2 d09-s01))))
    (is (= [[0 0] [0 0] [0 0] [0 0] [1 1] [2 2] [3 2] [2 2] [1 2] [2 2]]
           (last (t/all-moves 10 d09-s01))))
    (is (= [[-11 6] [-11 7] [-11 8] [-11 9] [-11 10]
            [-11 11] [-11 12] [-11 13] [-11 14] [-11 15]]
           (last (t/all-moves 10 d09-s02))))))

(deftest distinct-tail-positions-test
  (testing "Counts all the unique positions the tail of the rope occupied"
    (is (= 13 (t/distinct-tail-positions 2 d09-s01)))
    (is (= 1  (t/distinct-tail-positions 10 d09-s01)))
    (is (= 36 (t/distinct-tail-positions 10 d09-s02)))))

(def day09-input (u/parse-puzzle-input t/parse 2022 9))

(deftest part1-test
  (testing "Reproduces the answer for day09, part1"
    (is (= 5874 (t/part1 day09-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day09, part2"
    (is (= 2467 (t/part2 day09-input)))))