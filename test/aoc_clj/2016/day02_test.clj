(ns aoc-clj.2016.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day02 :as d02]))

(def d02-s00
  ["ULL"
   "RRDDD"
   "LURDL"
   "UUUUD"])

(deftest bathroom-code
  (testing "Computes the bathroom code on a square 9-digit keypad"
    (is (= "1985" (d02/square-bathroom-code d02-s00))))
  (testing "Computes the bathroom code on a diagonal keypad"
    (is (= "5DB3" (d02/diamond-bathroom-code d02-s00)))))

(def day02-input (u/parse-puzzle-input d02/parse 2016 2))

(deftest part1-test
  (testing "Reproduces the answer for day02, part1"
    (is (= "18843" (d02/part1 day02-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day02, part2"
    (is (= "67BB9" (d02/part2 day02-input)))))