(ns aoc-clj.2022.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day01 :as t]))

(def s01-01
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

(deftest max-capacity-test
  (testing "Find the max calorie capacity of any elf"
    (is (= 24000 (t/max-capacity s01-01)))))

(deftest top-three-max-capacity-test
  (testing "Find the max calorie capacity of top 3 elves"
    (is (= 45000 (t/top-three-max-capacity s01-01)))))

(deftest day01-part1-soln
  (testing "Reproduces the answer for day01, part1"
    (is (= 70116 (t/day01-part1-soln)))))

(deftest day01-part2-soln
  (testing "Reproduces the answer for day01, part2"
    (is (= 206582 (t/day01-part2-soln)))))