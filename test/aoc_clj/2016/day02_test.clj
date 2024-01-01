(ns aoc-clj.2016.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day02 :as t]))

(def d02-s00
  ["ULL"
   "RRDDD"
   "LURDL"
   "UUUUD"])

(deftest bathroom-code
  (testing "Computes the bathroom code on a square 9-digit keypad"
    (is (= "1985" (t/square-bathroom-code d02-s00))))
  (testing "Computes the bathroom code on a diagonal keypad"
    (is (= "5DB3" (t/diagonal-bathroom-code d02-s00)))))

(def day02-input (u/parse-puzzle-input t/parse 2016 2))

(deftest day02-part1-soln
  (testing "Reproduces the answer for day02, part1"
    (is (= "18843" (t/day02-part1-soln day02-input)))))

(deftest day02-part2-soln
  (testing "Reproduces the answer for day02, part2"
    (is (= "67BB9" (t/day02-part2-soln day02-input)))))