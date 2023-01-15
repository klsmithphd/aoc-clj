(ns aoc-clj.2022.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day04 :as t]))

(def s04-01
  (t/parse
   ["2-4,6-8"
    "2-3,4-5"
    "5-7,7-9"
    "2-8,3-7"
    "6-6,4-6"
    "2-6,4-8"]))

(deftest parse-test
  (testing "Parses the sample input correctly"
    (is (= s04-01
           [[[2 4] [6 8]]
            [[2 3] [4 5]]
            [[5 7] [7 9]]
            [[2 8] [3 7]]
            [[6 6] [4 6]]
            [[2 6] [4 8]]]))))

(deftest day04-part1-soln
  (testing "Reproduces the answer for day04, part1"
    (is (= 584 (t/day04-part1-soln)))))

(deftest day04-part2-soln
  (testing "Reproduces the answer for day04, part2"
    (is (= 933 (t/day04-part2-soln)))))