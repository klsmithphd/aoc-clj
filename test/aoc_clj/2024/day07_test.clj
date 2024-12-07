(ns aoc-clj.2024.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2024.day07 :as d07]))

(def d07-s00-raw
  ["190: 10 19"
   "3267: 81 40 27"
   "83: 17 5"
   "156: 15 6"
   "7290: 6 8 6 15"
   "161011: 16 10 13"
   "192: 17 8 14"
   "21037: 9 7 18 13"
   "292: 11 6 16 20"])

(def d07-s00
  [[190 10 19]
   [3267 81 40 27]
   [83 17 5]
   [156 15 6]
   [7290 6 8 6 15]
   [161011 16 10 13]
   [192 17 8 14]
   [21037 9 7 18 13]
   [292 11 6 16 20]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d07-s00 (d07/parse d07-s00-raw)))))