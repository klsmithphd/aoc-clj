(ns aoc-clj.2020.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day05 :as t]))

(def d05-s00 "FBFBBFFRLR")
(def d05-s01 "BFFFBBFRRR")
(def d05-s02 "FFFBBBFRRR")
(def d05-s03 "BBFFBBFRLL")

(deftest seatid-parser
  (testing "Correctly parses the seat-id from the string")
  (is (= 357 (t/seat-id d05-s00)))
  (is (= 567 (t/seat-id d05-s01)))
  (is (= 119 (t/seat-id d05-s02)))
  (is (= 820 (t/seat-id d05-s03))))

(def day05-input (u/parse-puzzle-input t/parse 2020 5))

(deftest part1-test
  (testing "Reproduces the answer for day05, part1"
    (is (= 858 (t/part1 day05-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day05, part2"
    (is (= 557 (t/part2 day05-input)))))