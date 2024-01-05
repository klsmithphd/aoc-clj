(ns aoc-clj.2022.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day01 :as t]))

(def d01-s00
  (t/parse
   ["1000"
    "2000"
    "3000"
    ""
    "4000"
    ""
    "5000"
    "6000"
    ""
    "7000"
    "8000"
    "9000"
    ""
    "10000"]))

(deftest parse-test
  (testing "Parses the sample input correctly"
    (is (= d01-s00
           [[1000 2000 3000] [4000] [5000 6000] [7000 8000 9000] [10000]]))))

(deftest top-n-capacity-sum-test
  (testing "Find the max calorie capacity of top elf and top 3 elves"
    (is (= 24000 (t/top-n-capacity-sum 1 d01-s00)))
    (is (= 45000 (t/top-n-capacity-sum 3 d01-s00)))))

(def day01-input (u/parse-puzzle-input t/parse 2022 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= 70116 (t/part1 day01-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day01, part2"
    (is (= 206582 (t/part2 day01-input)))))