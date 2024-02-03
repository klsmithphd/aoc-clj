(ns aoc-clj.2015.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day15 :as t]))

(def d15-s00-raw
  ["Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8"
   "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"])

(def d15-s00
  [[-1 -2 6 3 8]
   [2 3 -2 -1 3]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d15-s00 (t/parse d15-s00-raw)))))

(deftest score-vec-test
  (testing "Computes the score component vector"
    (is (= [68 80 152 76 520]  (t/score-vec d15-s00 [44 56])))
    (is (= [80 100 120 60 500] (t/score-vec d15-s00 [40 60])))))

(deftest score-test
  (testing "Computes the score correctly"
    (is (= 62842880 (t/score (t/score-vec d15-s00 [44 56]))))
    (is (= 57600000 (t/score (t/score-vec d15-s00 [40 60]))))))

(deftest all-options-test
  (testing "Can generate all combinations of n vars that sum to a total"
    (is (= (t/all-options 2 2) [[2 0] [1 1] [0 2]]))
    (is (= (t/all-options 5 2) [[5 0] [4 1] [3 2] [2 3] [1 4] [0 5]]))
    (is (= (t/all-options 2 3) [[2 0 0]
                                [1 1 0] [1 0 1]
                                [0 2 0] [0 1 1] [0 0 2]]))
    (is (= (t/all-options 2 4) [[2 0 0 0]
                                [1 1 0 0] [1 0 1 0] [1 0 0 1]
                                [0 2 0 0] [0 1 1 0] [0 1 0 1]
                                [0 0 2 0] [0 0 1 1] [0 0 0 2]]))))

(def day15-input (u/parse-puzzle-input t/parse 2015 15))

(deftest part1-test
  (testing "Reproduces the answer for day15, part1"
    (is (= 21367368 (t/part1 day15-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day15, part2"
    (is (= 1766400 (t/part2 day15-input)))))