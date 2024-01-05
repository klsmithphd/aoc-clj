(ns aoc-clj.2022.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day04 :as t]))

(def d04-s00
  (t/parse
   ["2-4,6-8"
    "2-3,4-5"
    "5-7,7-9"
    "2-8,3-7"
    "6-6,4-6"
    "2-6,4-8"]))

(deftest parse-test
  (testing "Parses the sample input correctly"
    (is (= d04-s00
           [[[2 4] [6 8]]
            [[2 3] [4 5]]
            [[5 7] [7 9]]
            [[2 8] [3 7]]
            [[6 6] [4 6]]
            [[2 6] [4 8]]]))))

(def day04-input (u/parse-puzzle-input t/parse 2022 4))

(deftest part1-test
  (testing "Reproduces the answer for day04, part1"
    (is (= 584 (t/part1 day04-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day04, part2"
    (is (= 933 (t/part2 day04-input)))))