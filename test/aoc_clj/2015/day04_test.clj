(ns ^:eftest/synchronized aoc-clj.2015.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day04 :as d04]))

(def d04-s00 "abcdef")
(def d04-s01 "pqrstuv")

(deftest ^:eftest/synchronized first-with-five-zeros
  (testing "Finds the earliest number to result in md5 hash starting 
            with five zeros"
    (is (= 609043  (d04/first-five-zero-int d04-s00)))
    (is (= 1048970 (d04/first-five-zero-int d04-s01)))))

(def day04-input (u/parse-puzzle-input d04/parse 2015 4))

(deftest ^:eftest/synchronized part1-test
  (testing "Reproduces the answer for day04, part1"
    (is (= 282749 (d04/part1 day04-input)))))

(deftest ^:slow part2-test
  (testing "Reproduces the answer for day04, part2"
    (is (= 9962624 (d04/part2 day04-input)))))